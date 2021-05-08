package com.oxy.vertx.demo.task.helloworld;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.utils.StringUtils;
import com.oxy.vertx.demo.msg.ExecHelloMsg;
import io.vertx.core.Handler;

public class ValidateHelloReqTask extends OxyTask<ExecHelloMsg> {
    @Override
    protected void exec(ExecHelloMsg input, Handler<ExecHelloMsg> nextTask) {
        if (StringUtils.isNullOrEmpty(input.getName())) {
            log.error("Name is null");
            input.fail(1);
            nextTask.handle(input);
            return;
        }
        log.info("Validate promotion request passed");
        nextTask.handle(input);
    }
}
