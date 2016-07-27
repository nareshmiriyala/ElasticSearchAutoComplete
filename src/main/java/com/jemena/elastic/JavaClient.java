package com.jemena.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jemena.csv.Reader;
import com.jemena.model.Baby;
import com.jemena.model.BabyBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * Created by nmiriyal on 27/07/2016.
 */
@Component
public class JavaClient {
    public static final String INDEX_NAME = "babies";
    public static final String TYPE = "baby";
    private Logger logger = LoggerFactory.getLogger(JavaClient.class);
    private static Client client;

    @Autowired
    private Reader csvReader;

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
        indexValue(baby,mapper);

    }
    public void index(){

        List<String[]> rows = csvReader.read("NationalNames.csv");
        rows.remove(0);
        ObjectMapper mapper = new ObjectMapper();
        rows.parallelStream().forEachOrdered(row -> {
            Baby baby = BabyBuilder.aBaby().withId(Integer.parseInt(row[0])).withName(row[1]).withYear(Integer.parseInt(row[2])).withGender(row[3].charAt(0)).withCount(Integer.parseInt(row[4])).build();
            indexValue(baby, mapper);
        });
    }

    private void indexValue(Baby baby,ObjectMapper mapper) {

        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(baby);
        } catch (JsonProcessingException e) {
            logger.error("Parsing json error",e);
        }
        if (nonNull(client)) {
            IndexResponse indexResponse = client.prepareIndex(INDEX_NAME, TYPE, String.valueOf(baby.getId()))
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
    public void delete(){
        client.delete(new DeleteRequest(INDEX_NAME));
    }
    public String getJson(SearchResponse searchResponse){
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits()) {

            Map<String, Object> result = hit.getSource();
            list.add(result);
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(list);
            return json;
        } catch (JsonProcessingException ex) {
            //log the error
        }
        return null;
    }
    public SearchResponse search(String searchStr){
        QueryBuilder queryBuilder=matchQuery("name",searchStr);
        SearchResponse searchResponse = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE)
                .setQuery(queryBuilder)
                .setExplain(true)
                .execute()
                .actionGet();
        return searchResponse;
    }
}
