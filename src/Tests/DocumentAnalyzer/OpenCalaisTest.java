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

    public static String content = "Prosecutors at the trial of former Liberian President Charles Taylor "
            + " hope the testimony of supermodel Naomi Campbell "
            + " will link Taylor to the trade in illegal conflict diamonds, "
            + " which they say he used to fund a bloody civil war in Sierra Leone.";

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
