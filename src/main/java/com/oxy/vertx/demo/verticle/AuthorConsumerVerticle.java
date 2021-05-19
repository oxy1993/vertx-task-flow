package com.oxy.vertx.demo.verticle;

import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.constant.VertxQueueName;
import com.oxy.vertx.demo.services.AuthorService;
import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ServiceBinder;

public class AuthorConsumerVerticle extends AbstractVerticle {
    private static final Logger log = Logger.getLogger(AuthorConsumerVerticle.class);
    @Override
    public void start(){
        AuthorService authorService = AuthorService.create();
        new ServiceBinder(vertx)
                .setAddress(VertxQueueName.AUTHOR_QUEUE_NAME)
                .register(AuthorService.class, authorService);
        log.info("AuthorConsumerVerticle start success");
    }
}
