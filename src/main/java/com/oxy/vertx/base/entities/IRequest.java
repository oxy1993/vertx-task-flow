package com.oxy.vertx.base.entities;

public interface IRequest {

    void setStopFlag();

    void setStopFlag(boolean isSuccess);

    boolean isBreakWorkFlow();

    boolean isSuccessFlow();

    default String getMsgType() {
        return this.getClass().getSimpleName();
    }
}
