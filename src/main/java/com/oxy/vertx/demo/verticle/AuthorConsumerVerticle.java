package com.oxy.vertx.demo.verticle;

import com.oxy.vertx.demo.constant.QueueName;
import com.oxy.vertx.demo.services.AuthorService;
import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ServiceBinder;

public class AuthorConsumerVerticle extends AbstractVerticle {
    @Override
    public void start(){
        AuthorService authorService = AuthorService.create();
        new ServiceBinder(vertx)
                .setAddress(QueueName.AUTHOR_QUEUE_NAME)
                .register(AuthorService.class, authorService);
    }
}
