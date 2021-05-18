package com.oxy.vertx.demo.verticle;

import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.base.utils.JWTUtils;
import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.flow.StartUpFlow;
import com.oxy.vertx.demo.handler.AuthorHandler;
import com.oxy.vertx.demo.handler.LoginHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;

import java.util.HashSet;
import java.util.Set;

public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger log = Logger.getLogger(HttpServerVerticle.class);
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = routing();
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8029, http -> {
                if (http.succeeded()) {
                    new StartUpFlow().run(new StartUpMsg(), done -> {
                        if (!done.isBreakWorkFlow()) {
                            log.info("HttpServerVerticle start success");
                        } else {
                            log.error("HttpServerVerticle start fail");
                            System.exit(1);
                        }
                    });
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

        router.route("/api/*").handler(JWTAuthHandler.create(JWTUtils.getInstance().getProvider()));

        AuthorHandler authorHandler = new AuthorHandler(vertx);
        router.route(HttpMethod.GET, "/api/authors").handler(authorHandler::fetchAllAuthors);
        router.route(HttpMethod.GET, "/api/authors-db").handler(authorHandler::fetchAuthorsFromDB);

        LoginHandler loginHandler = new LoginHandler(vertx);
        router.route(HttpMethod.POST, "/login").handler(loginHandler::login);

        router.route().handler(CorsHandler.create("*").allowedHeaders(allowedHeaders).allowedMethods(allowedMethods));
        return router;
    }
}
