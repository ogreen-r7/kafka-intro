package model;

import java.util.List;
import java.util.UUID;

public class TwitterWorkerTask {

    private UUID id;
    private String userName;
    private List<String> searchTerms;
    private Integer requestedNumber;

    public TwitterWorkerTask() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(List<String> searchTerms) {
        this.searchTerms = searchTerms;
    }

    public Integer getRequestedNumber() {
        return requestedNumber;
    }

    public void setRequestedNumber(Integer requestedNumber) {
        this.requestedNumber = requestedNumber;
    }
}
