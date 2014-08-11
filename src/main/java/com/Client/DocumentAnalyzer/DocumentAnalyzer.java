package com.Client.DocumentAnalyzer;
import javax.xml.parsers.DocumentBuilderFactory;
import com.Client.Utils.RDFXMLUtils;
import com.Client.Utils.sparqlUtils;
import com.hp.hpl.jena.query.ResultSet;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.apache.xerces.util.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by OJT4 on 8/1/14.
 */
public class DocumentAnalyzer {

    private static int MAX_DOC_SIZE = Configuration.getCurrent_config().getMaxdocSize();


    public static boolean analyze(String filePath, String APIKEY) throws Exception{
        DocumentAnalyzer analyzer = new DocumentAnalyzer();
        String content = null;
         try {
              content = analyzer.readfile(filePath);
                if (content.length()>MAX_DOC_SIZE) {
                    throw new IllegalArgumentException("must be less than 1000 characters");
                }
             content = OpenCalais.getCalaisRDFText(content,APIKEY);
            if (content.isEmpty()) {
                return false;
            }
             Document outdoc = analyzer.getDocumentFromRdf(content);

             System.out.println(outdoc.toString());

             serializeDoc(outdoc);
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

            // build Geographies section to output XML
            enrichCompanies(createSection(
                    Response, "Companies", "Company",
                    "Queries/Company.sparql", content,"subject"), content);

            // build Geographies section to output XML
            enrichGeo(createSection(
                    Response, "Geographies", "Geography",
                    "Queries/Geo.sparql", content,"subject"));

            // build Products section to output XML
            enrichProduct(createSection(
                    Response, "Products", "Product",
                    "Queries/Products.sparql", content,"subject"));
            //build Technology section to output XML
            enrichTechnology(createSection(Response,"Technologies","technology",
                    "Queries/Technology.sparql",content,"subject"));
//
//            //build Technology section to output XML
//            enrichTechnology(createSection(Response,"ProgrammingLanguage","ProgrammingLanguages",
//                    "Queries/ProgrammingLanguage.sparql",content,"subject"));



            return xdoc;
        } catch (ParserConfigurationException e) {
            throw new IllegalArgumentException(" Error creating new DOM object for content", e);
        }
    }


    private String getTextfromOpenCalais(String content, String APIkey) throws Exception{
        return OpenCalais.getCalaisRDFText(content,APIkey);
    }




