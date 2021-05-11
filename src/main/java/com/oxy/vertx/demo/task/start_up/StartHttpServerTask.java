package com.oxy.vertx.demo.task.start_up;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.entities.StartUpMsg;
import io.vertx.core.Handler;

public class StartHttpServerTask extends OxyTask<StartUpMsg> {
    @Override
    protected void exec(StartUpMsg input, Handler<StartUpMsg> nextTask) {
        nextTask.handle(input);
    }
}
