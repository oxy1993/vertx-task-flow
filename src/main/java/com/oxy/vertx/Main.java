package com.oxy.vertx;

import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.verticle.AuthorConsumerVerticle;
import com.oxy.vertx.demo.verticle.HttpServerVerticle;
import com.oxy.vertx.demo.verticle.LoginConsumerVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    public static void main(String[] args) {
// ============================== 1 instance ============================================================
//        Vertx vertx = Vertx.vertx();
//        vertx.deployVerticle(HttpServerVerticle.class.getName());
//        vertx.deployVerticle(AuthorConsumerVerticle.class.getName());

// ============================== multi instance ============================================================
        JsonObject zkConfig = new JsonObject();

        // run local
        zkConfig.put("zookeeperHosts", "localhost");

        // run on ec2
//        zkConfig.put("zookeeperHosts", "172.31.46.15");
        zkConfig.put("rootPath", "io.vertx");

        zkConfig.put("retry", new JsonObject()
                .put("initialSleepTime", 3000)
                .put("maxTimes", 3));

        ZookeeperClusterManager mgr = new ZookeeperClusterManager(zkConfig);
        VertxOptions options = new VertxOptions().setClusterManager(mgr);

        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                DeploymentOptions deploymentOptions = new DeploymentOptions().setInstances(1);
                vertx.deployVerticle(HttpServerVerticle.class.getName(), deploymentOptions);
                vertx.deployVerticle(AuthorConsumerVerticle.class.getName(), deploymentOptions);
                vertx.deployVerticle(LoginConsumerVerticle.class.getName(), deploymentOptions);
            }
        });
    }
}
