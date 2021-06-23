package producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class MyKafkaProducer {

    private final String bootstrapServer;
    private final String topic;
    private KafkaProducer producer;

    public MyKafkaProducer(String bootstrapServer, String topic) {
        this.bootstrapServer = bootstrapServer;
        this.topic = topic;
    }

    public void initProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.producer = new KafkaProducer<String, String>(properties);
    }

    public void sendMessage(String msg) {
        producer.send(new ProducerRecord<>(this.topic, msg));
    }

    public void closeProducer() {
        this.producer.close();
    }
}
