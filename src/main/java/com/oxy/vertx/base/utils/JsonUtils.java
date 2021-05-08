package com.oxy.vertx.base.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class JsonUtils {
    private static final ObjectMapper mapper;
    private static final ObjectMapper mapperThin;
    private static final ObjectMapper oracleMapper;
    private static final Map<String, String> fieldMapper;

    static {
        fieldMapper = new HashMap<>();
        fieldMapper.put("user", "USERNAME");
        fieldMapper.put("min", "MINVALUE");
        fieldMapper.put("max", "MAXVALUE");
        fieldMapper.put("desc", "DESCRIPTION");
        fieldMapper.put("number", "NUMBERVALUE");
        fieldMapper.put("default", "DEFAULTVALUE");
        fieldMapper.put("time", "TIMEVALUE");
        fieldMapper.put("like", "LIKEVALUE");
        fieldMapper.put("comment", "COMMENTVALUE");
        fieldMapper.put("share", "SHAREVALUE");

        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);

        mapperThin = new ObjectMapper(mapper.getFactory());
        mapperThin.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapperThin.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        oracleMapper = new ObjectMapper();
        oracleMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        oracleMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        oracleMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        oracleMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        oracleMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        oracleMapper.setPropertyNamingStrategy(new UpperCaseStrategy());
    }


    private JsonUtils() {
    }

    public static <T> T jsonToObj(String object, Class<T> type) {
        try {
            return mapper.readValue(object, type);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T jsonToObj(JsonObject object, Class<T> type) {
        try {
            return mapper.readValue(object.encode(), type);
        } catch (Exception e) {
            return null;
        }
    }

    public static String objToString(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e) {

            return null;
        }
    }

    public static JsonObject objToJsonObj(Object o) {
        try {
            return new JsonObject(mapper.writeValueAsString(o));
        } catch (Exception e) {

            return null;
        }
    }

    public static JsonObject objToThinJsonObj(Object o) {
        try {
            return new JsonObject(mapperThin.writeValueAsString(o));
        } catch (Exception e) {

            return null;
        }
    }

    public static Map<String, Object> objToMap(Object obj) {
        JsonObject jo = objToJsonObj(obj);
        return jo != null ? jo.getMap() : null;
    }

    public static <T> T mapToObj(Map<String, String> map, Class<T> type) {
        return mapper.convertValue(map, type);
    }

    public static boolean isJSON(String source) {
        try {
            new JsonObject(source);
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public static boolean isJSONArray(String source) {
        try {
            new JsonArray(source);
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public static String objToThinJsonString(Object data) {
        JsonObject jo = objToThinJsonObj(data);
        if (jo != null) {
            return jo.encode();
        }
        return null;
    }

    public static JsonObject objToJsonObjUpper(Object o) {
        try {
            return new JsonObject(oracleMapper.writeValueAsString(o));
        } catch (Exception e) {

            return null;
        }
    }

    public static Map<String, Object> objToMapUpper(Object obj) {
        JsonObject jo = objToJsonObjUpper(obj);
        return jo != null ? jo.getMap() : null;
    }

    static class UpperCaseStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {
        @Override
        public String translate(String propertyName) {
            if (fieldMapper.containsKey(propertyName.toLowerCase())) {
                return fieldMapper.get(propertyName.toLowerCase());
            }
            return propertyName.toUpperCase();
        }
    }

}
