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

    private static String HOST = "localhost";
    private static Integer PORT = 9200;
    private static String SCHEME = "http";
    private static String INDEX = "tweets";

    //id for our document
    public String id = "";

    //RestHighLevelClient Object
    private static RestHighLevelClient restHighLevelClient;

    public static void main(String[] args) throws IOException {
        MyElasticSearch es = new MyElasticSearch();
        es.makeConnection();
        String person = "{\"age\":10,\"dateOfBirth\":1471466076564,\"fullName\":\"John Doe\"}";
        es.insertSingleDocument(person);
        es.closeConnection();
    }

    public void insertSingleDocument(String tweet) throws IOException {
        IndexRequest request = new IndexRequest(INDEX);
        request.source(tweet, XContentType.JSON);

        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        String index = response.getIndex();
        long version = response.getVersion();
        System.out.println("es index: " + index);
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
