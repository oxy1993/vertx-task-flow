package com.oxy.vertx.base.conf;

import com.oxy.vertx.base.WarriorTask;
import com.oxy.vertx.base.entities.BaseRequest;
import com.oxy.vertx.base.utils.Logger;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class WarriorConfig extends WarriorTask<BaseRequest> {
    private static JsonObject config;

    public static JsonObject getConfigAsJson(String key) {
        return config.getJsonObject(key);
    }

    public static JsonObject getConfigAsJson(String key, JsonObject def) {
        return config.getJsonObject(key, def);
    }

    public static JsonArray getConfigAsJsonArray(String key) {
        return config.getJsonArray(key);
    }

    public static JsonArray getConfigAsJsonArray(String key, JsonArray def) {
        return config.getJsonArray(key, def);
    }

    public static double getConfigAsDouble(String key) {
        return config.getDouble(key);
    }

    public static double getConfigAsDouble(String key, double def) {
        return config.getDouble(key, def);
    }

    public static float getConfigAsFloat(String key) {
        return config.getFloat(key);
    }

    public static float getConfigAsDouble(String key, float def) {
        return config.getFloat(key, def);
    }

    public static long getConfigAsLong(String key) {
        return config.getLong(key);
    }

    public static long getConfigAsLong(String key, long def) {
        return config.getLong(key, def);
    }

    public static int getConfigAsInteger(String key) {
        return config.getInteger(key);
    }

    public static int getConfigAsInteger(String key, int def) {
        return config.getInteger(key, def);
    }

    public static String getConfigAsString(String key) {
        return config.getString(key);
    }

    public static String getConfigAsString(String key, String def) {
        return config.getString(key, def);
    }

    private static String getStringFromFile(InputStream is) throws Exception {
        StringBuilder rs = new StringBuilder();
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        while ((line = reader.readLine()) != null) {
            rs.append(line);
        }
        return rs.toString();
    }

    private static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = Logger.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    @Override
    protected void exec(BaseRequest input, Handler<BaseRequest> nextTask) {
        try {
            String env = System.getenv("XPC_SERVICE_NAME");
            InputStream is;
            if (env != null && env.contains("intellij")) {
                is = getFileFromResourceAsStream("conf/dev.conf");
            } else {
                is = getFileFromResourceAsStream("conf/prod.conf");
            }
            config = new JsonObject(getStringFromFile(is));
            nextTask.handle(input);
        } catch (Exception e) {
            log.error("Load config fail", e);
            input.fail(1);
            nextTask.handle(input);
        }
    }
}
