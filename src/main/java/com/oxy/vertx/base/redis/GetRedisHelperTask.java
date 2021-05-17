package com.oxy.vertx.base.redis;

import com.oxy.vertx.base.WarriorTask;
import com.oxy.vertx.base.msg.GetSetRedisValueMsg;
import io.vertx.core.Handler;

public class GetRedisHelperTask extends WarriorTask<GetSetRedisValueMsg> {

    @Override
    protected void exec(GetSetRedisValueMsg input, Handler<GetSetRedisValueMsg> nextTask) {
        RedisClientFactory.getRedisClient().get(input.getKey(), done -> {
            if (done.succeeded()) {
//                log.info("Get redis success for key: {},  value: {}", input.getKey(), done.result());
                input.setValue(done.result());
            } else {
                log.error("Get redis fail for key: {},  value: {}", input.getKey(), done.result(), done.cause());
                input.fail(1);
            }
            nextTask.handle(input);
        });
    }
}
