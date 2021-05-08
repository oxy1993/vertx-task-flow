package com.oxy.vertx.demo.flow;

import com.oxy.vertx.demo.task.helloworld.BuildHelloWorldRespTask;
import com.oxy.vertx.demo.task.helloworld.ExecHelloWorldTask;
import com.oxy.vertx.demo.task.helloworld.ValidateHelloWorldReqTask;
import com.oxy.vertx.base.OxyFlow;
import com.oxy.vertx.demo.msg.ExecHelloWorldMsg;

import javax.inject.Singleton;

@Singleton
public class HelloWorldFlow extends OxyFlow<ExecHelloWorldMsg> {
    public HelloWorldFlow() {
        addTask(new ValidateHelloWorldReqTask());
        addTask(new ExecHelloWorldTask());
        addTask(new BuildHelloWorldRespTask());
    }
}
