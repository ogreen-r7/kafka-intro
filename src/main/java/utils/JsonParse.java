package utils;

import com.google.gson.Gson;
import model.SimpleTweet;
import model.Tweet;
import org.json.JSONObject;

public class JsonParse {
    public static JSONObject parseFromString(String string) {
        JSONObject jsonObject = new JSONObject(string);
        String text = jsonObject.getString("text");
        System.out.println(text);

        return jsonObject;
    }

    public static Tweet parseStringToTweet(String string) {
        Gson gson = new Gson();
        Tweet tweet = gson.fromJson(string, Tweet.class);
        return tweet;
    }
}
