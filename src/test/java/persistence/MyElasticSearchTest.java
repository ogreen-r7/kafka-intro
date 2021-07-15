package persistence;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class MyElasticSearchTest {
    private MyElasticSearch myElasticSearch;
    private RestHighLevelClient client;
    
    @Before
    public void setUp() {
        myElasticSearch = new MyElasticSearch();
        client = myElasticSearch.makeConnection();
    }

    @Test
    public void queryTest() throws IOException {
        SearchRequest searchRequest = new SearchRequest("tweets");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("text", "jerusalem"));
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        Arrays.stream(hits).limit(10).forEach(System.out::println);
    }

    @Test
    public void createIndexTest() throws IOException {
        myElasticSearch.createIndex("tweets1");
    }

    @Test
    public void insertRecordTest() throws IOException {
        String index = "stoptest";
        String doc1 = "{\"id\": \"12\", \"txt\": \"marry had a little lamb who's fleece was white as snow. and everywhere that marry went the lamb was sure to go\"}";
        String doc2 = "{\"id\": \"13\", \"txt\": \"black lamb of a white lamb\"}";
        String doc3 = "{\"id\": \"14\", \"txt\": \"RT my dear marry is a black lamb\"}";

        myElasticSearch.insertSingleDocument(index, doc1, "12");
        myElasticSearch.insertSingleDocument(index, doc2, "13");
        myElasticSearch.insertSingleDocument(index, doc3, "14");
    }
}

// http://localhost:9200/tweets/_search?q=text:startup
// http://localhost:9200/mytest/_search?q=text:lamb