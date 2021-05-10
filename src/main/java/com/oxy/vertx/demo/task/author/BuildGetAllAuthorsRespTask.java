package com.oxy.vertx.demo.task.author;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.demo.msg.ExecGetAllAuthorsMsg;
import io.vertx.core.Handler;

public class BuildGetAllAuthorsRespTask extends OxyTask<ExecGetAllAuthorsMsg> {
    @Override
    protected void exec(ExecGetAllAuthorsMsg input, Handler<ExecGetAllAuthorsMsg> nextTask) {
        input.getResponse().setResult(0);
        input.getResponse().setDescription("Success");
        nextTask.handle(input);
    }
}
