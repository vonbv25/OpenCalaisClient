package com.Client.Extractor;

import com.Client.DocumentAnalyzer.OpenCalais;
import com.Client.Utils.RDFXMLUtils;
import org.apache.xerces.util.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;

/**
 * Created by OJT4 on 8/15/14.
 */
public class EnrichCompanies extends AbstractEnricher {

    private String content = null;

    /**
     * Requires the response output from Open Calais
     * SOAP client in RDF format for events and facts section
     * @param content response output from Open Calais SOAP client. in RDF format
     */
    public EnrichCompanies(String content){
        this.content = content;
    }

    /**
     * Extract companies and its corresponding events and facts
     * @param companiesNodes
     * @throws Exception
     */
    @Override
    public void enrich(Node companiesNodes) throws Exception{
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

            //the resource URI must be in .rdf
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
}
