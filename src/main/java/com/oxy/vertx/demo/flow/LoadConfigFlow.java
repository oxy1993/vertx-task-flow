package com.oxy.vertx.demo.flow;

import com.oxy.vertx.base.BaseStartUpFlow;
import com.oxy.vertx.base.WarriorTask;
import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.demo.task.start_up.StartHttpServerTask;

import javax.inject.Singleton;

@Singleton
public class LoadConfigFlow extends BaseStartUpFlow {
    @Override
    protected WarriorTask<StartUpMsg> getChildTask() {
        return new StartHttpServerTask();
    }
}
