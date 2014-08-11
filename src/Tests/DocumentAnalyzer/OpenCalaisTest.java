package DocumentAnalyzer;

import com.Client.DocumentAnalyzer.DocumentAnalyzer;
import com.Client.DocumentAnalyzer.OpenCalais;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;


/**
 * Created by OJT4 on 8/1/14.
 */

public class OpenCalaisTest {

    public static String CNN_path = "C:\\Users\\OJT4\\Documents\\OpenCalaisClient\\src\\Tests\\resources\\CNN.txt";

    public static String APIKEY=  "ksgda5hzzy6wz8xtcunjjsn5";

    public static String output = null;

    public static String content ="C:\\Users\\OJT4\\Documents\\OpenCalaisClient\\src\\Tests\\DocumentAnalyzer\\CNN.txt";
    @Before
    public void setUp() throws Exception {
        output = OpenCalais.getCalaisRDFText(content, APIKEY);
    }

    @Test
    public void getCalaisRdfTest() throws Exception {
        Assert.assertFalse(!DocumentAnalyzer.analyze(content,APIKEY));
}







}
