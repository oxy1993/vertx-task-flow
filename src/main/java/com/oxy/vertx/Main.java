package com.oxy.vertx;

import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.verticle.HttpServerVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        JsonObject zkConfig = new JsonObject();
//        zkConfig.put("zookeeperHosts", "localhost");
        zkConfig.put("zookeeperHosts", "172.31.46.15");
        zkConfig.put("rootPath", "io.vertx");
        zkConfig.put("retry", new JsonObject()
                .put("initialSleepTime", 3000)
                .put("maxTimes", 3));

        ZookeeperClusterManager mgr = new ZookeeperClusterManager(zkConfig);
        VertxOptions options = new VertxOptions().setClusterManager(mgr);

        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                DeploymentOptions deploymentOptions = new DeploymentOptions().setInstances(3);
                vertx.deployVerticle(HttpServerVerticle.class.getName(), deploymentOptions);
            }
        });
    }
}
