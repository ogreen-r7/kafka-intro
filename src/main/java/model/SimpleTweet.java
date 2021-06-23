package model;

public class SimpleTweet {
    private String text;
    private String userName;
    private String userScreenName;
    private int userFollowersCount;
    private String lang;
    private int favouriteCount;
    private int retweetCount;

    public SimpleTweet() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public int getUserFollowersCount() {
        return userFollowersCount;
    }

    public void setUserFollowersCount(int userFollowersCount) {
        this.userFollowersCount = userFollowersCount;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
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
}
