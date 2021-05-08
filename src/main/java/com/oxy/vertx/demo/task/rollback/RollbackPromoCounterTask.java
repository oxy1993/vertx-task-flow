package com.oxy.vertx.demo.task.rollback;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.msg.GetSetRedisValueMsg;
import com.oxy.vertx.base.redis.SetRedisHelperTask;
import com.oxy.vertx.demo.msg.RollbackPromoCounterMsg;
import com.oxy.vertx.demo.utils.PromotionUtils;
import io.vertx.core.Handler;

public class RollbackPromoCounterTask extends OxyTask<RollbackPromoCounterMsg> {
    private final SetRedisHelperTask setRedisHelperTask;

    public RollbackPromoCounterTask() {
        this.setRedisHelperTask = new SetRedisHelperTask();
    }

    @Override
    protected void exec(RollbackPromoCounterMsg input, Handler<RollbackPromoCounterMsg> nextTask) {
        GetSetRedisValueMsg getSetRedisValueMsg = new GetSetRedisValueMsg();
        getSetRedisValueMsg.setKey(PromotionUtils.buildRedisKey(input.getOriginalRequest()));
        getSetRedisValueMsg.setValue(String.valueOf(input.getOriginalRequest().getExtra("discountTime")));
        setRedisHelperTask.run(getSetRedisValueMsg, isDone -> nextTask.handle(input));
    }
}
