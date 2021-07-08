package consumer;

import com.google.gson.JsonDeserializer;
import mappers.TwitterWorkerDeserialize;
import model.TwitterWorkerTask;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class TwitterKafkaConsumer {

    public KafkaConsumer<String, TwitterWorkerTask> initConsumer() {
        Properties props = new Properties();

        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TwitterWorkerDeserialize.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "worker-group");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        KafkaConsumer<String, TwitterWorkerTask> kafkaConsumer = new KafkaConsumer<String, TwitterWorkerTask>(props);

        kafkaConsumer.subscribe(Collections.singleton("twitter.job"));
        return kafkaConsumer;
    }
}
