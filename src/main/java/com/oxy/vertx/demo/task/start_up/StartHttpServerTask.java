package com.oxy.vertx.demo.task.start_up;

import com.oxy.vertx.base.conf.CommonConfig;
import com.oxy.vertx.demo.handler.GetAllAuthorsHandler;
import com.oxy.vertx.demo.handler.HiHandler;
import com.oxy.vertx.demo.handler.HelloHandler;
import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.entities.StartUpMsg;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

public class StartHttpServerTask extends OxyTask<StartUpMsg> {
    private Vertx vertx;

    @Override
    protected void exec(StartUpMsg input, Handler<StartUpMsg> nextTask) {
        JsonObject zkConfig = new JsonObject();
        JsonObject clusterConfig = CommonConfig.getConfigAsJson("cluster");
        zkConfig.put("zookeeperHosts", clusterConfig.getString("zookeeperHosts"));
        zkConfig.put("rootPath", "io.vertx");
        zkConfig.put("retry", new JsonObject()
                .put("initialSleepTime", 3000)
                .put("maxTimes", 3));

        ZookeeperClusterManager mgr = new ZookeeperClusterManager(zkConfig);
        VertxOptions options = new VertxOptions().setClusterManager(mgr);

        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                vertx = res.result();
                log.warn("Http server is starting...");
                HttpServer server = vertx.createHttpServer();
                Router router = Router.router(vertx);
                server.requestHandler(router).listen(8008);
                router.route().handler(BodyHandler.create());
                routing(router);
                log.info("Start http server success");
                nextTask.handle(input);
            }
        });
    }

    private void routing(Router router) {
        /**
         * TODO Add another api here by create a handler like HelloWorldHandler implements Handler<RoutingContext>
         * router.route(HttpMethod.POST, "/some_api").handler(new SomeHandler());
         */
        router.route(HttpMethod.POST, "/hello").handler(new HelloHandler());
        router.route(HttpMethod.GET, "/hi").handler(new HiHandler());
        router.route(HttpMethod.GET, "/authors").handler(new GetAllAuthorsHandler());
    }

}
