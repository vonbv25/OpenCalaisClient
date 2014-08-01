package com.Client.Run;

import com.Client.Calais;
import com.Client.CalaisSoap;

/**
 * Created by OJT4 on 7/31/14.
 */
public class Run {
    public static void main(String[] args) {
        try { // Call Web Service Operation
            Calais service = new Calais();
            CalaisSoap port = service.getCalaisSoap();


            String paramsXML="<c:params xmlns:c=\"http://s.opencalais.com/1/pred/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n" +
                    "    <c:processingDirectives c:contentType=\"TEXT/RAW\" c:enableMetadataType=\"\" c:outputFormat=\"Text/Simple\"></c:processingDirectives>\n" +
                    "    <c:userDirectives c:allowDistribution=\"true\" c:allowSearch=\"true\" c:externalID=\"17cabs901\" c:submitter=\"ABC\"></c:userDirectives>\n" +
                    "    <c:externalMetadata></c:externalMetadata>\n" +
                    "</c:params>";
            String licenseID = "ksgda5hzzy6wz8xtcunjjsn5"; //replace this with your own license key
            String content = "hello world!";


            java.lang.String result = port.enlighten(licenseID, content, paramsXML);
            System.out.println("Result = "+result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
