package com.oxy.vertx.base.rabbitmq;

import com.oxy.vertx.base.conf.WarriorContext;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
        CompletableFuture<RabbitMQClient> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return client;
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void initConnection(RabbitMQOptions config) {
        config.setUser("guest");
        config.setPassword("guest");
        config.setHost("localhost");
        config.setPort(5672);
        config.setConnectionTimeout(6000); // in milliseconds
        config.setRequestedHeartbeat(60); // in seconds
        config.setHandshakeTimeout(6000); // in milliseconds
        config.setRequestedChannelMax(5);
        config.setNetworkRecoveryInterval(500); // in milliseconds
        config.setAutomaticRecoveryEnabled(true);
        client = RabbitMQClient.create(WarriorContext.getVertx(), config);
        client.start(asyncResult -> {
            if (asyncResult.succeeded()) {
                System.out.println("RabbitMQ successfully connected!");
            } else {
                System.out.println("Fail to connect to RabbitMQ " + asyncResult.cause().getMessage());
            }
        });
    }
}
