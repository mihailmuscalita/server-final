package com.ubb.mihail.license.model;

import com.ubb.mihail.license.domain.User;


public class UserSteps {

    private UserModel user;
    private Integer number_of_steps;

    public UserSteps(UserModel user, Integer number_of_steps) {
        this.user = user;
        this.number_of_steps = number_of_steps;
    }

    public UserSteps(){}

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Integer getSteps() {
        return number_of_steps;
    }

    public void setSteps(Integer number_of_steps) {
        this.number_of_steps = number_of_steps;
    }

    @Override
    public String toString() {
        return "UserSteps{" +
                "user=" + user +
                ", number_of_steps=" + number_of_steps +
                '}';
    }
}
