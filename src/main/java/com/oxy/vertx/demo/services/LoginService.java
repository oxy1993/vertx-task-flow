package com.oxy.vertx.demo.services;

import com.oxy.vertx.demo.services.impl.LoginServiceImpl;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@ProxyGen
public interface LoginService {
    @GenIgnore
    static LoginService create() {
        return new LoginServiceImpl();
    }

    @GenIgnore
    static LoginService createProxy(Vertx vertx, String address) {
        return new LoginServiceVertxEBProxy(vertx, address);
    }

    void login(JsonObject input, Handler<AsyncResult<String>> resultHandler);
}
