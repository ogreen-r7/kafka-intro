package hello;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoCallback {
    final static Logger LOGGER = LoggerFactory.getLogger(ProducerDemoCallback.class);

    static final String BOOTSTRAP_SERVER = "localhost:9092";
    static final String TOPIC = "kofiko";

    public static void main(String[] args) {
        System.out.println("HELLO KAFKA");

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "Java Java Java");

        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    LOGGER.info("Topic: " + metadata.topic() + " | Offset: " + metadata.offset() + " | Timestamp: " + metadata.timestamp());
                } else {
                    LOGGER.error("Error on producer", exception);
                }
            }
        });

        producer.close();
    }
}
