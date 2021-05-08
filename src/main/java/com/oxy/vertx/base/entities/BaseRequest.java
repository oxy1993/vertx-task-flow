package com.oxy.vertx.base.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class BaseRequest implements IRequest {
    private final StopFlag stopFlag = new StopFlag();
    private String requestId = UUID.randomUUID().toString();
    private String user;
    private Map<String, Object> extras = new HashMap<>();
    private BaseResponse response;

    public void fail(int result) {
        getResponse().setResult(result);
        setStopFlag();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

    public Object getExtra(String key) {
        return extras.get(key);
    }

    public void putExtra(String key, Object value) {
        extras.put(key, value);
    }

    public void popExtra(String key) {
        extras.remove(key);
    }

    public BaseResponse getResponse() {
        if (response == null) {
            response = new BaseResponse();
            response.setUser(getUser());
            response.setExtras(getExtras());
            response.setRequestId(getRequestId());
            setResponse(response);
        }
        return response;
    }

    public void setResponse(BaseResponse response) {
        this.response = response;
    }

    public <T> T getResponse(Class<T> clazz) {
        if (response == null) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                return null;
            }
        }
        return (T) response;
    }

    public <T extends BaseResponse> T createResponse(Class<T> type) {
        try {
            response = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        response.setUser(getUser());
        response.setExtras(getExtras());
        response.setRequestId(getRequestId());
        setResponse(response);
        return (T) response;
    }

    @Override
    public void setStopFlag() {
        stopFlag.setStop(true);
    }

    @Override
    public void setStopFlag(boolean isSuccess) {
        stopFlag.setStop(true);
        stopFlag.setSuccess(isSuccess);
    }

    @Override
    public boolean isBreakWorkFlow() {
        return stopFlag.isStop();
    }

    @Override
    public boolean isSuccessFlow() {
        return stopFlag.isSuccess();
    }

}
