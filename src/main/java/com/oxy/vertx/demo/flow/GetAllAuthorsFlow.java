package com.oxy.vertx.demo.flow;

import com.oxy.vertx.base.WarriorFlow;
import com.oxy.vertx.demo.msg.ExecGetAllAuthorsMsg;
import com.oxy.vertx.demo.task.author.ExecGetAllAuthorsTask;

import javax.inject.Singleton;

@Singleton
public class GetAllAuthorsFlow extends WarriorFlow<ExecGetAllAuthorsMsg> {
    public GetAllAuthorsFlow() {
        addTask(new ExecGetAllAuthorsTask());
    }
}
