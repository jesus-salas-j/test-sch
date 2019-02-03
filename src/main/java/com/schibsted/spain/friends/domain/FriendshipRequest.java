package com.schibsted.spain.friends.domain;

public class FriendshipRequest {

    private String requesterUsername;
    private String requestedUsername;
    private boolean declined;

    public String getRequester() {
        return requesterUsername;
    }

    public String getRequested() {
        return requestedUsername;
    }

    public FriendshipRequest(String requester, String requested) {
        this.requesterUsername = requester;
        this.requestedUsername = requested;
        this.declined = false;
    }

    public void decline() {
        this.declined = true;
    }

    public boolean isDeclined() {
        return this.declined;
    }

}