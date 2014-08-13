package com.Client.Utils;

import com.Client.DocumentAnalyzer.Configuration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by OJT4 on 8/13/14.
 */
public class CalaisREST {

    /**
     * Submit an API call using REST service
     * @param key
     * @param content
     * @param paramsXML
     * @return
     * @throws Exception
     */
    public String submit(String key, String content, String paramsXML) throws Exception{
        try {
            ClientConfig clientConfig = new DefaultClientConfig();
            Client client = Client.create(clientConfig);
            System.out.println("Submit Calais API Call ... ");
            String body = "licenseID=" + URLEncoder.encode(key, "UTF-8")
                    + "&content=" + URLEncoder.encode(content, "UTF-8")
                    + "&paramsXML=" + URLEncoder.encode(paramsXML, "UTF-8");
            WebResource webResource = client
                    .resource(Configuration.getCurrent_config().getCalaisRestUri());
            webResource.accept(new String[] { "application/xml" });

            // body is a hard-coded string, with replacements for the variable bits
            ClientResponse response = webResource.post(ClientResponse.class, body);

            if(response.getStatus() == 200)
                return response.getEntity(String.class);

            System.out.println(StringUtils.replace(
                    Configuration.getCurrent_config().getStatusWarning()
                    , "$status", response.getStatus()));
            System.out.println(response.getEntity(String.class));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(" Error encoding URL", e);
        } catch (IOException e) {
            throw new IllegalArgumentException(" Could not parse string into XML output", e);
        }
        return null;

        }

    /**
     * REST service Resoponse for URL
     * @param url
     * @return
     * @throws Exception
     */
    public static String getCalaisRdfText(String url) throws Exception {
        ClientConfig clientConfig = new DefaultClientConfig();
        Client client = Client.create(clientConfig);

        System.out.println("Submit Linked Data Call (" + url + ") ... ");

        WebResource webResource = client.resource(url);
        webResource.accept(new String[] { "application/xml" });

        // body is a hard-coded string, with replacements for the variable bits
        ClientResponse response = webResource.get(ClientResponse.class);

        if(response.getStatus() == 200)
            return response.getEntity(String.class);

        System.out.println(StringUtils.replace(Configuration.getCurrent_config().getStatusWarning()
                , "$status", response.getStatus()));
        System.out.println(response.getEntity(String.class));
        return null;
    }

}

