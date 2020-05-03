package com.ubb.mihail.license.model;

import java.io.Serializable;

public class UserModel implements Serializable {

    private int id;
    private String userName;
    private String name;
    private String email;
    private int userRole;

    public UserModel(int id,String userName, String name,String email, int userRole) {
        this.id=id;
        this.userName = userName;
        this.name = name;
        this.email =email;
        this.userRole = userRole;
    }

    public UserModel(int id,String userName, String name, int userRole) {
        this.id=id;
        this.userName = userName;
        this.name = name;
        this.userRole = userRole;
    }

    public UserModel(String userName, String name, int userRole) {
        this.userName = userName;
        this.name = name;
        this.userRole = userRole;
    }

    public UserModel(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
