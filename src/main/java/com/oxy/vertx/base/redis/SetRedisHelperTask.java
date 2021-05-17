package com.oxy.vertx.base.redis;

import com.oxy.vertx.base.WarriorTask;
import com.oxy.vertx.base.msg.GetSetRedisValueMsg;
import io.vertx.core.Handler;
import io.vertx.redis.op.SetOptions;

public class SetRedisHelperTask extends WarriorTask<GetSetRedisValueMsg> {

    @Override
    protected void exec(GetSetRedisValueMsg input, Handler<GetSetRedisValueMsg> nextTask) {
        if (input.getTimeout() == 0) {
            RedisClientFactory.getRedisClient().set(input.getKey(), input.getValue(), done -> {
                if (done.succeeded()) {
                    log.info("Set redis success with key: {}, value: {}", input.getKey(), input.getValue());
                } else {
                    log.error("Set redis fail with key: {}, value: {}", input.getKey(), input.getValue(), done.cause());
                }
                nextTask.handle(input);
            });
        } else {
            SetOptions options = new SetOptions();
            options.setEX(input.getTimeout());
            RedisClientFactory.getRedisClient().setWithOptions(input.getKey(), input.getValue(), options, done -> {
                if (done.succeeded()) {
                    log.info("Set redis success with key: {}, value: {}", input.getKey(), input.getValue());
                } else {
                    log.error("Set redis fail with key: {}, value: {}", input.getKey(), input.getValue(), done.cause());
                }
                nextTask.handle(input);
            });
        }
    }
}
