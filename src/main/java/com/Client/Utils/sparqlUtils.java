package com.Client.Utils;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetRewindable;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.resultset.ResultSetMem;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by OJT4 on 8/13/14.
 */
public class sparqlUtils {

    public static ResultSet getSparqlOutput(String file, String content) throws Exception {
        return getSparqlOutput(file, content, null, null);
    }


    public static ResultSet getSparqlOutput(String file, String content, String parameterName, String parameterValue) throws Exception {
        Reader reader = new StringReader(content);

        // Create an empty in-memory model and populate it from the graph
        Model model = ModelFactory.createMemModelMaker().createDefaultModel();
        model.read(reader, null); // null base URI, since model URIs are
        // absolute
        try {
            reader.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("(EE) could not close input reader", e);
        }

        // Create a new query
        String queryString = getSparqlQuery(file);

        if(parameterName != null && parameterValue != null){
            queryString = StringUtils.replace(queryString, parameterName, parameterValue);
        }

        com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results | Use as Debug option in order to see a text format of a table
        // ResultSetFormatter.out(System.out, results, query);

        // Important - free up resources used running the query
        qe.close();

        return results;
    }

    private static String getSparqlQuery(String filename) throws Exception {
        return StringUtils.getContentsFromResource("/resources/" + filename);
    }

}
