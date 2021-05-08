package com.oxy.vertx.demo.msg;

import com.oxy.vertx.base.entities.BaseRequest;

public class RollbackPromoCounterMsg extends BaseRequest {
    private String originalRequestId;
    private CalculatePromotionMsg originalRequest;

    public String getOriginalRequestId() {
        return originalRequestId;
    }

    public void setOriginalRequestId(String originalRequestId) {
        this.originalRequestId = originalRequestId;
    }

    public CalculatePromotionMsg getOriginalRequest() {
        return originalRequest;
    }

    public void setOriginalRequest(CalculatePromotionMsg originalRequest) {
        this.originalRequest = originalRequest;
    }
}
