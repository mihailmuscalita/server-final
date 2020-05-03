package com.ubb.mihail.license.domain;

import javax.persistence.*;

@Entity
@Table(name = "friends")
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = {
            CascadeType.ALL})
    @JoinColumn(name = "firstUser")
    private User firstUser;

    @ManyToOne(cascade = {
            CascadeType.ALL})
    @JoinColumn(name = "secondUser")
    private User secondUser;

    public Friends() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "id=" + id +
                ", firstUser=" + firstUser +
                ", secondUser=" + secondUser +
                '}';
    }
}
