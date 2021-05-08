package com.oxy.vertx.demo.flow;

import com.oxy.vertx.base.BaseStartUpFlow;
import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.demo.task.statrt_up.StartHttpServerTask;
import com.oxy.vertx.base.OxyTask;

public class PromotionStartUpFlow extends BaseStartUpFlow {
    @Override
    protected OxyTask<StartUpMsg> getChildTask() {
        return new StartHttpServerTask();
    }
}
