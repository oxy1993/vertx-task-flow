package com.oxy.vertx.base.redis;

import com.oxy.vertx.base.WarriorTask;
import com.oxy.vertx.base.conf.CommonConfig;
import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.base.utils.Logger;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.impl.SocketAddressImpl;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class RedisClientFactory extends WarriorTask<StartUpMsg> {
    private static final Logger log = Logger.getLogger(RedisClientFactory.class);
    private static Redis redisClient;
    private static RedisAPI redis;

    public static RedisAPI getRedisClient() {
        if (redisClient == null) {
            redisClient = Redis.createClient(CommonConfig.getVertx(), new RedisOptions(CommonConfig.getConfigAsJson("redis")));
            redis = RedisAPI.api(redisClient);
        }
        return redis;
    }

    public static void createRedisPubSubClient(String topic, Handler<String> resultHandler) {
        JsonObject redisConfig = CommonConfig.getConfigAsJson("redis");
        Redis.createClient(CommonConfig.getVertx(),
                new RedisOptions().setEndpoints(Collections.singletonList("redis://" + redisConfig.getString("host") + ":" + redisConfig.getInteger("port").toString())))
                .connect(onConnect -> {
                    if (onConnect.succeeded()) {
                        RedisConnection client = onConnect.result();
                        if (client != null) {
                            List<String> agrs = new ArrayList<>();
                            agrs.add(topic);
                            RedisAPI.api(client).subscribe(agrs, done -> {
                                if (done.succeeded()) {
                                    client.handler(response -> resultHandler.handle(response.toString()));
                                } else {
                                    log.error("Unable to subscribe redis client", done.cause());
                                }
                            });
                        }
                    } else {
                        log.error("Redis client can not connect");
                    }
                });
    }

    @Override
    protected void exec(StartUpMsg input, Handler<StartUpMsg> nextTask) {
        JsonObject redisConfig = CommonConfig.getConfigAsJson("redis");
        log.info("Connect to redis with config: {}", redisConfig);
        redisClient = Redis.createClient(CommonConfig.getVertx(), new RedisOptions(redisConfig));
        redis = RedisAPI.api(redisClient);
        nextTask.handle(input);
    }
}
