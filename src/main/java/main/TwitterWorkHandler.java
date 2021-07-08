package main;

import consumer.TwitterKafkaConsumer;
import model.TwitterWorkerTask;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class TwitterWorkHandler {
    private Logger LOGGER = LoggerFactory.getLogger(TwitterWorkHandler.class);

    public static void main(String[] args) {

        TwitterWorkHandler workHandler = new TwitterWorkHandler();
        workHandler.consumerHandler();
    }

    public void consumerHandler() {
        TwitterKafkaConsumer twitterConsumer = new TwitterKafkaConsumer();
        KafkaConsumer<String, TwitterWorkerTask> kafkaConsumer = twitterConsumer.initConsumer();

        while (true) {
            ConsumerRecords<String, TwitterWorkerTask> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(2000));

            for (ConsumerRecord<String, TwitterWorkerTask> record : consumerRecords) {
                LOGGER.info("task: " + record.value().getId());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
