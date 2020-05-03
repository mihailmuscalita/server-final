package com.ubb.mihail.license.model;

public class Friend {

    private String name;

    public Friend(String name) {
        this.name = name;
    }

    public Friend(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "name='" + name + '\'' +
                '}';
    }
}
