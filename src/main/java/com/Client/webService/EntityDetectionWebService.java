package com.Client.webService;

import com.Client.DocumentAnalyzer.DocumentAnalyzer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


/**
 * Created by OJT4 on 8/19/14.
 */

@Path("/entitydetectionwebservice")
public class EntityDetectionWebService {

    //TODO

    @Path("{filepath},{APIKEY}")
    @GET
    @Produces("application/xml")
    public Boolean anaylize( @PathParam("filepath")String file, @PathParam("APIKEY")String APIKEY)
            throws Exception{

        return DocumentAnalyzer.analyze(file,APIKEY);

    }


}
