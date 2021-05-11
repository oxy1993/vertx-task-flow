package com.oxy.vertx.demo.flow;

import com.oxy.vertx.base.BaseStartUpFlow;
import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.demo.task.start_up.StartHttpServerTask;

public class DemoStartUpFlow extends BaseStartUpFlow {
    @Override
    protected OxyTask<StartUpMsg> getChildTask() {
        return new StartHttpServerTask();
    }
}
