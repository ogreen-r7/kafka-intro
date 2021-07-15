package persistence;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.mapper.Mapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.xContent;

public class MyElasticSearch {

    private static String HOST = "0.0.0.0";
    private static Integer PORT = 9200;
    private static String SCHEME = "http";
    private static String INDEX = "tweets";
    private static String TYPE = "_doc";

    //RestHighLevelClient Object
    private static RestHighLevelClient restHighLevelClient;

    public static void main(String[] args) throws IOException {

    }

    public void insertSingleDocument(String index, String tweet, String tweetId) throws IOException {
        IndexRequest request = new IndexRequest(index, TYPE, tweetId);
        request.source(tweet, XContentType.JSON);

        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        String responseId = response.getId();
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

    public synchronized void createIndex(String indexName) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);

        XContentBuilder settingsBuilder = XContentFactory.jsonBuilder()
                .startObject()
                    .startObject("analysis")
                        .startObject("analyzer")
                            .startObject("stopwords")
                                .field("type", "stop")
                                .field("tokenizer", "standard")
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();

        Map<String, Object> txt = new HashMap<>();
        txt.put("type", "text");
        txt.put("fielddata", "true");
        txt.put("analyzer", "stopwords");
        Map<String, Object> properties = new HashMap<>();
        properties.put("text", txt);
        properties.put("extended_tweet.full_text", txt);
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        request.mapping("mapping", mapping);

        request.settings(settingsBuilder);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.toString());
    }

    public synchronized void closeConnection() throws IOException {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }
}
