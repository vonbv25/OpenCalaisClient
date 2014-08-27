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
public class EnrichGeo extends AbstractEnricher {


    /**
     * Extract entities pertaining to geographies
     * example:
     * @throws Exception
     */
    @Override
    public void enrich(Node placesNodes) throws Exception {
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

            //the resource URI must be in .rdf
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
}
