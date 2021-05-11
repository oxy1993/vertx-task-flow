package com.oxy.vertx.demo.handler;

import io.vertx.ext.web.RoutingContext;

public class HelloHandler extends AbstractHandler {
    @Override
    public void handle(RoutingContext routingContext) {
        log.info("hello world");
        sendResponse(routingContext.response(), "hello world", 200);
    }
}
