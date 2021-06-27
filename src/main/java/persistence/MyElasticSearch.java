package persistence;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class MyElasticSearch {

    private static String HOST = "0.0.0.0";
    private static Integer PORT = 9200;
    private static String SCHEME = "http";
    private static String INDEX = "tweets";
    private static String TYPE = "_doc";

    //RestHighLevelClient Object
    private static RestHighLevelClient restHighLevelClient;

    public static void main(String[] args) throws IOException {
        MyElasticSearch es = new MyElasticSearch();
        es.makeConnection();
        String person = "{\"age\":10,\"dateOfBirth\":1471466076564,\"fullName\":\"John Doe\"}";
        es.insertSingleDocument(person, "1");
        es.closeConnection();
    }

    public void insertSingleDocument(String tweet, String tweetId) throws IOException {
        IndexRequest request = new IndexRequest(INDEX, TYPE, tweetId);
        request.source(tweet, XContentType.JSON);

        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        String index = response.getIndex();
        String responseId = response.getId();
        System.out.println("es index: " + index + " id: " + responseId);
    }

    public synchronized RestHighLevelClient makeConnection() {
        if (restHighLevelClient == null) {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(HOST, PORT, SCHEME)
                    ));
        }
        return restHighLevelClient;
    }

    public synchronized void closeConnection() throws IOException {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }
}
