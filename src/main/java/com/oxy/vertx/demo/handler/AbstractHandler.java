package com.oxy.vertx.demo.handler;

import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.base.utils.Logger;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public abstract class AbstractHandler implements Handler<RoutingContext> {
    protected final Logger log = Logger.getLogger(this.getClass());

    protected void sendResponse(HttpServerResponse response, String rep, int statusCode) {
        response.setChunked(true)
                .putHeader("content-type", "application/json")
                .setStatusCode(statusCode)
                .write(rep, "UTF-8");
        response.end();
    }

    protected <T> T validateHttpRequest(RoutingContext routingContext, Class<T> clazz) {
        if (routingContext.getBody() == null || routingContext.getBodyAsJson() == null) {
            log.error("Request body is null");
            sendResponse(routingContext.response(), "", HttpResponseStatus.BAD_REQUEST.code());
            return null;
        }
        T t = JsonUtils.jsonToObj(routingContext.getBodyAsJson(), clazz);
        if (t == null) {
            log.error("Request body invalid " + routingContext.getBodyAsJson());
            sendResponse(routingContext.response(), "", HttpResponseStatus.BAD_REQUEST.code());
            return null;
        }
        log.info("Processing with request: {}", routingContext.getBodyAsJson());
        return t;
    }

}
