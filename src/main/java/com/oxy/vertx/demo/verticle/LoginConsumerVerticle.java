package com.oxy.vertx.demo.verticle;

import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.constant.VertxQueueName;
import com.oxy.vertx.demo.services.LoginService;
import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ServiceBinder;

public class LoginConsumerVerticle extends AbstractVerticle {
    private static final Logger log = Logger.getLogger(LoginConsumerVerticle.class);
    @Override
    public void start(){
        LoginService loginService = LoginService.create();
        new ServiceBinder(vertx)
                .setAddress(VertxQueueName.LOGIN_QUEUE_NAME)
                .register(LoginService.class, loginService);
        log.info("LoginConsumerVerticle start success");
    }
}
