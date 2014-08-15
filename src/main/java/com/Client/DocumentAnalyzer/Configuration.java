package com.Client.DocumentAnalyzer;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by OJT4 on 8/6/14.
 */
public class Configuration {

    private static Configuration current_config = null;
    private Properties properties = null;

    public Configuration() {
        try {
            properties= new Properties();
            properties.load(Configuration.class.getResourceAsStream("/resources/configuration/analyzer.properties"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setCurrent_config(Configuration config) {
        Configuration.current_config= config;
    }
    public static Configuration getCurrent_config() {
        if (current_config==null) {
            setCurrent_config(new Configuration());
        }
        return current_config;

    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String getStatusWarning() {
        return get("httpstatus.warning");
    }


    public String getOutputFilename() {
        return get("output.filename");
    }

    public String getCalaisRestUri() {
        return get("calais.rest.uri");
    }

    public Object getContentType() {
        return get("opencalais.contenttype");
    }

    public Object getOutputFormat() {
        return get("opencalais.outputformat");
    }

    public Object getSubmitter() {
        return get("opencalais.submitter");

    }

    public int getMaxdocSize() {
        String max = get("maxdocsize");

        if (max!=null && !max.equals("")&& Pattern.matches("\\d+",max))
            return Integer.parseInt(max);
        return 0;
    }




}
