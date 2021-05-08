package com.oxy.vertx.demo.task.helloworld;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.utils.StringUtils;
import com.oxy.vertx.demo.msg.ExecHelloWorldMsg;
import io.vertx.core.Handler;

public class ValidateHelloWorldReqTask extends OxyTask<ExecHelloWorldMsg> {
    @Override
    protected void exec(ExecHelloWorldMsg input, Handler<ExecHelloWorldMsg> nextTask) {
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
