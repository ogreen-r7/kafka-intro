package model;

import java.util.List;

public class Extended_Tweet {
    private String full_text;
    private List<Integer> display_text_range;

    public Extended_Tweet() {
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public List<Integer> getDisplay_text_range() {
        return display_text_range;
    }

    public void setDisplay_text_range(List<Integer> display_text_range) {
        this.display_text_range = display_text_range;
    }

    @Override
    public String toString() {
        return "ExtendedTweet{" +
                "extended_tweet='" + full_text + '\'' +
                ", display_text_range=" + display_text_range +
                '}';
    }
}
