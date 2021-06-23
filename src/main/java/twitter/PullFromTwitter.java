package twitter;

import com.twitter.hbc.core.Client;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface PullFromTwitter {
    Client clientInit();
}
