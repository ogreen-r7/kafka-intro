package main;

import com.google.gson.Gson;
import com.twitter.hbc.core.Client;
import model.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import producer.MyKafkaProducer;
import twitter.PullFromTwitter;
import twitter.PullFromTwitterImpl;
import utils.JsonParse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MainProducer {

    private Logger LOGGER = LoggerFactory.getLogger(MainProducer.class);
    private static final String KAFKA_SERVER = "localhost:9092";
    private static final String KAFKA_TOPIC = "twitter-3";
    private static final String SEARCH_TERM = "israel";
    private final long TWEETER_PULL_LIMIT = 10000L;

    public static void main(String[] args) {
        MainProducer mainProducer = new MainProducer();
        mainProducer.runProducer();
    }

    public void runProducer() {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10000);
        List<String> searchTermList = new ArrayList<>();
        searchTermList.add(SEARCH_TERM);
        searchTermList.add("TDF");
        searchTermList.add("soccer");
        searchTermList.add("europe");

        PullFromTwitter pullFromTwitter = new PullFromTwitterImpl(queue, searchTermList);
        Client twitterClient = pullFromTwitter.clientInit();

        MyKafkaProducer twitterKafkaProducer = new MyKafkaProducer(KAFKA_SERVER, KAFKA_TOPIC);
        twitterKafkaProducer.initProducer();

        Gson gson = new Gson();

        for (int msgRead = 0; msgRead < TWEETER_PULL_LIMIT; msgRead++) {
            String msg = null;
            try {
                msg = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Tweet tweet = JsonParse.parseStringToTweet(msg);
            twitterKafkaProducer.sendMessage(gson.toJson(tweet));
            LOGGER.info("@@@@ Pulled tweet: " + tweet.getText());
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Shutting down producer");
            twitterClient.stop();
            queue.clear();
            twitterKafkaProducer.closeProducer();
            LOGGER.info("Shutdown complete");
        }));
    }
}
