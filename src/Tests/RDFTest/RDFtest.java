package RDFTest;

import DocumentAnalyzer.OpenCalaisTest;
import com.Client.DocumentAnalyzer.OpenCalais;
import com.Client.Utils.SparqlUtils;
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
        String sample2 = "Probably the most popular freeware cleaner globally with over 1 billion downloads since its launch in 2003. Piriform’s CCleaner is a quick and easy to use program which makes your computer faster, more secure and more reliable. CCleaner removes cookies, temporary files and various other unused data that clogs up your operating system. This frees up valuable hard disk space allowing your system to run faster. Removing this data also protects your anonymity meaning you can browse online more securely. The built in Registry Cleaner fixes errors and broken settings to make your computer more stable. The simple, intuitive UI and rapid but powerful cleaning make CCleaner a favourite among novices and techies alike. Professional, Network, Business and Technician Editions of CCleaner are also available for serious users.\n" +
                "\n" +
                "A Faster Computer\n" +
                "\n" +
                "Browsing the internet your computer picks up a whole host of unnecessary files, cookies and history. The same thing happens when you run most programs on your computer – lots of temporary files and settings are saved. CCleaner removes these unused files and settings to free up valuable hard drive space, enabling your system to run faster. The default settings only focus on typical junk files and locations so you’re unlikely to lose important information unless you alter these. A Startup Cleaner also helps you to identify and remove unused programs running in the background when you start your computer. This makes the startup time shorter and puts less strain on your hard drive throughout use. You can read more about startup support here.\n" +
                "\n" +
                "Less Crashes & System Errors\n" +
                "\n" +
                "If you notice lots of system freezes, error messages and often experience crashes, the chances are your registry become cluttered with unused files and broken settings. CCleaner’s fully featured Registry Cleaner identifies these issues and fixes them. The registry analysis takes seconds and cleaning just a little bit longer. Prompts advise you when to save backups to avoid losing important data.\n" +
                "\n" +
                "More Secure Browsing\n" +
                "\n" +
                "Advertisers and websites track your behaviour online with cookies. Saved passwords, cached data and internet histories make your identity less secure. CCleaner removes these files to make your browsing experience confidential, meaning you are less likely to suffer from identity theft and/or online fraud. The military grade Drive Wiper ensures any data you want to be permanently deleted, stays deleted.\n" +
                "\n" +
                "Customisable Cleaning\n" +
                "\n" +
                "CCleaner Tools and Options tabs allow you to customise cleaning options to fit your needs. If you are not a savvy user, you might want to stick with the safe default settings. Advanced users can uninstall unwanted programs, select which cookies to save, customise cleaning settings and setup system monitoring.\n" +
                "\n" +
                "Summary\n" +
                "\n" +
                "CCleaner has earned its position at the top of the freeware cleaners offering the sort of features premium programs struggle to compete with. The clean, intuitive UI makes it one of the easiest programs to use, but don’t let its size and simplicity fool you. This is a powerful utility for improving your computers performance. Piriform’s regular update process means CCleaner is constantly improving and its popularity will continue to grow.";
                String uri = OpenCalais.getCalaisRDFText(sample, OpenCalaisTest.APIKEY);

        model.read(new StringReader(uri), null);
        model.write(System.out,"TURTLE");

//        ResultSetFormatter.out(SparqlUtils.getSparqlOutput("Queries/Person.sparql",uri));
//        ResultSetFormatter.out(SparqlUtils.getSparqlOutput("Queries/Geo.sparql",uri));
            ResultSetFormatter.out(SparqlUtils.getSparqlOutput("Queries/SocialTag.sparql", uri));
    }
}