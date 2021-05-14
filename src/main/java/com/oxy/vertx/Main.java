package com.oxy.vertx;

import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.verticle.AuthorVerticle;
import com.oxy.vertx.demo.verticle.HttpServerVerticle;
import io.vertx.core.Vertx;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(HttpServerVerticle.class.getName());
        vertx.deployVerticle(AuthorVerticle.class.getName());
    }
}
