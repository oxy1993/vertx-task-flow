package com.oxy.vertx.demo.task.calculate;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.demo.msg.CalculatePromotionMsg;
import io.vertx.core.Handler;

public class BuildPromotionRespTask extends OxyTask<CalculatePromotionMsg> {
    @Override
    protected void exec(CalculatePromotionMsg input, Handler<CalculatePromotionMsg> nextTask) {
        input.getResponse().setResult(0);
        input.getResponse().setDescription("Success");
        nextTask.handle(input);
    }
}
