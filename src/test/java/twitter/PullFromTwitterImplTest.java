package twitter;

import com.twitter.hbc.core.Client;
import model.Tweet;
import org.junit.Before;
import org.junit.Test;
import utils.JsonParse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PullFromTwitterImplTest {
    private final String SEARCH_TERM1 = "tour de france";
    private final String SEARCH_TERM2 = "le tour";
    private final String SEARCH_TERM3 = "TDF";
    private PullFromTwitter pullFromTwitter;
    Client twitterClient;
    BlockingQueue<String> queue;

    @Before
    public void setUp() {
        queue = new LinkedBlockingQueue<>(10000);
        List<String> searchTermList = new ArrayList<>();
        searchTermList.add(SEARCH_TERM1);
        searchTermList.add(SEARCH_TERM2);
        searchTermList.add(SEARCH_TERM3);

        pullFromTwitter = new PullFromTwitterImpl(queue, searchTermList);
        twitterClient = pullFromTwitter.clientInit();
    }

    @Test
    public void twitterSimpleTest() {
        for (int msgRead = 0; msgRead < 20; msgRead++) {
            String msg = null;
            try {
                msg = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            System.out.println(msg);
            Tweet tweet = JsonParse.parseStringToTweet(msg);
//            System.out.println("Text: " + tweet.getText());
            if (!tweet.getText().startsWith("RT")) {
//                System.out.println(tweet);
                String extendedText = tweet.getText();
                if (tweet.getExtended_tweet() != null) {
                    extendedText = tweet.getExtended_tweet().getFull_text();
                }
                System.out.println("UserId: " + tweet.getUser().getId_str() + " id: "+ tweet.getId_str() + " text: " + extendedText);
//                System.out.println("--------");
            }
        }
        twitterClient.stop();
        queue.clear();
    }
}