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
public class EnrichProducts extends AbstractEnricher {

    /**
     * Extract Products entity
     * @param productsNodes
     * @throws Exception
     */
    @Override
    public void enrich(Node productsNodes) throws Exception {
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
}
