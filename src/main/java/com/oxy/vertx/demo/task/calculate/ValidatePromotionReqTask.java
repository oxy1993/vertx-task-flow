package com.oxy.vertx.demo.task.calculate;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.utils.StringUtils;
import com.oxy.vertx.demo.msg.CalculatePromotionMsg;
import io.vertx.core.Handler;

public class ValidatePromotionReqTask extends OxyTask<CalculatePromotionMsg> {
    @Override
    protected void exec(CalculatePromotionMsg input, Handler<CalculatePromotionMsg> nextTask) {
        if (StringUtils.isNullOrEmpty(input.getUser())) {
            log.error("User is null");
            input.fail(1);
            nextTask.handle(input);
            return;
        }
        if (input.getOriginalAmount() <= 0) {
            log.error("Original amount invalid");
            input.fail(1);
            nextTask.handle(input);
            return;
        }
        log.info("Validate promotion request passed");
        nextTask.handle(input);
    }
}
