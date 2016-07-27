package com.jemena.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jemena.model.Baby;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by nmiriyal on 27/07/2016.
 */
@Component
public class JavaClient {
    public static final String INDEX_NAME = "babies";
    public static final String TYPE = "baby";
    private Logger logger = LoggerFactory.getLogger(JavaClient.class);
    private static Client client;

    public void start() {
        try {
            if (isNull(client)) {
                client = TransportClient.builder().build()
                        .addTransportAddress(new InetSocketTransportAddress(
                                InetAddress.getByName("localhost"), 9300));
            }
        } catch (UnknownHostException e) {
            logger.error("Cant start the transport client", e);
        }
    }

    public void index(Baby baby)  {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(baby);
        } catch (JsonProcessingException e) {
            logger.error("Parsing json error",e);
        }
        if (nonNull(client)) {
            IndexResponse indexResponse = client.prepareIndex(INDEX_NAME, TYPE, "1")
                    .setSource(jsonString)
                    .get();
            logger.info("Response of indexing {}", indexResponse.getId());
        }

    }
    public GetResponse get(String id){
        GetResponse response = client.prepareGet(INDEX_NAME, TYPE, id).get();
        logger.info("Response of get call {}",response.getId());
        return response;
    }

    public void stop() {
        if (nonNull(client)) {
            client.close();
        }
    }
}
