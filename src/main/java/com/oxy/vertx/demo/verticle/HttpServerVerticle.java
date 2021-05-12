package com.oxy.vertx.demo.verticle;

import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.flow.DemoStartUpFlow;
import com.oxy.vertx.demo.handler.GetAllAuthorsHandler;
import com.oxy.vertx.demo.handler.HelloHandler;
import com.oxy.vertx.demo.handler.HiHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger log = Logger.getLogger(HttpServerVerticle.class);
    @Override
    public void start() throws Exception {
        log.warn("Http server is starting...");
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        server.requestHandler(router).listen(8003);
        router.route().handler(BodyHandler.create());
        routing(router);
        log.info("Start http server success");

        new DemoStartUpFlow().run(new StartUpMsg(), done -> {
            if (!done.isBreakWorkFlow()) {
                log.info("Service start success");
            } else {
                log.error("Service start fail");
                System.exit(1);
            }
        });
    }

    private void routing(Router router) {
        router.route(HttpMethod.GET, "/hello").handler(new HelloHandler());
        router.route(HttpMethod.GET, "/hi").handler(new HiHandler());
        router.route(HttpMethod.GET, "/authors").handler(new GetAllAuthorsHandler());
    }
}
