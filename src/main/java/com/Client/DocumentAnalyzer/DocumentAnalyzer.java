package com.Client.DocumentAnalyzer;
import javax.xml.parsers.DocumentBuilderFactory;

import com.Client.Extractor.*;
import com.Client.Utils.RDFXMLUtils;
import com.Client.Utils.SparqlUtils;
import com.hp.hpl.jena.query.ResultSet;
import org.apache.xerces.util.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by OJT4 on 8/1/14.
 *
 */

public class DocumentAnalyzer {

    private static int MAX_DOC_SIZE = Configuration.getCurrent_config().getMaxdocSize();



    /**
     * run Open calais SOAP Analyzer and save the response to output.xml
     * @param filePath location and name of the file
     * @param APIKEY license key obtain from registration in Open calais
     * @return true if response submitted successfully
     * @throws Exception
     */
    public static boolean analyze(String filePath, String APIKEY) throws Exception{
        DocumentAnalyzer analyzer = new DocumentAnalyzer();
        String content = null;
         try {
              content = analyzer.readfile(filePath);
                if (content.length()>MAX_DOC_SIZE) {
                    throw new IllegalArgumentException("must be less than 1000 characters");
                }

             content = analyzer.getTextfromOpenCalais(content, APIKEY);
            if (content.isEmpty()) {
                return false;
            }
             Document outdoc = analyzer.getDocumentFromRdf(content);

             RDFXMLUtils.serializeDoc(outdoc);
         }
         catch (IOException e) {
             e.printStackTrace();
         }
        return true;
    }


    private String readfile(String filePath) throws Exception{
        BufferedReader in = null;
        StringBuffer out = null;
        try {
            in = new BufferedReader(new FileReader(filePath));
            out = new StringBuffer();
            String line;

            while ((line = in.readLine()) != null) {
                out.append(line);
                out.append("\r\n");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read text file (" + filePath + ")", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("could not close input reader for " + filePath);
                }
            }
        }
        return out.toString();
    }

    private Document getDocumentFromRdf(String content)
            throws Exception {
        try {
            Document xdoc;
            xdoc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();

            // set root element of the output XML
            Node Response = xdoc.createElement("Response");
            xdoc.appendChild(Response);
//
            EnrichSocialTags enrichSocialTags= new EnrichSocialTags();
            enrichSocialTags.enrich(createSection(Response
                    ,"SocialTags","SocialTag","Queries/SocialTag.sparql",content,"subject"));

            EnrichCompanies enrichCompanies = new EnrichCompanies(content);
            enrichCompanies.enrich(createSection(
                    Response, "Companies", "Company",
                    "Queries/Company.sparql", content, "subject"));

            EnrichGeo enrichGeo= new EnrichGeo();
            enrichGeo.enrich(createSection(
                    Response, "Geographies", "Geography",
                    "Queries/Geo.sparql", content, "subject"));

            EnrichProducts enrichProducts= new EnrichProducts();
            enrichProducts.enrich(createSection(
                    Response, "Products", "Product",
                    "Queries/Products.sparql", content, "subject"));

            EnrichTechnology enrichTechnology= new EnrichTechnology();
            enrichTechnology.enrich(createSection(
                    Response, "Technology", "Technologies",
                    "Queries/Technology.sparql", content, "subject"));


            return xdoc;
        } catch (ParserConfigurationException e) {
            throw new IllegalArgumentException(" Error creating new DOM object for content", e);
        }
    }


    private String getTextfromOpenCalais(String content, String APIkey) throws Exception{
        return OpenCalais.getCalaisRDFText(content,APIkey);
    }


    /**
     * create a section in response node
     * @param Response created Node that where section and element will be appended
     * @param sectionName name of the section to be added
     * @param elementName name of the element to be added
     * @param sparqlFile Query file of the section to be added
     * @param inContent response output from Open Calais SOAP client. in RDF format
     * @param nodeName name of the node to be added
     * @return
     * @throws Exception
     */
    private static Node createSection(Node Response, String sectionName,
                                     String elementName,
                                     String sparqlFile, String inContent, String nodeName) throws Exception {

        System.out.println("Building " + sectionName + " Section");
        Document xdoc = Response.getOwnerDocument();
        Node sectionNode = xdoc.createElement(sectionName);
        Response.appendChild(sectionNode);
        ResultSet results = SparqlUtils.getSparqlOutput(sparqlFile, inContent);
        RDFXMLUtils.builldXml(sectionNode, elementName, results);

        if(sectionNode.getFirstChild() != null) {
            NodeList elements = sectionNode.getChildNodes();
            for (int i = 0; i < elements.getLength(); i++) {
                Node subjectNode = RDFXMLUtils.getNodeByName(elements.item(i), nodeName);
                if (subjectNode != null) {
                    Node exacts = subjectNode.getOwnerDocument().createElement("exacts");
                    String paramValue = DOMUtil.getChildText(subjectNode);

                    results = SparqlUtils.getSparqlOutput("Queries/exacts.sparql", inContent, "${subject}", paramValue);
                    RDFXMLUtils.builldXml (exacts, null,results);

                    elements.item(i).appendChild(exacts);
                }
            }
        }

        return sectionNode;
    }


    //<-----


}
