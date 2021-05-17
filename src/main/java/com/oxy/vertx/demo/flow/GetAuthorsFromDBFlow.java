package com.oxy.vertx.demo.flow;

import com.oxy.vertx.base.OxyFlow;
import com.oxy.vertx.demo.msg.ExecGetAllAuthorsMsg;
import com.oxy.vertx.demo.task.author.ExecGetAllAuthorsTask;
import com.oxy.vertx.demo.task.author.ExecGetAuthorsFromDBTask;

import javax.inject.Singleton;

@Singleton
public class GetAuthorsFromDBFlow extends OxyFlow<ExecGetAllAuthorsMsg> {
    public GetAuthorsFromDBFlow() {
        addTask(new ExecGetAuthorsFromDBTask());
    }
}
