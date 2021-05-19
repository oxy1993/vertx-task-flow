package com.oxy.vertx.base;

import com.oxy.vertx.base.conf.WarriorConfig;
import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.base.redis.RedisClientFactory;

public abstract class BaseStartUpFlow extends WarriorFlow<StartUpMsg> {
    protected BaseStartUpFlow() {
        addTask(new WarriorConfig());
        addTask(new RedisClientFactory());
        addTask(getChildTask());
    }

    protected abstract WarriorTask<StartUpMsg> getChildTask();
}
