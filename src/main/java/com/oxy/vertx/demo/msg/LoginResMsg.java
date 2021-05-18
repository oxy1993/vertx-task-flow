package com.oxy.vertx.demo.msg;

import com.oxy.vertx.base.entities.BaseResponse;

public class LoginResMsg extends BaseResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
