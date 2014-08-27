package com.Client.Extractor;

import com.Client.DocumentAnalyzer.OpenCalais;
import com.Client.Utils.RDFXMLUtils;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.xerces.util.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;

/**
 * Created by OJT4 on 8/15/14.
 */
public class EnrichSocialTags extends AbstractEnricher {


    /**
     * Extract Social tags
     * @param socialtags
     * @throws Exception
     */
    @Override
    public void enrich(Node socialtags) throws Exception {
        for (int i = 0; i < socialtags.getChildNodes().getLength(); i++) {
            Node socialtag = socialtags.getChildNodes().item(i);

            Node resourceNode = socialtag.getChildNodes().item(2);
            String resourceUri = DOMUtil.getChildText(resourceNode);
            resourceUri = resourceUri.substring(1, resourceUri.length() - 1);

            Node newnode = socialtag.getOwnerDocument().createElement("url");

            newnode.appendChild(socialtag.getOwnerDocument().createTextNode(resourceUri));
            socialtag.appendChild(newnode);
            socialtag.removeChild(resourceNode);

//            Node subjectNode = RDFXMLUtils.getNodeByName(technology, "subject");
//            technology.removeChild(subjectNode);

            // TODO: cannot get subject URI of social tags or subject is not working rather?
            //the resource URI must be in .rdf
            Document linkedData = OpenCalais.getCalaisRdf(resourceUri.substring(0, resourceUri.length() - 12
            ) + ".rdf");

            if(linkedData!=null) {
                XPath xpath = RDFXMLUtils.getXPath(linkedData);
                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:foaf") != null) {
                    retrivedFromLinked(xpath, linkedData, socialtag, "//foaf:homepage", null, "Homepage");
                    retrivedFromLinked(xpath, linkedData, socialtag, "//foaf:img", null, "Image");
                }

                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:owl") != null)
                    retrivedFromLinked(xpath, linkedData, socialtag, "//owl:sameAs", "RdfLinks", "Link");
                if(linkedData.getDocumentElement().getAttributes().getNamedItem("xmlns:foaf") != null){
                    retrivedFromLinked(xpath, linkedData, socialtag, "//foaf:page", "WebLinks", "Link");
                }
            }
        }

    }
}
