package com.oxy.vertx.base.rabbitmq;

import com.oxy.vertx.base.conf.CommonConfig;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;

public class RabbitConnector {
    private static RabbitMQClient client;

    private RabbitConnector() {
        RabbitMQOptions config = new RabbitMQOptions();
        initConnection(config);
    }

    private static class RabbitConnectorHolder {
        static final RabbitConnector instance = new RabbitConnector();
    }

    public static RabbitConnector getConnection() {
        return RabbitConnectorHolder.instance;
    }

    public RabbitMQClient getClient() {
        return client;
    }

    private static void initConnection(RabbitMQOptions config) {
        config.setUser("guest");
        config.setPassword("guest");
        config.setHost("localhost");
        config.setPort(5672);
        config.setVirtualHost("vhost1");
        config.setConnectionTimeout(6000); // in milliseconds
        config.setRequestedHeartbeat(60); // in seconds
        config.setHandshakeTimeout(6000); // in milliseconds
        config.setRequestedChannelMax(5);
        config.setNetworkRecoveryInterval(10000); // in milliseconds
        config.setAutomaticRecoveryEnabled(true);
        client = RabbitMQClient.create(CommonConfig.getVertx(), config);
        client.start(asyncResult -> {
            if (asyncResult.succeeded()) {
                System.out.println("RabbitMQ successfully connected!");
            } else {
                System.out.println("Fail to connect to RabbitMQ " + asyncResult.cause().getMessage());
            }
        });
    }
}
