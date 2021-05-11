package com.oxy.vertx;

import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.task.start_up.HttpServerVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        JsonObject zkConfig = new JsonObject();
        zkConfig.put("zookeeperHosts", "172.31.20.253");
        zkConfig.put("rootPath", "io.vertx");
        zkConfig.put("retry", new JsonObject()
                .put("initialSleepTime", 3000)
                .put("maxTimes", 3));

        ZookeeperClusterManager mgr = new ZookeeperClusterManager(zkConfig);
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
//        EventBusOptions eventBusOptions = new EventBusOptions();
//        eventBusOptions.setClustered(true);
//        eventBusOptions.setHost("localhost");
//        eventBusOptions.setPort(8008);
//        eventBusOptions.setClusterPublicHost("localhost");
//        eventBusOptions.setClusterPublicPort(8008);
//        options.setEventBusOptions(eventBusOptions);

        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                DeploymentOptions deploymentOptions = new DeploymentOptions().setInstances(7);
                vertx.deployVerticle(HttpServerVerticle.class.getName(), deploymentOptions);
            }
        });
    }
}
