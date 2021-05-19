package com.oxy.vertx.base.conf;

import io.vertx.core.Vertx;

public class WarriorContext {
    private static Vertx vertx;

    public static Vertx getVertx() {
        if (vertx == null) {
            vertx = Vertx.vertx();
        }
        return vertx;
    }
}
