package com.ubb.mihail.license.domain;


import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="Users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "userId", unique = true, nullable = false)
    private int userId;

    @Column(name="userName", length = 100)
    private String userName;

    @Column(name="userPassword", length = 100)
    private String userPassword;

    @Column(name="name", length = 100)
    private String name;

    @Column(name="email", length = 200)
    private String email;

    @Column(name="userRole")
    private int userRole;


   // private Set<User> friends;

    public User(String userName, String userPassword, String name, int userRole) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.name = name;
        this.userRole = userRole;
       // this.friends =new TreeSet<>();
    }

    public User(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userRole=" + userRole +
                '}';
    }

}
