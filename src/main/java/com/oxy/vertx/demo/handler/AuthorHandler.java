package com.oxy.vertx.demo.handler;

import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.constant.VertxQueueName;
import com.oxy.vertx.demo.services.AuthorService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AuthorHandler {
    private final AuthorService authorService;
    private static final Logger log = Logger.getLogger(AuthorHandler.class);

    public AuthorHandler(Vertx vertx) {
        this.authorService = AuthorService.createProxy(vertx, VertxQueueName.AUTHOR_QUEUE_NAME);
    }

    public void fetchAllAuthors(RoutingContext context) {
        var jwtUser = context.user();
        String username = jwtUser.principal().getString("username");
        log.info("Context user ======> {}", username);

        authorService.fetchAllAuthors(new JsonObject().put("time", System.currentTimeMillis()), res -> {
            if (res.succeeded()) {
                context.response().end(res.result());
            } else {
                context.response().setStatusCode(500).end(res.cause().getMessage());
            }
        });
    }

    public void fetchAuthorsFromDB(RoutingContext context) {
        var jwtUser = context.user();
        String username = jwtUser.principal().getString("username");
        log.info("Context user ======> {}", username);

        authorService.fetchAuthorsFromDB(new JsonObject().put("time", System.currentTimeMillis()), res -> {
            if (res.succeeded()) {
                context.response().end(res.result());
            } else {
                context.response().setStatusCode(500).end(res.cause().getMessage());
            }
        });
    }
}
