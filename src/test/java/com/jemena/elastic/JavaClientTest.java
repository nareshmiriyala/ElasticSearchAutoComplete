package com.jemena.elastic;

import org.elasticsearch.action.search.SearchResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nmiriyal on 27/07/2016.
 */
public class JavaClientTest {
    private JavaClient javaClient;
    @Before
    public void setUp() throws Exception {
        javaClient=new JavaClient();
        javaClient.start();

    }

    @After
    public void tearDown() throws Exception {
        javaClient.stop();

    }

    @Test
    public void testSearch() throws InterruptedException {
        SearchResponse searchResponse = javaClient.search("dan");
        System.out.println("Total hits:"+javaClient.getJson(searchResponse));
    }

}