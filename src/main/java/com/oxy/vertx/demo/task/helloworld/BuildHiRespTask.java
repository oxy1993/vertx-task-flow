package com.oxy.vertx.demo.task.helloworld;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.demo.msg.ExecHiMsg;
import io.vertx.core.Handler;

public class BuildHiRespTask extends OxyTask<ExecHiMsg> {
    @Override
    protected void exec(ExecHiMsg input, Handler<ExecHiMsg> nextTask) {
        input.getResponse().setResult(0);
        input.getResponse().setDescription("Success");
        nextTask.handle(input);
    }
}
