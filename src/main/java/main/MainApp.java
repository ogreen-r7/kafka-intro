package main;

public class MainApp {
    private static final String KAFKA_SERVER = "localhost:9092";
    private static final String KAFKA_TOPIC = "twitter-app";

    public static void main(String[] args) {
        MainApp app = new MainApp();
        app.runApp();
    }

    private void runApp() {
        MainProducer mainProducer = new MainProducer();
        mainProducer.runProducer();
    }
}
