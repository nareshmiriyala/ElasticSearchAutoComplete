package com.jemena;

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
public class Application implements CommandLineRunner {
    @Autowired
    private RestClient restClient;
    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {
        restClient.insertBaby(BabyBuilder.aBaby().withName("Vihaan").withGender('M').withYear(2016).withCount(1).build());
        restClient.getBaby(1000023);
    }
}
