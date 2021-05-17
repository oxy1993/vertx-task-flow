package com.oxy.vertx.demo.task.start_up;

import com.oxy.vertx.base.WarriorTask;
import com.oxy.vertx.base.entities.StartUpMsg;
import io.vertx.core.Handler;

public class StartHttpServerTask extends WarriorTask<StartUpMsg> {
    @Override
    protected void exec(StartUpMsg input, Handler<StartUpMsg> nextTask) {
        nextTask.handle(input);
    }
}
