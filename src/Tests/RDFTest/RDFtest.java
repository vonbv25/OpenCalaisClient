package RDFTest;

import DocumentAnalyzer.OpenCalaisTest;
import com.Client.DocumentAnalyzer.OpenCalais;
import com.Client.Utils.sparqlUtils;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import org.junit.Test;

import java.io.StringReader;

/**
 * Created by OJT4 on 8/4/14.
 */
public class RDFtest {

    @Test
    public void Test1() throws Exception{
        Model model = ModelFactory.createDefaultModel();
        String uri = OpenCalais.getCalaisRDFText(OpenCalaisTest.content,OpenCalaisTest.APIKEY);

        model.read(new StringReader(uri), null);
        model.write(System.out,"TURTLE");
//        ResultSetFormatter.out(sparqlUtils.getSparqlOutput("Queries/entities.sparql",uri));




    }
}
