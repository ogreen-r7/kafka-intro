package utils;

import org.junit.Test;

public class JsonParseTest {

    @Test
    public void firstTest() {
        System.out.println("test");
    }

    @Test
    public void numberOfProcessors() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("availableProcessors = " + availableProcessors);

    }
}