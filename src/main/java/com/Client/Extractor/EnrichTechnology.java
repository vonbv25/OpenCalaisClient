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
public class EnrichTechnology extends AbstractEnricher {

    /**
     * Extract technology Entity
     * @param technologyNode
     * @throws Exception
     */
    @Override
    public void enrich(Node technologyNode) throws Exception {
        for (int i = 0; i < technologyNode.getChildNodes().getLength(); i++) {
            Node technology = technologyNode.getChildNodes().item(i);

            Node resourceNode = technology.getChildNodes().item(2);
            String resourceUri = DOMUtil.getChildText(resourceNode);
            resourceUri = resourceUri.substring(1, resourceUri.length() - 1);

            Node newnode = technology.getOwnerDocument().createElement("url");

            newnode.appendChild(technology.getOwnerDocument().createTextNode(resourceUri));
            technology.appendChild(newnode);
            technology.removeChild(resourceNode);



            Node subjectNode = RDFXMLUtils.getNodeByName(technology, "subject");
            technology.removeChild(subjectNode);

            //the resource URI must be in .rdf
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
}
