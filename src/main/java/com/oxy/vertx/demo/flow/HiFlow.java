package com.oxy.vertx.demo.flow;

import com.oxy.vertx.base.OxyFlow;
import com.oxy.vertx.demo.msg.ExecHiMsg;
import com.oxy.vertx.demo.task.helloworld.*;

public class HiFlow extends OxyFlow<ExecHiMsg> {
    public HiFlow() {
        addTask(new ExecHiTask());
        addTask(new BuildHiRespTask());
    }
}
