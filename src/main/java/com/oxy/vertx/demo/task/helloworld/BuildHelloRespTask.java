package com.oxy.vertx.demo.task.helloworld;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.demo.msg.ExecHelloMsg;
import io.vertx.core.Handler;

public class BuildHelloRespTask extends OxyTask<ExecHelloMsg> {
    @Override
    protected void exec(ExecHelloMsg input, Handler<ExecHelloMsg> nextTask) {
        input.getResponse().setResult(0);
        input.getResponse().setDescription("Success");
        nextTask.handle(input);
    }
}
