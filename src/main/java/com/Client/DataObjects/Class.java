package com.Client.DataObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OJT4 on 7/28/14.
 */
public class Class {

    private String className;
    private List<NameEntity> entities;

    private Class() {
        className = null;
        entities = new ArrayList<NameEntity>();

    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<NameEntity> getEntities() {
        return entities;
    }

    public String getClassName() {
        return className;
    }

    public void setEntities(List<NameEntity> entities) {
        this.entities = entities;
    }

}
