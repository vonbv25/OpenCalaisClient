package com.Client.DocumentAnalyzer;

import com.Client.Calais;
import com.Client.CalaisSoap;
import com.Client.Utils.CalaisREST;
import com.Client.Utils.StringUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;

import com.Client.DocumentAnalyzer.Configuration;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by OJT4 on 8/1/14.
 */
public class OpenCalais {

    private static final StringBuilder strParams = new StringBuilder();

    static {
        strParams.append("<c:params xmlns:c=\"http://s.opencalais.com/1/pred/\"");
        strParams.append(" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">");
        strParams.append("<c:processingDirectives c:enableMetadataType=\"GenericRelations,SocialTags\" c:contentType=\"${CONTENT_TYPE}\" c:outputFormat=\"${OUTPUT_FORMAT}\"></c:processingDirectives>");
        strParams.append("<c:userDirectives c:allowDistribution=\"true\" c:allowSearch=\"true\" c:externalID=\"17cabs901\" c:submitter=\"${SUBMITTER}\"></c:userDirectives>");
        strParams.append("<c:externalMetadata c:caller=\"SemanticProxy\"/>");
        strParams.append("</c:params>");
    }

    public static String getCalaisRDFText(String content, String APIKEY)  {

        try { // Call Web Service Operation
            Calais service = new Calais();
            CalaisSoap port = service.getCalaisSoap();
            String result = port.enlighten(APIKEY, content, getParamsXml());
            return result;

        } catch (Exception ex) {
            throw new IllegalArgumentException("Error encoding URL",ex);
        }
    }


    private static String getParamsXml() throws Exception {
        String params = strParams.toString();
        params = StringUtils.replace(params, "${CONTENT_TYPE}",
                Configuration.getCurrent_config().getContentType());
        params = StringUtils.replace(params, "${OUTPUT_FORMAT}",
                Configuration.getCurrent_config().getOutputFormat());
        params = StringUtils.replace(params, "${SUBMITTER}",
                Configuration.getCurrent_config().getSubmitter());
        return params;
    }

    public static Document stringToDom(String xmlSource) throws Exception {

        if(xmlSource == null)
            return null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            return builder.parse( new InputSource(new StringReader(xmlSource)));

        } catch (ParserConfigurationException e) {
            throw new IllegalArgumentException("Could not create a document builder due to configuration error", e);
        } catch (IOException e) {
            throw new IllegalArgumentException(" Could not parse string into XML output", e);
        }
    }

    public static Document getCalaisRdf(String content) throws Exception {
        return stringToDom(CalaisREST.getCalaisRdfText(content));
    }

















}
