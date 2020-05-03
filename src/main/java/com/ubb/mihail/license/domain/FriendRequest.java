package com.ubb.mihail.license.domain;

import javax.persistence.*;

@Entity
@Table(name = "friendsrequest")
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "sendby")
    private String sendByUser;

    @ManyToOne(cascade = {
            CascadeType.ALL})
    @JoinColumn(name = "idUser")
    private User user;


    public FriendRequest(String sendByUser, User user) {
        this.sendByUser = sendByUser;
        this.user = user;
    }

    public FriendRequest(String sendByUser) {
        this.sendByUser = sendByUser;
    }

    public FriendRequest(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSendByUser() {
        return sendByUser;
    }

    public void setSendByUser(String sendByUser) {
        this.sendByUser = sendByUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "FriendsRequest{" +
                "id=" + id +
                ", sendByUser='" + sendByUser + '\'' +
                ", user=" + user +
                '}';
    }
}
