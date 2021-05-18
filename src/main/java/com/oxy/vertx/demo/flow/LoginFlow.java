package com.oxy.vertx.demo.flow;

import com.oxy.vertx.base.WarriorFlow;
import com.oxy.vertx.demo.msg.LoginMsg;
import com.oxy.vertx.demo.task.login.ExecLoginTask;

import javax.inject.Singleton;

@Singleton
public class LoginFlow extends WarriorFlow<LoginMsg> {
    public LoginFlow() {
        addTask(new ExecLoginTask());
    }
}
