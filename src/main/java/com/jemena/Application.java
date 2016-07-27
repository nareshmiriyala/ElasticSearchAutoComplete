package com.jemena;

import com.jemena.elastic.JavaClient;
import com.jemena.elastic.RestClient;
import com.jemena.model.Baby;
import com.jemena.model.BabyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by nmiriyal on 27/07/2016.
 */
@SpringBootApplication
public class Application  {
    @Autowired
    private RestClient restClient;
    @Autowired
    private JavaClient transportClient;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
//
//    @Override
//    public void run(String... strings) throws Exception {
////        transportClientInsert();
//    }

    private void transportClientInsert() {
        transportClient.start();
        insertIndex();
//        transportClient.delete();
        transportClient.stop();

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
