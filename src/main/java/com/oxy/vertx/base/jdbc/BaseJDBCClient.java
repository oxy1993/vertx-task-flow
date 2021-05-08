package com.oxy.vertx.base.jdbc;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;

import java.util.List;

public interface BaseJDBCClient extends io.vertx.ext.jdbc.JDBCClient {
    void doCallWithParams(final String sp, final JsonArray param1, final JsonArray param2, final Handler<AsyncResult<ResultSet>> callback);

    <T> void doQuery(final String query, final Class<T> type, final Handler<List<T>> callback);

    <T> void doQueryWithParams(final String query, JsonArray params, final Class<T> type, final Handler<List<T>> callback);

    void batchUpdateWithParams(String query, List<JsonArray> listParams, Handler<Boolean> callback);

    void batchWithParams(String query, List<JsonArray> args, Handler<Boolean> callback);
}
