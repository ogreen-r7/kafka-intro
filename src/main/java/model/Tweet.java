package model;

public class Tweet {

    private String id_str;
    private String text;
    private Extended_Tweet extended_tweet;
    private User user;
    private String lang;
    private int favouriteCount;
    private int retweetCount;

    public Tweet() {
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Extended_Tweet getExtended_tweet() {
        return extended_tweet;
    }

    public void setExtended_tweet(Extended_Tweet extended_tweet) {
        this.extended_tweet = extended_tweet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String language) {
        this.lang = language;
    }

    public int getFavouriteCount() {
        return favouriteCount;
    }

    public void setFavouriteCount(int favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id_str='" + id_str + '\'' +
                ", text='" + text + '\'' +
                ", extended_tweet=" + extended_tweet +
                ", user=" + user +
                ", lang='" + lang + '\'' +
                ", favouriteCount=" + favouriteCount +
                ", retweetCount=" + retweetCount +
                '}';
    }
}
