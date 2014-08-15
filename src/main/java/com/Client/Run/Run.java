package com.Client.Run;

import com.Client.Calais;
import com.Client.CalaisSoap;
import com.Client.DocumentAnalyzer.DocumentAnalyzer;

/**
 * Created by OJT4 on 7/31/14.
 */
public class Run {
    public static void main(String[] args) {
        try { // Call Web Service Operation
        boolean isrunning = DocumentAnalyzer.analyze(args[0],args[1]);
            if (!isrunning) {
                System.out.print("error in running the analyzer");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
