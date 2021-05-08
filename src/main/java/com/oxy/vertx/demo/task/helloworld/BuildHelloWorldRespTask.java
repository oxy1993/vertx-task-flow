package com.oxy.vertx.demo.task.helloworld;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.demo.msg.ExecHelloWorldMsg;
import io.vertx.core.Handler;

public class BuildHelloWorldRespTask extends OxyTask<ExecHelloWorldMsg> {
    @Override
    protected void exec(ExecHelloWorldMsg input, Handler<ExecHelloWorldMsg> nextTask) {
        input.getResponse().setResult(0);
        input.getResponse().setDescription("Success");
        nextTask.handle(input);
    }
}
