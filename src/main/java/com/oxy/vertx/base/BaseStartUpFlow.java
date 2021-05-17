package com.oxy.vertx.base;

import com.oxy.vertx.base.conf.CommonConfig;
import com.oxy.vertx.base.conf.CommonDbConfig;
import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.base.redis.RedisClientFactory;

public abstract class BaseStartUpFlow extends WarriorFlow<StartUpMsg> {
    protected BaseStartUpFlow() {
        addTask(new CommonConfig());
        addTask(new CommonDbConfig());
        addTask(new RedisClientFactory());
        addTask(getChildTask());
    }

    protected abstract WarriorTask<StartUpMsg> getChildTask();
}
