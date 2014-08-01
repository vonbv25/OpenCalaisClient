package com.Client.DocumentAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by OJT4 on 8/1/14.
 */
public class DocumentAnalyzer {

    public static boolean analyze(String filePath, String APIKEY) {

        return true;
    }

    private String readfile(String filePath) throws Exception {
        BufferedReader in = null;
        StringBuffer out = null;
        try {
            in = new BufferedReader(new FileReader(filePath));
            out = new StringBuffer();
            String line;

            while ((line = in.readLine()) != null) {
                out.append(line);
                out.append("\r\n");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("(EE) Could read text file (" + filePath + ")", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("(WW) could not close input reader for " + filePath);
                }
            }
        }
        return out.toString();
    }


}