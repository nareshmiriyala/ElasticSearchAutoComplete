package com.dellnaresh.elastic;

import com.dellnaresh.model.Baby;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by nmiriyal on 27/07/2016.
 */
@RestController
public class RestClient {
    private Logger logger= org.slf4j.LoggerFactory.getLogger(RestClient.class);
    private RestTemplate restTemplate;

    public void insertBaby(Baby baby){
        restTemplate=new RestTemplate();
        logger.info("Inserting baby {}",baby);
        restTemplate.put("http://localhost:9200/naresh/babyname/1000023",baby);
    }

    public void getBaby(int id){
        logger.info("Getting baby with id {}",id);
        Baby baby = restTemplate.getForObject("http://localhost:9200/naresh/babyname/1000023", Baby.class);
        logger.info("Baby fetched {}",baby.toString());
    }


}
