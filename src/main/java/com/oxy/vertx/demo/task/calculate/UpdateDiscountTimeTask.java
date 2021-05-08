package com.oxy.vertx.demo.task.calculate;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.msg.GetSetRedisValueMsg;
import com.oxy.vertx.base.redis.SetRedisHelperTask;
import com.oxy.vertx.demo.msg.CalculatePromotionMsg;
import com.oxy.vertx.demo.msg.PromotionResponseMsg;
import com.oxy.vertx.demo.utils.PromotionUtils;
import io.vertx.core.Handler;

public class UpdateDiscountTimeTask extends OxyTask<CalculatePromotionMsg> {
    private final SetRedisHelperTask setRedisHelperTask;

    public UpdateDiscountTimeTask() {
        this.setRedisHelperTask = new SetRedisHelperTask();
    }

    @Override
    protected void exec(CalculatePromotionMsg input, Handler<CalculatePromotionMsg> nextTask) {
        GetSetRedisValueMsg getSetRedisValueMsg = new GetSetRedisValueMsg();
        getSetRedisValueMsg.setKey(PromotionUtils.buildRedisKey(input));
        int discountTime = input.getResponse(PromotionResponseMsg.class).getTotalDiscountTimeThisMouth() + 1;
        getSetRedisValueMsg.setValue(String.valueOf(discountTime));
        setRedisHelperTask.run(getSetRedisValueMsg, isDone -> nextTask.handle(input));
    }
}
