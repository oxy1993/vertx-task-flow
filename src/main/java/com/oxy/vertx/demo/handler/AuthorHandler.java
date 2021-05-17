package com.oxy.vertx.demo.handler;

import com.oxy.vertx.demo.constant.QueueName;
import com.oxy.vertx.demo.services.AuthorService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AuthorHandler {
    private final AuthorService authorService;

    public AuthorHandler(Vertx vertx) {
        this.authorService = AuthorService.createProxy(vertx, QueueName.AUTHOR_QUEUE_NAME);
    }

    public void fetchAllAuthors(RoutingContext context) {
        authorService.fetchAllAuthors(new JsonObject().put("time", System.currentTimeMillis()), res -> {
            if (res.succeeded()) {
                context.response().end(res.result());
            } else {
                context.response().setStatusCode(500).end(res.cause().getMessage());
            }
        });
    }

    public void fetchAuthorsFromDB(RoutingContext context) {
        authorService.fetchAuthorsFromDB(new JsonObject().put("time", System.currentTimeMillis()), res -> {
            if (res.succeeded()) {
                context.response().end(res.result());
            } else {
                context.response().setStatusCode(500).end(res.cause().getMessage());
            }
        });
    }
}
