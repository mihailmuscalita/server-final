package com.ubb.mihail.license.websocketmodel;

import java.io.Serializable;

public class SendRequest implements Serializable {

    private String scope;
    private String message;

    public SendRequest(String scope, String message) {
        this.scope = scope;
        this.message = message;
    }

    public SendRequest(){}

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "scope='" + scope + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
