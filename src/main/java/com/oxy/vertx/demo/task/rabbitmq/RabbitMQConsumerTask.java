package com.oxy.vertx.demo.task.rabbitmq;

import com.oxy.vertx.base.WarriorTask;
import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.base.rabbitmq.RabbitConnector;
import io.vertx.core.Handler;
import io.vertx.rabbitmq.QueueOptions;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQConsumer;

public class RabbitMQConsumerTask extends WarriorTask<StartUpMsg> {
    @Override
    protected void exec(StartUpMsg input, Handler<StartUpMsg> nextTask) {
        QueueOptions options = new QueueOptions()
                .setMaxInternalQueueSize(1000)
                .setKeepMostRecent(true);

        RabbitMQClient rabbitMQClient = RabbitConnector.getConnection().getClient();
        if (rabbitMQClient != null) {
            rabbitMQClient.basicConsumer("my.queue", options, rabbitMQConsumerAsyncResult -> {
                if (rabbitMQConsumerAsyncResult.succeeded()) {
                    System.out.println("RabbitMQ consumer created !");
                    RabbitMQConsumer mqConsumer = rabbitMQConsumerAsyncResult.result();
                    mqConsumer.handler(message -> {
                        System.out.println("Got message: " + message.body().toString());
                    });
                } else {
                    rabbitMQConsumerAsyncResult.cause().printStackTrace();
                }
                nextTask.handle(input);
            });
        }
    }
}
