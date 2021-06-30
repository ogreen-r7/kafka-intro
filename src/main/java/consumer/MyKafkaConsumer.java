package consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class MyKafkaConsumer {

    private final String bootstrapServer;
    private final String topic;
    private final String groupId;
    private KafkaConsumer<String, String> consumer;

    public MyKafkaConsumer(String bootstrapServer, String topic, String groupId) {
        this.bootstrapServer = bootstrapServer;
        this.topic = topic;
        this.groupId = groupId;
    }

    public KafkaConsumer<String, String> initConsumer() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");

        this.consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton(topic));
        return consumer;
    }
}
