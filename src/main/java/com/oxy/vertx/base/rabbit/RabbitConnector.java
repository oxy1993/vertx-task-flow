package com.oxy.vertx.base.rabbit;

import io.vertx.core.Vertx;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;

public class RabbitConnector {
    private static Vertx vertx;
    private static RabbitMQClient client;

    public RabbitConnector() {
        vertx = Vertx.vertx();
        RabbitMQOptions config = new RabbitMQOptions();
        initConnection(config);
    }

    public static RabbitMQClient getConnection() {
        return client;
    }

    public static void initConnection(RabbitMQOptions config) {
        config.setUser("user1");
        config.setPassword("password1");
        config.setHost("localhost");
        config.setPort(5672);
        config.setVirtualHost("vhost1");
        config.setConnectionTimeout(6000); // in milliseconds
        config.setRequestedHeartbeat(60); // in seconds
        config.setHandshakeTimeout(6000); // in milliseconds
        config.setRequestedChannelMax(5);
        config.setNetworkRecoveryInterval(500); // in milliseconds
        config.setAutomaticRecoveryEnabled(true);
        client = RabbitMQClient.create(vertx, config);
        client.start(asyncResult ->
        {
            if (asyncResult.succeeded()) {
                System.out.println("RabbitMQ successfully connected!");
            } else {
                System.out.println("Fail to connect to RabbitMQ " + asyncResult.cause().getMessage());
            }
        });
    }
}
