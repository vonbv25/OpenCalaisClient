package com.Client.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by OJT4 on 8/1/14.
 */
public class StringUtils {

    public static String replace(String text, String repl, Object with) {
        int max = 1;
        StringBuffer buf = new StringBuffer(text.length());
        int start = 0, end = 0;
        while ((end = text.indexOf(repl, start)) != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();

            if (--max == 0) {
                break;
            }
        }
        buf.append(text.substring(start));
        return buf.toString();
    }


    public static String getContentsFromResource(String filename) {
        try {
            String fileContents = getFileContents(StringUtils.class.getResourceAsStream(filename));
            return fileContents;
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getFileContents(InputStream inputStream) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        StringBuilder sb = new StringBuilder();

        try {
            while ((line = reader.readLine())!=null) {
                sb.append(line + "\n");

            }
        }


        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException e) {
               e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
