package DocumentAnalyzer;

import com.Client.DocumentAnalyzer.OpenCalais;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;


/**
 * Created by OJT4 on 8/1/14.
 */

public class OpenCalaisTest {

    public static String CNN_path = "C:\\Users\\OJT4\\Documents\\OpenCalaisClient\\src\\Tests\\resources\\CNN.txt";

    public static String content = " LOS ANGELES, California (CNN) -- Patrick Swayze, whose hunky good looks and sympathetic performances in such films as \"Dirty Dancing\" and \"Ghost\" made him a romantic idol to millions, died Monday. He was 57.\n" +
            "Patrick Swayze's doctor said in March 2008 that Swayze was suffering from pancreatic cancer.\n" +
            "\n" +
            "Patrick Swayze's doctor said in March 2008 that Swayze was suffering from pancreatic cancer.\n" +
            "\n" +
            "Swayze died of pancreatic cancer, his publicist, Annett Wolf, told CNN.\n" +
            "\n" +
            "\"Patrick Swayze passed away peacefully today with family at his side after facing the challenges of his illness for the last 20 months,\" Wolf said in a statement Monday.\n" +
            "\n" +
            "Swayze's doctor, Dr. George Fisher, revealed in early March 2008 that Swayze was fighting the disease.\n" +
            "\n" +
            "Most recently, Swayze starred in A&E network's \"The Beast,\" which debuted in January. He agreed to take the starring role of an undercover FBI agent before his diagnosis. The network agreed to shoot an entire season of the show after Swayze responded well to cancer treatment. Video Watch report on the toll of pancreatic cancer »\n" +
            "\n" +
            "In an interview with ABC's Barbara Walters in January, Swayze said his work on that show was exhausting, requiring 12-hour workdays in Chicago, Illinois, doing his own stunts. But he said the show's character \"just felt right for my soul.\" iReport.com: Send us your memories of Swayze\n" +
            "\n" +
            "\"If I leave this Earth, I want to leave this Earth just knowing I've tried to give something back and tried to do something worthwhile with myself,\" Swayze told Walters, when asked why he decided to do the show. \"And that keeps me going, that gets me up in the morning. My work ... is my legacy.\"\n" +
            "\n" +
            "\"The Beast\" was canceled in June because of Swayze's illness, after doctors told him the cancer had spread to his liver.\n" +
            "Don't Miss\n" +
            "\n" +
            "    * KTLA:  Patrick Swayze dies after battle with cancer\n" +
            "    * LIFE: Photo gallery of Swayze\n" +
            "\n" +
            "\"We are saddened by the loss of one of our generation's greatest talents and a member of the A&E family,\" a statement from the network said. \"Patrick's work on 'The Beast' was an inspiration to us all. He will be greatly missed and our thoughts are with his wife, Lisa, and his entire family during this difficult time.\"\n" +
            "\n" +
            "Swayze was mostly known for a handful of supporting roles when he broke through with his performance as dance instructor Johnny Castle in 1987's \"Dirty Dancing.\"\n" +
            "\n" +
            "Co-star Jennifer Grey, who played his young lover, \"Baby\" Houseman, in the film, described Swayze as \"gorgeous and strong.\"\n" +
            "\n" +
            "\"Patrick was a rare and beautiful combination of raw masculinity and amazing grace. ... He was a real cowboy with a tender heart. He was fearless and insisted on always doing his own stunts, so it was not surprising to me that the war he waged on his cancer was so courageous and dignified,\" Grey said in a statement.\n" +
            "\n" +
            "\"When I think of him, I think of being in his arms when we were kids, dancing, practicing the lift in the freezing lake, having a blast doing this tiny little movie we thought no one would ever see. My heart goes out to his wife and childhood sweetheart, Lisa Niemi, to his mom, Patsy, and to the rest of their family.\"\n" +
            "\n" +
            "Three years later, he became an even bigger star with the movie \"Ghost,\" in which he played investment banker Sam Wheat, who dies and learns to tap into his unspoken feelings for his partner, Molly Jensen, played by Demi Moore.\n" +
            "\n" +
            "\"Patrick you are loved by so many and your light will forever shine in all of our lives,\" Moore said in a statement.\n" +
            "\n" +
            "\"In the words of Sam to Molly. 'It's amazing Molly. The love inside, you take it with you.' I will miss you.\"\n" +
            "\n" +
            "\"Ghost\" won Whoopi Goldberg an Oscar and helped make Swayze People magazine's \"Sexiest Man Alive\" in 1991.\n" +
            "\n" +
            "\"Patrick was a really good man, a funny man and one to whom I owe much that I can't ever repay,\" Goldberg said in a statement. \"I believe in 'Ghost's message, so he'll always be near.\" Quiz: How well do you know Patrick Swayze? »\n" +
            "\n" +
            "Swayze told Entertainment Weekly in 1990 that, \"The movies that have had the most powerful effects on my life have been about romantic characters.\"\n" +
            "\n" +
            "He expanded on the effort he put into love scenes for People in 1991.\n" +
            "\n" +
            "\"It's possibly the scariest thing I do,\" he said, \"doing something so personal and giving people out there the opportunity to see if you're a good kisser or not.\"\n" +
            "\n" +
            "Patrick Wayne Swayze was born on August 18, 1952, in Houston, Texas. His father was an engineering draftsman; his mother was a ballet dancer and later the director of the Houston Ballet Dance Company.\n" +
            "\n" +
            "She led her son into the dancing world, which wasn't always easy for a Texas male. The young Swayze played football, practiced martial arts and was an accomplished diver and track star while growing up, though he was good enough at dance to earn a college scholarship.\n" +
            "\n" +
            "After playing Prince Charming in an early 1970s version of \"Disney on Ice,\" Swayze returned to Houston, where he met Lisa Niemi, a student of his mother's. The two married in 1975 and moved to New York to pursue their careers. Video Watch a look back on Swayze's career »\n" +
            "\n" +
            "Swayze seemed set on a dance career: He studied with the prestigious Joffrey Ballet and joined another company, the Eliot Feld Ballet Company. But surgery for an old football injury ended his ballet career and he turned to acting, nabbing the lead role of Danny Zuko in the long-running Broadway production of \"Grease\" in 1978, about the time the movie starring John Travolta was hitting theaters. \"Grease\" earned Swayze some Hollywood attention, and he and Niemi moved West.\n" +
            "\n" +
            "After a couple of bit parts, including one in a 1981 episode of \"M*A*S*H,\" Swayze picked up the role of Darrel Curtis in Francis Ford Coppola's 1983 film \"The Outsiders.\"\n" +
            "\n" +
            "The movie included future stars Matt Dillon, Rob Lowe, Ralph Macchio, Emilio Estevez and Tom Cruise.\n" +
            "\n" +
            "Swayze also was one of the leads in 1984's \"Red Dawn,\" about teenagers defending their town from a Soviet attack on America.\n" +
            "\n" +
            "\"Not only did we lose a fine actor today, I lost my older 'Outsiders' brother,\" actor C. Thomas Howell, who also starred with Swayze in \"Red Dawn\" and \"Grandview, U.S.A.\"\n" +
            "\n" +
            "But it was with \"Dirty Dancing\" that Swayze hit it big. The film about a girl's coming of age at a Catskills, New York, resort in the early '60s was intended for a limited release but became one of the decade's biggest sleeper hits and made Swayze and Grey household names.\n" +
            "\n" +
            "The film gave birth to a catchphrase -- \"Nobody puts Baby in a corner,\" spoken by Swayze's character to Grey's domineering father -- and led to a follow-up, 2004's \"Dirty Dancing: Havana Nights.\"\n" +
            "\n" +
            "Swayze even sang a Top 10 hit, \"She's Like the Wind,\" on the film's soundtrack.\n" +
            "\n" +
            "Swayze, known as a down-to-earth, nice-guy actor, was determined not to follow a predictable career path. He followed \"Dirty Dancing\" with \"Road House\" (1989), in which he played a manager of a rough-and-tumble bar (the film was particularly popular on late-night cable).\n" +
            "\n" +
            "He succeeded \"Ghost\" with \"Point Break\" (1991), about a group of thieves; \"City of Joy\" (1992), in which he played a doctor in a poverty-stricken Indian village; and \"To Wong Foo, Thanks for Everything, Julie Newmar\" (1995), in which he starred as a drag queen. See timeline of Swayze's life »\n" +
            "\n" +
            "\"I don't want to be Mr. Romantic Leading Man. I don't want to be the Dance Dude. I don't want to be the Action Guy. If I had to do any one of those all my life, it'd drive me crazy,\" he told Entertainment Weekly in a 1990 interview.\n" +
            "\n" +
            "Swayze's career diminished in the late '90s. He broke both legs in 1997 while making the film \"Letters From a Killer,\" and went into rehab for a drinking problem.\n" +
            "\n" +
            "In 2000, he was flying in his twin-engine plane when it depressurized; Swayze landed in a housing development in Arizona. Though some witnesses said he appeared intoxicated, he was later revealed to have been suffering from hypoxia, related to the depressurization and his three-pack-a-day cigarette habit.\n" +
            "\n" +
            "Swayze re-established his knack for picking sleepers with \"Donnie Darko\" (2001), a dark film about a troubled student that became a sensation on video. Swayze played a creepy motivational speaker and won raves.\n" +
            "\n" +
            "Swayze's more recent work included a TV version of \"King Solomon's Mines\" and 2007's \"Christmas in Wonderland.\"\n" +
            "\n" +
            "Though he still made hearts flutter -- 22-year-old Scarlett Johansson, on receiving Harvard's Hasty Pudding Award in February 2007, said her dream date was \"probably Patrick Swayze, my dream come true\" -- Swayze wasn't too impressed with himself.";

    public static String APIKEY=  "ksgda5hzzy6wz8xtcunjjsn5";

    public static String output = null;

    @Before
    public void setUp() throws Exception {
        output = OpenCalais.getCalaisRDFText(content, APIKEY);
    }
    @Test
    public void getCalaisRDFTextTest(){

        Assert.assertFalse(output.isEmpty());
       // System.out.println(output);
    }

    @Test
    public void getCalaisRdfTest() throws Exception {
        System.out.println(output);
    }







}
