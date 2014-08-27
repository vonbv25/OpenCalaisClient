package RDFTest;

import DocumentAnalyzer.OpenCalaisTest;
import com.Client.DocumentAnalyzer.OpenCalais;
import com.Client.Utils.CalaisREST;
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

        String sample = "BAGHDAD, Iraq (CNN) -- Saddam Hussein, the former Iraqi dictator who spent his last years in captivity after his ruthless regime was toppled by the U.S.-led coalition in 2003, was hanged before dawn Saturday for crimes committed in a brutal crackdown during his reign.\n" +
                "\n" +
                "The execution took place shortly after 6 a.m. (10 p.m. Friday ET), Iraq's national security adviser, Mowaffak al-Rubaie, told Iraqi television.\n" +
                "\n" +
                "\"This dark page has been turned over,\" Rubaie said. \"Saddam is gone. Today Iraq is an Iraq for all the Iraqis, and all the Iraqis are looking forward. ... The [Hussein] era has gone forever.\" (Watch noose placed around Hussein's neck Video)\n" +
                "\n" +
                "Al-Iraqiya state television aired videotape of Hussein's last moments several hours after the execution.\n" +
                "\n" +
                "The video showed Hussein, dressed in a black overcoat, being led into a room by three masked guards.\n" +
                "\n" +
                "The broadcast only showed the execution to the point where the noose was placed over Hussein's head and tightened around his neck. No audio was heard.\n" +
                "\n" +
                "Rubaie, who witnessed the execution, said the former leader was \"strangely submissive\" to the process.\n" +
                "\n" +
                "\"He was a broken man,\" he said. \"He was afraid. You could see fear in his face.\"\n" +
                "\n" +
                "Rubaie said that Hussein carried with him a copy of the Quran and asked that it be given to \"a certain person.\" Rubaie did not identify that person.\n" +
                "\n" +
                "On Al-Arabiya television, Rubaie said the execution took place at the 5th Division intelligence office in Qadhimiya. He said Hussein refused to wear a black hood over his head before execution and told him \"don't be afraid.\" (Watch Rubaie describe Hussein's final moments Video)\n" +
                "\n" +
                "White House deputy press secretary Scott Stanzel said President Bush was asleep when the execution took place and was not awakened. The president had been briefed by national security adviser Stephen Hadley before retiring and was aware the hanging was imminent, Stanzel said.\n" +
                "\n" +
                "The White House issued a statement praising the Iraqi people for giving Hussein a fair trial.\n" +
                "\n" +
                "\"Fair trials were unimaginable under Saddam Hussein's tyrannical rule,\" Bush's statement read. \"It is a testament to the Iraqi people's resolve to move forward after decades of oppression that, despite his terrible crimes against his own people, Saddam Hussein received a fair trial.\" (Full story)\n" +
                "\n" +
                "The execution took place outside the heavily fortified Green Zone, Rubaie said, and no Americans were present.\n" +
                "\n" +
                "\"It was an Iraqi operation from A to Z,\" he said. \"The Americans were not present during the hour of the execution. They weren't even in the building.\"\n" +
                "\n" +
                "He added that \"there were no Shiite or Sunni clerics present, only the witnesses and those who carried out the actual execution were present.\"\n" +
                "\n" +
                "Hussein was hanged for his role in the 1982 Dujail massacre, in which 148 Iraqis were killed after a failed assassination attempt against the then-Iraqi president. (Watch what happened in Dujail Video)\n" +
                "\n" +
                "Two other co-defendants -- Barzan Hassan, Hussein's half-brother, and Awwad Bandar, the former chief judge of the Revolutionary Court -- were also found guilty and had been expected to face execution with Hussein, but Rubaie said their executions were postponed.\n" +
                "\n" +
                "\"We chose to postpone Barzan and Awwad's execution to a later date because we wanted to have this day to have an historic distinction,\" he said. \"We wanted to have one specific date for Saddam so people remember this date to be linked to Saddam's execution and nothing else.\"\n" +
                "\n" +
                "Rubaie said the execution was videotaped and photographed extensively from the time Hussein was transferred from U.S. to Iraqi custody until he was dead.\n" +
                "\n" +
                "Many of those who witnessed the execution celebrated in the aftermath. (Full story)\n" +
                "\n" +
                "\"Saddam's body is in front me,\" said an official in the prime minister's office when CNN telephoned. \"It's over.\"\n" +
                "\n" +
                "In the background, Shiite chanting could be heard. When asked about the chanting, the official said, \"These are employees of the prime minister's office and government chanting in celebration.\" (Watch what Hussein's death could mean in Iraq Video)\n" +
                "\n" +
                "He said that celebrations broke out after Hussein was dead, and that there was \"dancing around the body.\"\n" +
                "\n" +
                "Iraqi-Americans celebrated in the street in Dearborn, Michigan, home to the largest concentration of Iraqis in the United States. (Watch Iraqi-Americans dancing, kissing and singing in the streets Video)\n" +
                "\n" +
                "Prime Minister Nuri al-Maliki did not attend the execution, according to an adviser to the prime minister who was interviewed on state television.\n" +
                "\n" +
                "\"It's a very solemn moment for me,\" Feisal Istrabadi, Iraq's U.N. ambassador, said on CNN's \"Anderson Cooper 360.\" \"I can understand why some of my compatriots may be cheering. I have friends whose particular people I can think of who have lost 10, 15, 20 members of their family, more.\n" +
                "\n" +
                "\"But for me, it's a moment really of remembrance of the victims of Saddam Hussein.\"\n" +
                "\n" +
                "Friday evening, a U.S. district judge refused a request to stay the execution.\n" +
                "\n" +
                "Attorney Nicholas Gilman said in an application for a restraining order, filed Friday in U.S. District Court in Washington, that a stay would allow Hussein \"to be informed of his rights and take whatever action he can and may wish to pursue.\"\n" +
                "\n" +
                "Munir Haddad, a judge on the appeals court that upheld the former dictator's death sentence, called Gilman's filing \"rubbish,\" and said, \"It will not delay carrying out the sentence,\" which he called \"final.\"\n" +
                "\n" +
                "Throughout the day, there were conflicting reports about who had custody of Hussein. Giovanni di Stefano, one of Hussein's defense attorneys, told CNN the U.S. military officially informed him that the former Iraqi dictator had been transferred to Iraqi custody, but that the move in U.S. court could have meant that Hussein was back in U.S. custody.\n" +
                "\n" +
                "There had been speculation that Hussein would be executed before Eid Al-Adha -- a holiday period that means Feast of the Sacrifice, celebrated by Muslims around the world at the climax of the hajj pilgrimage to Mecca. The law does not permit executions to be carried out during religious holidays.\n" +
                "\n" +
                "Eid began Saturday for Sunnis and begins Sunday for Shiites. It lasts for four days. Hussein was a Sunni Muslim.\n" +
                "\n" +
                "Meeting with half-brothers\n" +
                "\n" +
                "Another defense lawyer, Badie Aref, told CNN that Hussein met with two of his half-brothers in his cell on Thursday and passed on messages and instructions to his family.\n" +
                "\n" +
                "\"President Saddam was just bracing for the worst, so he wanted to see his brothers and pass on some messages and instructions to his family,\" Aref said. The half brothers who visited were Sabawi and Wathban Ibrahim Hassan al-Tikriti, he said.\n" +
                "\n" +
                "He never asked to see anyone else -- not even his wife, said his lawyers. She was the mother of his five children.\n" +
                "\n" +
                "Aref said the U.S. soldiers guarding Hussein on Tuesday took away a radio he kept in his cell so he could not hear news reports about his death sentence, which was confirmed that day.\n" +
                "\n" +
                "\"They did not want him to hear the news from the appeals court upholding the sentence,\" he said. \"They gave him back the radio on Wednesday.\"\n" +
                "\n" +
                "Aref said Saddam found out about the appeals court verdict \"a few hours after it was announced.\"\n" +
                "\n" +
                "Crimes against humanity\n" +
                "\n" +
                "Hussein was convicted on November 5 of crimes against humanity in connection with the killings of 148 people in the town of Dujail after an attempt on his life.\n" +
                "\n" +
                "The dictator was found guilty of murder, torture and forced deportation.\n" +
                "\n" +
                "The Dujail episode falls within 12 of the worst cases out of 500 documented \"baskets of crimes\" during the Hussein regime.\n" +
                "\n" +
                "The U.S. State Department says torture and extrajudicial killings followed the Dujail killings and that 550 men, women and children were arrested without warrants.";
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
                String uri = OpenCalais.getCalaisRDFText(sample2, OpenCalaisTest.APIKEY);

        model.read(new StringReader(uri), null);
        model.write(System.out);

//       System.out.println(CalaisREST.getCalaisRdfText("http://d.opencalais.com/genericHasher-1/05ddd98f-097f-3701-a737-6fd2555f411c.rdf"));

//        ResultSetFormatter.out(SparqlUtils.getSparqlOutput("Queries/Person.sparql",uri));
        ResultSetFormatter.out(SparqlUtils.getSparqlOutput("Queries/Company.sparql",uri));
            ResultSetFormatter.out(SparqlUtils.getSparqlOutput("Queries/Products.sparql", uri));
    }
}