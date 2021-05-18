package com.oxy.vertx.demo.handler;

import com.oxy.vertx.demo.constant.QueueName;
import com.oxy.vertx.demo.services.AuthorService;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.RoutingContext;

public class AuthorHandler {
    private final AuthorService authorService;

    public AuthorHandler(Vertx vertx) {
        this.authorService = AuthorService.createProxy(vertx, QueueName.AUTHOR_QUEUE_NAME);
    }

    public void fetchAllAuthors(RoutingContext context) {
//        String authorization = context.request().headers().get(HttpHeaders.AUTHORIZATION);
//
//        if (authorization == null) {
//            context.fail(401);
//            return;
//        } else {
//            String scheme;
//            String token;
//            try {
//                String[] parts = authorization.split(" ");
//                scheme = parts[0];
//                token = parts[1];
//            } catch (ArrayIndexOutOfBoundsException e) {
//                context.fail(401);
//                return;
//            } catch (IllegalArgumentException | NullPointerException e) {
//                context.fail(e);
//                return;
//            }
//
//            if (scheme.equalsIgnoreCase("bearer")) {
//                JsonObject creds = new JsonObject();
//                creds.put("token", token);
//            } else {
//                context.fail(401);
//            }
//        }

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
