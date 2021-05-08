package com.oxy.vertx.demo.task.rollback;

import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.msg.GetSetRedisValueMsg;
import com.oxy.vertx.base.redis.GetRedisHelperTask;
import com.oxy.vertx.base.utils.StringUtils;
import com.oxy.vertx.demo.msg.CalculatePromotionMsg;
import com.oxy.vertx.demo.msg.RollbackPromoCounterMsg;
import io.vertx.core.Handler;

public class ValidateRollbackCounterRqTask extends OxyTask<RollbackPromoCounterMsg> {
    private final GetRedisHelperTask getRedisHelperTask;

    public ValidateRollbackCounterRqTask() {
        this.getRedisHelperTask = new GetRedisHelperTask();
    }

    @Override
    protected void exec(RollbackPromoCounterMsg input, Handler<RollbackPromoCounterMsg> nextTask) {
        if (StringUtils.isNullOrEmpty(input.getUser())) {
            log.error("User is null");
            input.fail(1);
            nextTask.handle(input);
            return;
        }
        if (StringUtils.isNullOrEmpty(input.getOriginalRequestId())) {
            log.error("Original request id invalid");
            input.fail(1);
            nextTask.handle(input);
            return;
        }
        GetSetRedisValueMsg getSetRedisValueMsg = new GetSetRedisValueMsg();
        getSetRedisValueMsg.setKey(input.getOriginalRequestId());
        getRedisHelperTask.run(getSetRedisValueMsg, done -> {
            if (done.getResponse().getResult() != 0 || StringUtils.isNullOrEmpty(done.getValue())) {
                input.fail(1);
                log.error("Original request is null");
                nextTask.handle(input);
                return;
            }
            CalculatePromotionMsg originalRequest = JsonUtils.jsonToObj(done.getValue(), CalculatePromotionMsg.class);
            if (originalRequest == null || originalRequest.getResponse().getResult() != 0) {
                input.fail(1);
                log.error("Original calculate request get response fail, do not rollback");
                nextTask.handle(input);
                return;
            }
            input.setOriginalRequest(originalRequest);
            log.info("Validate rollback request passed");
            nextTask.handle(input);
        });
    }
}
