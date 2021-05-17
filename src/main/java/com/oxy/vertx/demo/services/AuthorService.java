package com.oxy.vertx.demo.services;

import com.oxy.vertx.demo.services.impl.AuthorServiceImpl;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@ProxyGen
public interface AuthorService {
    @GenIgnore
    static AuthorService create() {
        return new AuthorServiceImpl();
    }

    @GenIgnore
    static AuthorService createProxy(Vertx vertx, String address) {
        return new AuthorServiceVertxEBProxy(vertx, address);
    }

    void fetchAllAuthors(JsonObject input, Handler<AsyncResult<String>> resultHandler);

    void fetchAuthorsFromDB(JsonObject input, Handler<AsyncResult<String>> resultHandler);
}
