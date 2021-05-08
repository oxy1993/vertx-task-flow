package com.oxy.vertx.demo.task.calculate;

import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.msg.GetSetRedisValueMsg;
import com.oxy.vertx.base.redis.SetRedisHelperTask;
import com.oxy.vertx.demo.msg.CalculatePromotionMsg;
import io.vertx.core.Handler;

public class SaveCacheForRollbackTask extends OxyTask<CalculatePromotionMsg> {
    private final SetRedisHelperTask setRedisHelperTask;

    public SaveCacheForRollbackTask() {
        this.setRedisHelperTask = new SetRedisHelperTask();
    }

    @Override
    protected void exec(CalculatePromotionMsg input, Handler<CalculatePromotionMsg> nextTask) {
        if (input.getResponse().getResult() != 0) {
            log.warn("Calculate promotion fail, do not save cache");
            nextTask.handle(input);
            return;
        }
        GetSetRedisValueMsg getSetRedisValueMsg = new GetSetRedisValueMsg();
        getSetRedisValueMsg.setKey(input.getRequestId());
        getSetRedisValueMsg.setValue(JsonUtils.objToString(input));
        getSetRedisValueMsg.setTimeout(180); //180 seconds = 3 minutes
        setRedisHelperTask.run(getSetRedisValueMsg, isDone -> nextTask.handle(input));
    }
}
