package com.jemena.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by nmiriyal on 27/07/2016.
 */
@RestController
public class SearchController {
    @Autowired
    private JavaClient client;
    private Logger logger= LoggerFactory.getLogger(SearchController.class);

    @RequestMapping(method = RequestMethod.POST, value = "/search", consumes = {"application/json", "application/xml"}, produces = "application/json")
    public String search(String searchStr) {
        logger.info("Searching for string '{}'",searchStr);
        SearchResponse searchResponse = client.search(searchStr);
       return client.getJson(searchResponse);
    }
}
