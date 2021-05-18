package com.oxy.vertx.demo.handler;

import com.oxy.vertx.demo.constant.QueueName;
import com.oxy.vertx.demo.services.LoginService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class LoginHandler {
    private final LoginService loginService;

    public LoginHandler(Vertx vertx) {
        this.loginService = LoginService.createProxy(vertx, QueueName.LOGIN_QUEUE_NAME);
    }

    public void login(RoutingContext context) {
        loginService.login(new JsonObject(), res -> {
            if (res.succeeded()) {
                context.response().end(res.result());
            } else {
                context.response().setStatusCode(500).end(res.cause().getMessage());
            }
        });
    }
}
