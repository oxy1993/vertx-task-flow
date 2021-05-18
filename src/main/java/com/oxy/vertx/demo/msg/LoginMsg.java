package com.oxy.vertx.demo.msg;

import com.oxy.vertx.base.entities.BaseRequest;

public class LoginMsg extends BaseRequest {
    private String username;
    private String password;

    public LoginMsg() {
    }

    public LoginMsg(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
