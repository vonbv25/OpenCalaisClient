package com.Client.DataObjects;

import java.util.HashMap;

/**
 * Created by OJT4 on 7/28/14.
 */
public class NameEntity {
    private String name;
    private HashMap attributes;

    public NameEntity() {
        name = null;
        attributes = new HashMap();

    }

    public void setAttributes(HashMap attributes) {
        this.attributes = attributes;
    }

    public HashMap getAttributes() {
        return attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
