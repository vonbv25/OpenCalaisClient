package com.Client.Extractor;

import com.Client.DocumentAnalyzer.OpenCalais;
import com.Client.Utils.RDFXMLUtils;
import org.apache.xerces.util.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;

/**
 * Abstract class of Enrich Classes.
 *
 * Created by OJT4 on 8/15/14.
 */
public abstract class AbstractEnricher implements IEnricher {

    /**
     * @param xpath XML xpath of the linked data (resourceURI .rdf)
     * @param linkedData Document object of obtained linked Data
     * @param enrichMe Node of the enriched entities
     * @param rNodeNames Node name of the linked to be retrieved
     * @param section section of the linked data to be retrieved
     * @param entName set the name of the sub entity of the entity obtained from the linked data
     * @throws Exception
     */
    protected void retrivedFromLinked(XPath xpath, Document linkedData,
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
        nlist = RDFXMLUtils. getNodesFromDoc(xpath, rNodeNames, linkedData);

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


    /**
     *
     * @param entity Node of the entity where Events and facts will be retrieved
     * @param content the response output from Open Calais SOAP client in RDF format
     * @throws Exception
     */
    public void updateEventsAndFacts(Node entity, String content) throws Exception {
        System.out.println("Handling 'EventsAndFacts' ...");

        // subject uri refer to the id of the markup entity
        // contains the entity
        Node subject = entity.getChildNodes().item(2);
        String subjectURI = DOMUtil.getChildText(subject);
        subjectURI = subjectURI.substring(1, subjectURI.length() - 1);

        System.out.println(subjectURI);

        // use xpath to search for all the element linked to the entity
        // markup entity on calais api output RDF.
        Document contentDom = OpenCalais.stringToDom(content);
        XPath xpath = RDFXMLUtils.getXPath(contentDom);
        String xpathQuery = "//*[@rdf:resource='" + subjectURI + "']/..";
        NodeList nlist = RDFXMLUtils. getNodesFromDoc(xpath, xpathQuery, contentDom);

        // create section to add to the simple
        Node eventsAndFacts = entity.getOwnerDocument().createElement("EventsAndFacts");

        for (int i = 0; i < nlist.getLength(); i++) {
            Node typeNode = RDFXMLUtils.getNodeByName(nlist.item(i), "rdf:type");
            if(typeNode != null)	{ // if node has type
                String rdfType = typeNode.getAttributes().getNamedItem("rdf:resource").getTextContent();
                if(rdfType.indexOf("/type/em/r/") > -1) { // if type is a "Relational" element (Like Acquisition)
                    // an "event or fact" element in put xml for each event
                    Node eventNode = entity.getOwnerDocument().createElement("EventOrFact");

                    // subject URI will link to the markup entity of the Relational element
                    subjectURI = typeNode.getParentNode().getAttributes()
                            .getNamedItem("rdf:about").getTextContent();
                    // set rdfType to hold the relational type name.
                    rdfType = rdfType.substring(rdfType.lastIndexOf('/') + 1);

                    // search for the markup element using xpath
                    xpathQuery = "//c:subject[@rdf:resource='" + subjectURI + "']/..";
                    String markuptext = "";
                    NodeList nlistMarkup  = RDFXMLUtils. getNodesFromDoc(xpath, xpathQuery, contentDom);
                    for (int j = 0; j < nlistMarkup.getLength(); j++) {
                        Node typeMarkup = nlistMarkup.item(j);
                        markuptext += DOMUtil.getChildText(RDFXMLUtils.getNodeByName(typeMarkup, "c:prefix"))
                                + " " + DOMUtil.getChildText(RDFXMLUtils.getNodeByName(typeMarkup, "c:exact"))
                                + " " + DOMUtil.getChildText(RDFXMLUtils.getNodeByName(typeMarkup, "c:suffix"));
                    }

                    // create Type element for the ouput XML
                    Node tempnode = entity.getOwnerDocument().createElement("Type");
                    tempnode.appendChild(entity.getOwnerDocument().createTextNode(rdfType));
                    eventNode.appendChild(tempnode);

                    // create Markup element for the ouput XML
                    tempnode = entity.getOwnerDocument().createElement("Markup");
                    tempnode.appendChild(entity.getOwnerDocument().createTextNode(markuptext));
                    eventNode.appendChild(tempnode);

                    eventsAndFacts.appendChild(eventNode);
                }
            }
        }
        if(eventsAndFacts.getChildNodes().getLength() > 0)
            entity.appendChild(eventsAndFacts);
    }



}
