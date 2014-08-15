package com.Client.Extractor;

import org.w3c.dom.Node;

/**
 * Created by OJT4 on 8/15/14.
 */
public interface IEnricher {


    /**
     * extract entity and enriched it's linked data
     * @param nameNode namenode of the entity
     * @throws Exception
     */
    public void enrich(Node nameNode) throws Exception;

}
