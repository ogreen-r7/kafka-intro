package producer;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyKafkaProducerTest {

    private static final String KAFKA_SERVER = "localhost:9092";

    @Test
    public void sendMessageToKafka() {
        MyKafkaProducer kafkaProducer = new MyKafkaProducer(KAFKA_SERVER, "advisor-report");
        kafkaProducer.initProducer();

        kafkaProducer.sendMessage("Test Message");
    }
}