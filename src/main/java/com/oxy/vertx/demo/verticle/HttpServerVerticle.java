package com.oxy.vertx.demo.verticle;

import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.flow.LoadConfigFlow;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger log = Logger.getLogger(HttpServerVerticle.class);
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = routing();
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8010, http -> {
                if (http.succeeded()) {
                    startPromise.complete();
                    new LoadConfigFlow().run(new StartUpMsg(), done -> {
                        if (!done.isBreakWorkFlow()) {
                            log.info("Service start success");
                        } else {
                            log.error("Service start fail");
                            System.exit(1);
                        }
                    });
                    System.out.println("HTTP server started on port 8010");
                } else {
                    startPromise.fail(http.cause());
                }
            });
    }

    private Router routing() {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route(HttpMethod.GET, "/authors").handler(this::AuthorHandler);
        return router;
    }

    private void AuthorHandler(RoutingContext context) {
        vertx.eventBus().request("authors","", handler -> {
            if(handler.succeeded()){
                context.response()
                        .end(handler.result().body().toString());
            } else{
                context.response().setStatusCode(500)
                        .end(handler.cause().getMessage());
            }
        });
    }

}
