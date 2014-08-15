package com.Client.Utils;


import com.Client.DocumentAnalyzer.Configuration;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSetRewindable;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.resultset.ResultSetMem;
import com.hp.hpl.jena.sparql.util.FmtUtils;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.xpath.*;

import com.hp.hpl.jena.query.ResultSet;
import org.w3c.dom.NodeList;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by OJT4 on 8/4/14.
 */
public class RDFXMLUtils {

    public static XPath getXPath(Document linkedData) {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();

        NameSpace nsctx = new NameSpace();
        NamedNodeMap attributes = linkedData.getDocumentElement().getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            String nodename = attr.getNodeName();
            nodename = nodename.substring(nodename.indexOf(':') + 1);
            nsctx.setMap (nodename, attr.getNodeValue());
        }
        xpath.setNamespaceContext(nsctx);
        return xpath;
    }


    public static String getVarValueAsString(QuerySolution rBind, String varName) {
        RDFNode obj = rBind.get(varName);

        if (obj == null)
            return "N/A";

        return FmtUtils.stringForRDFNode(obj);
    }

    public static Node getNodeByName(Node parent, String name)	{
        for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
            if(parent.getChildNodes().item(i).getNodeName().equals(name))
                return parent.getChildNodes().item(i);
        }
        return null;
    }


    public static void builldXml(Node paNode, String resultName,
                           ResultSet resultSet) {
        ResultSetRewindable resultSetRewindable = new ResultSetMem(resultSet);
        int numCols = resultSetRewindable.getResultVars().size();

        while (resultSetRewindable.hasNext()) {
            Node resultNode = paNode;
            if(resultName != null && !resultName.equals(""))
                resultNode = paNode.appendChild(paNode.getOwnerDocument()
                        .createElement(resultName));

            QuerySolution rBind = resultSetRewindable.nextSolution();
            for (int col = 0; col < numCols; col++) {
                String colName = (String) resultSet.getResultVars().get(col);
                String colValue = RDFXMLUtils.getVarValueAsString(rBind, colName);
                resultNode.appendChild(paNode.getOwnerDocument().createElement(colName));
                resultNode.getLastChild().appendChild(paNode.getOwnerDocument().createTextNode(colValue));
            }
        }

    }


    public static NodeList getNodesFromDoc(XPath xpath, String xpathString, Document linkedData) throws Exception {
        try {
            XPathExpression expr = xpath.compile(xpathString);
            return (NodeList) expr.evaluate(linkedData, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new IllegalArgumentException("xpathString provided in order to " +
                    "analyze XML was wrong empty " +
                    "or contained an error ("+xpathString+")", e);
        }
    }


    public static void serializeDoc(Document outdoc) throws Exception {
        try {
            XMLSerializer serializer = new XMLSerializer();
            serializer.setOutputCharStream(new FileWriter(Configuration.getCurrent_config().getOutputFilename()));
            serializer.serialize(outdoc);
        } catch (IOException e) {
            throw new IllegalArgumentException(" XML Serialization Failed or could not find configuration data", e);
        }

    }






}
