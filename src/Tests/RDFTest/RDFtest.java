package RDFTest;

import DocumentAnalyzer.OpenCalaisTest;
import com.Client.DocumentAnalyzer.OpenCalais;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import org.junit.Test;

import java.io.StringReader;
import java.sql.ResultSet;

/**
 * Created by OJT4 on 8/4/14.
 */
public class RDFtest {

    @Test
    public void Test1() throws Exception{
        Model model = ModelFactory.createDefaultModel();
        String uri = OpenCalais.getCalaisRDFText(OpenCalaisTest.content,OpenCalaisTest.APIKEY);
//        System.out.print(uri);

      model.read(new StringReader(uri),null);

//
       model.write(System.out, "TURTLE");


    }
}
