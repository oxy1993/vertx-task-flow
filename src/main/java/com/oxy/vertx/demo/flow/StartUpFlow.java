package com.oxy.vertx.demo.flow;

import com.oxy.vertx.base.BaseStartUpFlow;
import com.oxy.vertx.base.WarriorTask;
import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.demo.task.rabbitmq.RabbitMQConsumerTask;

import javax.inject.Singleton;

@Singleton
public class StartUpFlow extends BaseStartUpFlow {
    @Override
    protected WarriorTask<StartUpMsg> getChildTask() {
        return new RabbitMQConsumerTask();
    }
}
