package com.oxy.vertx.demo.msg;

import com.oxy.vertx.base.entities.BaseResponse;

public class HelloWorldResponseMsg extends BaseResponse {
    private String greeting;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
