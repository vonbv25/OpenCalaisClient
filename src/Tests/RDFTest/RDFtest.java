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

        String sample = "MySQL Community Edition is a freely downloadable version of the world's most popular open source database that is supported by an active community of open source developers and enthusiasts.\n" +
                "\n" +
                "MySQL delivers enterprise features, including:\n" +
                "\n" +
                "Partitioning to improve performance and management of very large database environments\n" +
                "Row-based/Hybrid Replication for improved replication security\n" +
                "Event Scheduler to create and schedule jobs that perform various database tasks\n" +
                "XPath Support\n" +
                "Dynamic General/Slow Query Log\n" +
                "Performance/Load Testing Utility (mysqlslap)\n" +
                "Improved! Full Text Search (faster, new dev templates)\n" +
                "Improved! Archive engine (better compression, more features)\n" +
                "Improved! User session and problem SQL identification\n" +
                "Improved! MySQL embedded library (libmysqld)\n" +
                "Additional INFORMATION_SCHEMA objects\n" +
                "Faster data import operations (parallel file load)\n" +
                "ACID Transactions to build reliable and secure business critical applications\n" +
                "Stored Procedures to improve developer productivity\n" +
                "Triggers to enforce complex business rules at the database level\n" +
                "Views to ensure sensitive information is not compromised\n" +
                "Information Schema to provide easy access to metadata\n" +
                "Pluggable Storage Engine Architecture for maximum flexibility\n" +
                "Archive Storage Engine for historical and audit data";
        String sample2 = "The company also declined to deny or confirm a report in The Boston Globe last month, which quoted a letter to State Street clients alerting them to a 42 percent decline " +
                "this year in the State Street Limited Duration Bond Fund for institutional investors.";
                String uri = OpenCalais.getCalaisRdfText(sample,OpenCalaisTest.APIKEY);

//        System.out.print(OpenCalais.getCalaisRdfText("" +
//                "http://d.opencalais.com/comphash-1/1778bdda-c512-315d-a937-b29edc876437"+".rdf"));
        model.read(new StringReader(uri), null);
        model.write(System.out);

//        ResultSetFormatter.out(sparqlUtils.getSparqlOutput("Queries/Person.sparql",uri));
//        ResultSetFormatter.out(sparqlUtils.getSparqlOutput("Queries/Geo.sparql",uri));
            ResultSetFormatter.out(sparqlUtils.getSparqlOutput("Queries/Technology.sparql",uri));

    }
}