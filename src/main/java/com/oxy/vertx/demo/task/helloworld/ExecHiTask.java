package com.oxy.vertx.demo.task.helloworld;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.demo.msg.ExecHiMsg;
import com.oxy.vertx.demo.msg.HiResponseMsg;
import io.vertx.core.Handler;

public class ExecHiTask extends OxyTask<ExecHiMsg> {
    @Override
    protected void exec(ExecHiMsg input, Handler<ExecHiMsg> nextTask) {
        HiResponseMsg responseMsg = input.createResponse(HiResponseMsg.class);
        responseMsg.setGreeting("Hi " + input.getName());
        nextTask.handle(input);
    }
}
