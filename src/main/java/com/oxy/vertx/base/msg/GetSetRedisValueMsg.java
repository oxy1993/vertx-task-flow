package com.oxy.vertx.base.msg;

import com.oxy.vertx.base.entities.BaseRequest;

public class GetSetRedisValueMsg extends BaseRequest {
    private String key;
    private String value;
    private long timeout;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
