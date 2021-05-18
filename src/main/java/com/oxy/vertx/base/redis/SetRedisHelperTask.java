package com.oxy.vertx.base.redis;

import com.oxy.vertx.base.WarriorTask;
import com.oxy.vertx.base.msg.GetSetRedisValueMsg;
import io.vertx.core.Handler;

import java.util.Arrays;

public class SetRedisHelperTask extends WarriorTask<GetSetRedisValueMsg> {

    @Override
    protected void exec(GetSetRedisValueMsg input, Handler<GetSetRedisValueMsg> nextTask) {
        RedisClientFactory.getRedisClient().set(Arrays.asList(input.getKey(), input.getValue()), done -> {
            if (done.succeeded()) {
                log.info("Set redis success with key: {}, value: {}", input.getKey(), input.getValue());
            } else {
                log.error("Set redis fail with key: {}, value: {}", input.getKey(), input.getValue(), done.cause());
            }
            nextTask.handle(input);
        });
    }
}
