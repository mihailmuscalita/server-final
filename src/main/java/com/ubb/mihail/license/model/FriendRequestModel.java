package com.ubb.mihail.license.model;

import java.io.Serializable;

public class FriendRequestModel implements Serializable {

    private String friendsRequest;

    public FriendRequestModel(String friendsRequest) {
        this.friendsRequest = friendsRequest;
    }

    public FriendRequestModel(){}

    public String getFriendsRequest() {
        return friendsRequest;
    }

    public void setFriendsRequest(String friendsRequest) {
        this.friendsRequest = friendsRequest;
    }

    @Override
    public String toString() {
        return "FriendsRequestModel{" +
                "friendsRequest='" + friendsRequest + '\'' +
                '}';
    }
}
