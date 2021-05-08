package com.oxy.vertx.demo.msg;

import com.oxy.vertx.base.entities.BaseResponse;

public class PromotionResponseMsg extends BaseResponse {
    private long finalAmount;
    private int totalDiscountTimeThisMouth;

    public long getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(long finalAmount) {
        this.finalAmount = finalAmount;
    }

    public int getTotalDiscountTimeThisMouth() {
        return totalDiscountTimeThisMouth;
    }

    public void setTotalDiscountTimeThisMouth(int totalDiscountTimeThisMouth) {
        this.totalDiscountTimeThisMouth = totalDiscountTimeThisMouth;
    }
}
