package com.oxy.vertx.base.entities;

public class StopFlag {
    private boolean stop;
    private boolean isSuccess;

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}

