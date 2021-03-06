package com.dellnaresh;

import com.dellnaresh.elastic.JavaClient;
import com.dellnaresh.elastic.RestClient;
import com.dellnaresh.model.Baby;
import com.dellnaresh.model.BabyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by nmiriyal on 27/07/2016.
 */
@SpringBootApplication
public class Application{
    @Autowired
    private RestClient restClient;
    @Autowired
    private JavaClient transportClient;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    private void insertIndex() {
        transportClient.index();
        transportClient.get("1");
    }

    private void restClientInsert() {
        restClient.insertBaby(createBaby());
        restClient.getBaby(1000023);
    }

    private Baby createBaby() {
        return BabyBuilder.aBaby().withName("Vihaan").withGender('M').withYear(2016).withCount(1).build();
    }
}
