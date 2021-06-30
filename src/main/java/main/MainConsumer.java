package main;

import com.google.gson.Gson;
import com.twitter.hbc.core.Client;
import consumer.MyKafkaConsumer;
import model.Tweet;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.MyElasticSearch;
import twitter.PullFromTwitter;
import twitter.PullFromTwitterImpl;
import utils.JsonParse;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MainConsumer {

    private Logger LOGGER = LoggerFactory.getLogger(MainConsumer.class);
    private static final String KAFKA_SERVER = "localhost:9092";
    private static final String KAFKA_TOPIC = "twitter-3";
    private static final String CONSUMER_GROUP = "twitter-1";

    public static void main(String[] args) throws IOException {
        MainConsumer mainConsumer = new MainConsumer();
        mainConsumer.runConsumer();
//        mainConsumer.testTweetsToElastic();
    }

    public void runConsumer() throws IOException {
        MyKafkaConsumer myConsumer = new MyKafkaConsumer(KAFKA_SERVER, KAFKA_TOPIC, CONSUMER_GROUP);
        KafkaConsumer<String, String> consumer = myConsumer.initConsumer();

        MyElasticSearch myElasticSearch = new MyElasticSearch();
        RestHighLevelClient elasticSearchClient = myElasticSearch.makeConnection();

        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));

            Integer recordsCount = consumerRecords.count();
            LOGGER.info("@@@ Records received: " + recordsCount);
            BulkRequest bulkRequest = new BulkRequest();
            for (ConsumerRecord<String, String> rec : consumerRecords) {
                LOGGER.info("Key: " + rec.key() + " -- Partition: " + rec.partition() + " Offset: " + rec.offset());
                Tweet tweet = JsonParse.parseStringToTweet(rec.value());
//                myElasticSearch.insertSingleDocument(gson.toJson(tweet), tweet.getId_str());
                IndexRequest indexRequest = new IndexRequest("tweets", "_doc", tweet.getId_str()) // makes consumer idempotent
                        .source(tweet, XContentType.JSON);
                bulkRequest.add(indexRequest);
            }

            if (recordsCount > 0) {
                BulkResponse bulkResponse = elasticSearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
                consumer.commitSync();
                LOGGER.info("#### Offset commit");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void testTweetsToElastic() throws IOException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10000);
        List<String> searchTermList = new ArrayList<>();
        searchTermList.add("israel");

        PullFromTwitter pullFromTwitter = new PullFromTwitterImpl(queue, searchTermList);
        Client twitterClient = pullFromTwitter.clientInit();

        MyElasticSearch myElasticSearch = new MyElasticSearch();
        myElasticSearch.makeConnection();

        Gson gson = new Gson();

        for (int msgRead = 0; msgRead < 50; msgRead++) {
            String msg = null;
            try {
                msg = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Tweet tweet = JsonParse.parseStringToTweet(msg);
            LOGGER.info(String.valueOf(tweet));
            myElasticSearch.insertSingleDocument(gson.toJson(tweet), tweet.getId_str());
        }
        twitterClient.stop();
        queue.clear();
        myElasticSearch.closeConnection();
    }
}
