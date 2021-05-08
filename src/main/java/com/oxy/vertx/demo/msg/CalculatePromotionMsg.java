package com.oxy.vertx.demo.msg;

import com.oxy.vertx.base.entities.BaseRequest;

public class CalculatePromotionMsg extends BaseRequest {
    private long originalAmount;
    private int paymentMethod;

    public long getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(long originalAmount) {
        this.originalAmount = originalAmount;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
