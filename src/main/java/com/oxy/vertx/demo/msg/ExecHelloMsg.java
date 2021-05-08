package com.oxy.vertx.demo.msg;

import com.oxy.vertx.base.entities.BaseRequest;

public class ExecHelloMsg extends BaseRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
