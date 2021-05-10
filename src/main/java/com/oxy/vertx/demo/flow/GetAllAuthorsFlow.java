package com.oxy.vertx.demo.flow;

import com.oxy.vertx.base.OxyFlow;
import com.oxy.vertx.demo.msg.ExecGetAllAuthorsMsg;
import com.oxy.vertx.demo.task.helloworld.BuildGetAllAuthorsRespTask;
import com.oxy.vertx.demo.task.helloworld.ExecGetAllAuthorsTask;

import javax.inject.Singleton;

@Singleton
public class GetAllAuthorsFlow extends OxyFlow<ExecGetAllAuthorsMsg> {
    public GetAllAuthorsFlow() {
        addTask(new ExecGetAllAuthorsTask());
        addTask(new BuildGetAllAuthorsRespTask());
    }
}
