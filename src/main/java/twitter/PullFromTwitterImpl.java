package twitter;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PullFromTwitterImpl implements PullFromTwitter {

    static final Logger LOGGER = LoggerFactory.getLogger(PullFromTwitterImpl.class);
    static final String CONSUMER_KEY = "LYP3glNLYaj0mkERngGHfg5DY";
    static final String CONSUMER_SECRET = "zVnr6zxCtSr4ZFHsRA1JZyka1Z0P65zRXJlA2vZJxbUp2gTzQM";
    static final String TOKEN = "47083393-qT1d5WxXZe0VoftOtWsT6ba1M8yv0f2RLhNGiDuPN";
    static final String SECRET = "IERIPBqjaxDx72RhhfuVMUo30opcKWydI0PAZQ4PQuC8y";

    private final BlockingQueue queue;
    private final List searchTermList;

    public PullFromTwitterImpl(BlockingQueue queue, List searchTermList) {
        this.queue = queue;
        this.searchTermList = searchTermList;
    }

    @Override
    public Client clientInit() {
        Authentication oAuth1 = new OAuth1(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, SECRET);

        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        endpoint.trackTerms(this.searchTermList);

        Client twitterClient = new ClientBuilder()
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(oAuth1)
                .processor(new StringDelimitedProcessor(this.queue))
                .build();

        // Establish a connection
        twitterClient.connect();
        LOGGER.info("Established Connection to Twitter API");

        return twitterClient;
    }
}
