package com.oxy.vertx.demo.task.helloworld;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.demo.msg.ExecHelloMsg;
import com.oxy.vertx.demo.msg.HelloResponseMsg;
import io.vertx.core.Handler;

public class ExecHelloTask extends OxyTask<ExecHelloMsg> {
    @Override
    protected void exec(ExecHelloMsg input, Handler<ExecHelloMsg> nextTask) {
        HelloResponseMsg responseMsg = input.createResponse(HelloResponseMsg.class);
        responseMsg.setGreeting("Hello " + input.getName());
        nextTask.handle(input);
    }
}
