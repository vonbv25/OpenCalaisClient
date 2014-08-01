package com.Client.DataObjects;

import java.util.HashMap;

/**
 * Created by OJT4 on 7/28/14.
 */
public class SocialTags {

    private HashMap <String, Object> attributes;
    private String tagName;

    public SocialTags() {
        attributes = new HashMap<String, Object>();
        tagName = null;
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    public HashMap<String, Object> getAttributes() {
        return attributes;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
