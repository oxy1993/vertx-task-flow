package com.oxy.vertx.demo.verticle;

import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.constant.Action;
import com.oxy.vertx.demo.constant.QueueName;
import com.oxy.vertx.demo.flow.LoadConfigFlow;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

import java.util.HashSet;
import java.util.Set;

public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger log = Logger.getLogger(HttpServerVerticle.class);
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = routing();
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8027, http -> {
                if (http.succeeded()) {
                    new LoadConfigFlow().run(new StartUpMsg(), done -> {
                        if (!done.isBreakWorkFlow()) {
                            log.info("Service start success");
                        } else {
                            log.error("Service start fail");
                            System.exit(1);
                        }
                    });
                    log.info("HTTP server started on port 8020");
                    startPromise.complete();
                } else {
                    startPromise.fail(http.cause());
                }
            });
    }

    private Router routing() {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("x-requested-with");
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("origin");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("accept");
        allowedHeaders.add("X-PINGARUNER");

        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);
        allowedMethods.add(HttpMethod.DELETE);
        allowedMethods.add(HttpMethod.PATCH);
        allowedMethods.add(HttpMethod.PUT);
        router.route(HttpMethod.GET, "/authors").handler(this::AuthorHandler);
        router.route().handler(CorsHandler.create("*").allowedHeaders(allowedHeaders).allowedMethods(allowedMethods));
        return router;
    }

    private void AuthorHandler(RoutingContext context) {
        JsonObject message = new JsonObject().put("time", System.currentTimeMillis());
        DeliveryOptions options = new DeliveryOptions().addHeader("action", Action.GET_AUTHORS);
        vertx.eventBus().request(QueueName.AUTHOR_QUEUE_NAME, message, options, reply -> {
            if(reply.succeeded()){
                context.response()
                        .end(reply.result().body().toString());
            } else{
                context.response().setStatusCode(500)
                        .end(reply.cause().getMessage());
            }
        });
    }

}
