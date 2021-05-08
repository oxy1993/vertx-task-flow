package com.oxy.vertx.demo.task.helloworld;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.demo.msg.ExecHelloWorldMsg;
import com.oxy.vertx.demo.msg.HelloWorldResponseMsg;
import io.vertx.core.Handler;

public class ExecHelloWorldTask extends OxyTask<ExecHelloWorldMsg> {
    @Override
    protected void exec(ExecHelloWorldMsg input, Handler<ExecHelloWorldMsg> nextTask) {
        HelloWorldResponseMsg responseMsg = input.createResponse(HelloWorldResponseMsg.class);
        responseMsg.setGreeting("Hello " + input.getName());
        nextTask.handle(input);
    }
}
