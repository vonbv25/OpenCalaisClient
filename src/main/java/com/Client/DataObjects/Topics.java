package com.Client.DataObjects;

/**
 * Created by OJT4 on 7/28/14.
 */
public class Topics {

    private String topicName;
    private int relevancies;

    public Topics() {
        topicName = null;
        relevancies = 0;
    }

    public void setRelevancies(int relevancies) {
        this.relevancies = relevancies;
    }

    public int getRelevancies() {
        return relevancies;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