    //--->
    private void enrichTechnology(Node technologyNode) throws Exception {
        for (int i = 0; i < technologyNode.getChildNodes().getLength(); i++) {
            Node technology = technologyNode.getChildNodes().item(i);

            Node resourceNode = technology.getChildNodes().item(2);
            String resourceUri = DOMUtil.getChildText(resourceNode);
            resourceUri = resourceUri.substring(1, resourceUri.length() - 1);

            Node newnode = technology.getOwnerDocument().createElement("url");

            newnode.appendChild(technology.getOwnerDocument().createTextNode(resourceUri));
            technology.appendChild(newnode);
            technology.removeChild(resourceNode);



//            Node subjectNode = RDFXMLUtils.getNodeByName(technology, "subject");
//            technology.removeChild(subjectNode);

            Document linkedData = OpenCalais.getCalaisRdf(resourceUri + ".rdf");

            if(linkedData!=null) {
                XPath xpath = RDFXMLUtils.getXPath(linkedData);
                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:foaf") != null) {
                    retrivedFromLinked(xpath, linkedData, technology, "//foaf:homepage", null, "Homepage");
                    retrivedFromLinked(xpath, linkedData, technology, "//foaf:img", null, "Image");
                }

                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:owl") != null)
                    retrivedFromLinked(xpath, linkedData, technology, "//owl:sameAs", "RdfLinks", "Link");
                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:foaf") != null){
                    retrivedFromLinked(xpath, linkedData, technology, "//foaf:page", "WebLinks", "Link");
                }
            }
        }
    }


    //<-----

    private void enrichCompanies(Node companiesNodes, String content) throws Exception {
        for (int i = 0; i < companiesNodes.getChildNodes().getLength(); i++) {
            Node company = companiesNodes.getChildNodes().item(i);

            Node resourceNode = company.getChildNodes().item(2);
            String resourceUri = DOMUtil.getChildText(resourceNode);

            resourceUri = resourceUri.substring(1, resourceUri.length() - 1);
            System.out.println(resourceUri);

            Node newnode = company.getOwnerDocument().createElement("url");

            newnode.appendChild(company.getOwnerDocument().createTextNode(resourceUri));
            company.appendChild(newnode);
            company.removeChild(resourceNode);
            updateEventsAndFacts(company, content);

            Node subjectNode = RDFXMLUtils.getNodeByName(company, "subject");
            company.removeChild(subjectNode);

            Document linkedData = OpenCalais.getCalaisRdf(resourceUri + ".rdf");

           //test


            if(linkedData!=null) {
                XPath xpath = RDFXMLUtils.getXPath(linkedData);

                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:foaf") != null)
                    retrivedFromLinked(xpath, linkedData, company, "//foaf:homepage", null, "Homepage");

                retrivedFromLinked(xpath, linkedData, company, "//cld:competitor", "Competitors", "Competitor");
                retrivedFromLinked(xpath, linkedData, company, "//cld:personposition", "PersonsInPosition", "Person");

                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:owl") != null)
                    retrivedFromLinked(xpath, linkedData, company, "//owl:sameAs", "RdfLinks", "Link");

                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:foaf") != null)
                    retrivedFromLinked(xpath, linkedData, company, "//foaf:page", "WebLinks", "Link");
            }

        }
    }


    private void enrichGeo(Node placesNodes) throws Exception {
        for (int i = 0; i < placesNodes.getChildNodes().getLength(); i++) {
            Node place = placesNodes.getChildNodes().item(i);

            Node resourceNode = place.getChildNodes().item(2);
            String resourceUri = DOMUtil.getChildText(resourceNode);
            resourceUri = resourceUri.substring(1, resourceUri.length() - 1);

            Node newnode = place.getOwnerDocument().createElement("url");

            newnode.appendChild(place.getOwnerDocument().createTextNode(resourceUri));
            place.appendChild(newnode);
            place.removeChild(resourceNode);

            Node subjectNode = RDFXMLUtils.getNodeByName(place, "subject");
            place.removeChild(subjectNode);

            Document linkedData = OpenCalais.getCalaisRdf(resourceUri + ".rdf");
            if(linkedData!=null) {
                XPath xpath = RDFXMLUtils.getXPath(linkedData);
                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:foaf") != null)
                    retrivedFromLinked(xpath, linkedData, place, "//foaf:homepage", null, "Homepage");

                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:geo") != null) {
                    retrivedFromLinked(xpath, linkedData, place, "//geo:lat", null, "Latitude");
                    retrivedFromLinked(xpath, linkedData, place, "//geo:long", null, "Longitude");
                }

                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:owl") != null)
                    retrivedFromLinked(xpath, linkedData, place, "//owl:sameAs", "RdfLinks", "Link");
                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:foaf") != null)
                    retrivedFromLinked(xpath, linkedData, place, "//foaf:page", "WebLinks", "Link");
            }
        }
    }

    private void enrichProduct(Node productsNodes) throws Exception {
        for (int i = 0; i < productsNodes.getChildNodes().getLength(); i++) {
            Node product = productsNodes.getChildNodes().item(i);

            Node resourceNode = product.getChildNodes().item(2);
            String resourceUri = DOMUtil.getChildText(resourceNode);
            resourceUri = resourceUri.substring(1, resourceUri.length() - 1);

            Node newnode = product.getOwnerDocument().createElement("url");

            newnode.appendChild(product.getOwnerDocument().createTextNode(resourceUri));
            product.appendChild(newnode);
            product.removeChild(resourceNode);

            Node subjectNode = RDFXMLUtils.getNodeByName(product, "subject");
            product.removeChild(subjectNode);

            Document linkedData = OpenCalais.getCalaisRdf(resourceUri + ".rdf");

            if(linkedData!=null) {
                XPath xpath = RDFXMLUtils.getXPath(linkedData);
                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:foaf") != null) {
                    retrivedFromLinked(xpath, linkedData, product, "//foaf:homepage", null, "Homepage");
                    retrivedFromLinked(xpath, linkedData, product, "//foaf:img", null, "Image");
                }

                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:owl") != null)
                    retrivedFromLinked(xpath, linkedData, product, "//owl:sameAs", "RdfLinks", "Link");
                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:foaf") != null){
                    retrivedFromLinked(xpath, linkedData, product, "//foaf:page", "WebLinks", "Link");
                }
            }
        }
    }


    private void retrivedFromLinked(XPath xpath, Document linkedData,
                                  Node enrichMe, String rNodeNames, String section, String entName) throws Exception {
        Node sectionNode;
        if(section == null) {
            sectionNode = enrichMe;
        }
        else {
            sectionNode = enrichMe.getOwnerDocument().createElement(section);
            enrichMe.appendChild(sectionNode);
        }
        NodeList nlist;
        nlist = getNodesFromDoc(xpath, rNodeNames, linkedData);

        for (int j = 0; j < nlist.getLength(); j++) {
            Node rcompetitor = nlist.item(j);
            Node competitor = enrichMe.getOwnerDocument().createElement(entName);

            String xText;
            xText = DOMUtil.getChildText(rcompetitor);
            if(rcompetitor.getAttributes().getNamedItem("rdf:resource") != null)
                xText = rcompetitor.getAttributes().getNamedItem("rdf:resource").getNodeValue();
            competitor.appendChild(enrichMe.getOwnerDocument().createTextNode(xText));

            sectionNode.appendChild(competitor);
        }
    }

    private void updateEventsAndFacts(Node company, String content) throws Exception {
        System.out.println("Handling 'EventsAndFacts' ...");

        // subject uri refer to the id of the markup entity
        // contains the company
        Node subject = company.getChildNodes().item(2);
        String subjectURI = DOMUtil.getChildText(subject);
        subjectURI = subjectURI.substring(1, subjectURI.length() - 1);

        System.out.println(subjectURI);

        // use xpath to search for all the element linked to the company
        // markup entity on calais api output RDF.
        Document contentDom = OpenCalais.stringToDom(content);
        XPath xpath = RDFXMLUtils.getXPath(contentDom);
        String xpathQuery = "//*[@rdf:resource='" + subjectURI + "']/..";
        NodeList nlist = getNodesFromDoc(xpath, xpathQuery, contentDom);

        // create section to add to the simple
        Node eventsAndFacts = company.getOwnerDocument().createElement("EventsAndFacts");

        for (int i = 0; i < nlist.getLength(); i++) {
            Node typeNode = RDFXMLUtils.getNodeByName(nlist.item(i), "rdf:type");
            if(typeNode != null)	{ // if node has type
                String rdfType = typeNode.getAttributes().getNamedItem("rdf:resource").getTextContent();
                if(rdfType.indexOf("/type/em/r/") > -1) { // if type is a "Relational" element (Like Acquisition)
                    // an "event or fact" element in put xml for each event
                    Node eventNode = company.getOwnerDocument().createElement("EventOrFact");

                    // subject URI will link to the markup entity of the Relational element
                    subjectURI = typeNode.getParentNode().getAttributes()
                            .getNamedItem("rdf:about").getTextContent();
                    // set rdfType to hold the relational type name.
                    rdfType = rdfType.substring(rdfType.lastIndexOf('/') + 1);

                    // search for the markup element using xpath
                    xpathQuery = "//c:subject[@rdf:resource='" + subjectURI + "']/..";
                    String markuptext = "";
                    NodeList nlistMarkup = getNodesFromDoc(xpath, xpathQuery, contentDom);
                    for (int j = 0; j < nlistMarkup.getLength(); j++) {
                        Node typeMarkup = nlistMarkup.item(j);
                        markuptext += DOMUtil.getChildText(RDFXMLUtils.getNodeByName(typeMarkup, "c:prefix"))
                                + " " + DOMUtil.getChildText(RDFXMLUtils.getNodeByName(typeMarkup, "c:exact"))
                                + " " + DOMUtil.getChildText(RDFXMLUtils.getNodeByName(typeMarkup, "c:suffix"));
                    }

                    // create Type element for the ouput XML
                    Node tempnode = company.getOwnerDocument().createElement("Type");
                    tempnode.appendChild(company.getOwnerDocument().createTextNode(rdfType));
                    eventNode.appendChild(tempnode);

                    // create Markup element for the ouput XML
                    tempnode = company.getOwnerDocument().createElement("Markup");
                    tempnode.appendChild(company.getOwnerDocument().createTextNode(markuptext));
                    eventNode.appendChild(tempnode);

                    eventsAndFacts.appendChild(eventNode);
                }
            }
        }

        if(eventsAndFacts.getChildNodes().getLength() > 0)
            company.appendChild(eventsAndFacts);
    }


    private Node createSection(Node Response, String sectionName,
                               String elementName,
                               String sparqlFile, String inContent, String nodeName) throws Exception {

        System.out.println("Building " + sectionName + " Section");
        Document xdoc = Response.getOwnerDocument();
        Node sectionNode = xdoc.createElement(sectionName);
        Response.appendChild(sectionNode);
        ResultSet results = sparqlUtils.getSparqlOutput(sparqlFile, inContent);
        RDFXMLUtils.builldXml(sectionNode, elementName, results);

        if(sectionNode.getFirstChild() != null) {
            NodeList elements = sectionNode.getChildNodes();
            for (int i = 0; i < elements.getLength(); i++) {
                Node subjectNode = RDFXMLUtils.getNodeByName(elements.item(i), nodeName);
                if (subjectNode != null) {
                    Node exacts = subjectNode.getOwnerDocument().createElement("exacts");
                    String paramValue = DOMUtil.getChildText(subjectNode);

                    results = sparqlUtils.getSparqlOutput ("Queries/exacts.sparql", inContent, "${subject}", paramValue);
                    RDFXMLUtils.builldXml (exacts, null,results);

                    elements.item(i).appendChild(exacts);
                }
            }
        }
        return sectionNode;
    }

    private NodeList getNodesFromDoc(XPath xpath, String xpathString, Document linkedData) throws Exception {
        try {
            XPathExpression expr = xpath.compile(xpathString);
            return (NodeList) expr.evaluate(linkedData, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new IllegalArgumentException("xpathString provided in order to " +
                    "analyze XML was wrong empty " +
                    "or contained an error ("+xpathString+")", e);
        }
    }


    private static void serializeDoc(Document outdoc) throws Exception {
        try {
            XMLSerializer serializer = new XMLSerializer();
            serializer.setOutputCharStream(new FileWriter(Configuration.getCurrent_config().getOutputFilename()));
            serializer.serialize(outdoc);
        } catch (IOException e) {
            throw new IllegalArgumentException(" XML Serialization Failed or could not find configuration data", e);
        }

    }






}
