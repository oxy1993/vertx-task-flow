package com.oxy.vertx.demo.flow;

import com.oxy.vertx.demo.task.helloworld.BuildHelloRespTask;
import com.oxy.vertx.demo.task.helloworld.ExecHelloTask;
import com.oxy.vertx.demo.task.helloworld.ValidateHelloReqTask;
import com.oxy.vertx.base.OxyFlow;
import com.oxy.vertx.demo.msg.ExecHelloMsg;

import javax.inject.Singleton;

@Singleton
public class HelloFlow extends OxyFlow<ExecHelloMsg> {
    public HelloFlow() {
        addTask(new ValidateHelloReqTask());
        addTask(new ExecHelloTask());
        addTask(new BuildHelloRespTask());
    }
}
