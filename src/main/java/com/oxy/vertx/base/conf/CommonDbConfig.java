package com.oxy.vertx.base.conf;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.entities.BaseRequest;
import com.oxy.vertx.base.jdbc.BaseJDBCClientImpl;
import com.oxy.vertx.base.redis.RedisClientFactory;
import io.vertx.core.Handler;

import java.util.HashMap;
import java.util.Map;

public class CommonDbConfig extends OxyTask<BaseRequest> {

    private static final String SQL = "SELECT * FROM config";
    private static Map<String, String> config;

    public CommonDbConfig() {
        config = new HashMap<>();
    }

    public static String getConfig(String key) {
        return config.get(key);
    }

    public static String getConfig(String key, String def) {
        return config.getOrDefault(key, def);
    }

    @Override
    protected void exec(BaseRequest input, Handler<BaseRequest> nextTask) {
        loadDbConfig(input, nextTask);
        String topic = CommonConfig.getConfigAsString("redis_topic", "DB_CONFIG");
        RedisClientFactory.createRedisPubSubClient(topic, result -> {
            if (result != null) {
                log.info("Received new event reload DB config from topic: {}", topic);
                loadDbConfig(input);
            }
        });
    }

    private void loadDbConfig(BaseRequest input, Handler<BaseRequest> nextTask) {
        BaseJDBCClientImpl.getClient().doQuery(SQL, DbConfig.class, done -> {
            log.info("Get config from config success with size {}", done.size());
            Map<String, String> tempConfig = new HashMap<>();
            done.forEach(dbConfig -> tempConfig.put(dbConfig.getKey_id(), dbConfig.getValue()));
            config = tempConfig;
            if (nextTask != null) {
                nextTask.handle(input);
            }
        });
    }

    private void loadDbConfig(BaseRequest input) {
        loadDbConfig(input, null);
    }

}
