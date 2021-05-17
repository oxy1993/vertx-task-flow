package com.oxy.vertx.base.redis;

import com.oxy.vertx.base.WarriorTask;
import com.oxy.vertx.base.conf.CommonConfig;
import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.base.utils.Logger;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.impl.SocketAddressImpl;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;

import java.util.ArrayList;
import java.util.List;


public class RedisClientFactory extends WarriorTask<StartUpMsg> {
    private static final Logger log = Logger.getLogger(RedisClientFactory.class);
    private static RedisClient redisClient;

    public static RedisClient getRedisClient() {
        if (redisClient == null) {
            redisClient = RedisClient.create(CommonConfig.getVertx(), new RedisOptions(CommonConfig.getConfigAsJson("redis")));
        }
        return redisClient;
    }

    public static void createRedisPubSubClient(String topic, Handler<String> resultHandler) {
        JsonObject redisConfig = CommonConfig.getConfigAsJson("redis");
        io.vertx.redis.client.impl.RedisClient.create(CommonConfig.getVertx(),
                new io.vertx.redis.client.RedisOptions()
                        .setEndpoint(new SocketAddressImpl( redisConfig.getInteger("port"), redisConfig.getString("host"))))
                .connect(onConnect -> {
                    if (onConnect.succeeded()) {
                        Redis client = onConnect.result();
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
        redisClient = RedisClient.create(CommonConfig.getVertx(), new RedisOptions(redisConfig));
        nextTask.handle(input);
    }
}
