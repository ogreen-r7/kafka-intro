package mappers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.TwitterWorkerTask;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class TwitterWorkerDeserialize implements Deserializer {
    @Override
    public void configure(Map configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public TwitterWorkerTask deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        TwitterWorkerTask twitterWorkerTask = null;
        try {
            twitterWorkerTask = mapper.readValue(data, TwitterWorkerTask.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twitterWorkerTask;
    }

    @Override
    public Object deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
