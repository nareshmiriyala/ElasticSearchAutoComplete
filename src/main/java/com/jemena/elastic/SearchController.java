package com.jemena.elastic;

import org.elasticsearch.action.search.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nmiriyal on 27/07/2016.
 */
@RestController
public class SearchController {
    @Autowired
    private JavaClient client;
    private Logger logger= LoggerFactory.getLogger(SearchController.class);

    @RequestMapping(method = RequestMethod.POST, value = "/search", consumes = {"application/json", "application/xml"}, produces = "application/json")
    public String search(@RequestBody String searchStr) {
        logger.info("Searching for string '{}'",searchStr);
        SearchResponse searchResponse = client.search(searchStr);
        String json = client.getJson(searchResponse);
        logger.info("Json response '{}",json);
        return json;
    }
}
