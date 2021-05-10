package com.oxy.vertx.base.jdbc;

import com.oxy.vertx.base.conf.CommonConfig;
import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.base.utils.Logger;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.impl.JDBCClientImpl;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BaseJDBCClientImpl extends JDBCClientImpl implements BaseJDBCClient {

    private static BaseJDBCClientImpl jdbcClient;
    private final Logger log = Logger.getLogger(BaseJDBCClientImpl.class);


    public BaseJDBCClientImpl(Vertx vertx, DataSource dataSource) {
        super(vertx, dataSource);
    }

    public BaseJDBCClientImpl(Vertx vertx, JsonObject config, String datasourceName) {
        super(vertx, config, datasourceName);
    }


    public static BaseJDBCClientImpl getClient() {
        if (jdbcClient == null) {
            JsonObject config = CommonConfig.getConfigAsJson("db");
            jdbcClient = new BaseJDBCClientImpl(CommonConfig.getVertx(), config, "be.u.ora");
        }
        return jdbcClient;
    }

    /**
     * Do a query with generic types
     *
     * @param query    query
     * @param type     generic types
     * @param callback callback handler
     * @param <T>      generic class
     */
    public <T> void doQuery(final String query, final Class<T> type, final Handler<List<T>> callback) {
        doQueryWithParams(query, null, type, callback);
    }

    /**
     * Do a query with parameters and generic types
     *
     * @param query    query
     * @param params   params
     * @param type     generic types
     * @param callback callback handler
     * @param <T>      generic class
     */
    public <T> void doQueryWithParams(final String query, JsonArray params, final Class<T> type, final Handler<List<T>> callback) {
        queryWithParams(query, params, asyncResult -> {
            if (!asyncResult.succeeded()) {
                log.error("Query failed: ", asyncResult.cause());
                callback.handle(null);
                return;
            }

            ResultSet rs = asyncResult.result();
            final List<T> result = new ArrayList<>();
            if (rs == null) {
                log.warn("Execute query " + query + " fail");
                callback.handle(result);
                return;
            }

            final AtomicInteger count = new AtomicInteger(rs.getNumRows());
            if (rs.getNumRows() > 0) {
                rs.getRows().forEach(entries -> {
                    result.add(JsonUtils.jsonToObj(entries, type));

                    if (count.decrementAndGet() <= 0) {
                        callback.handle(result);
                    }
                });
            } else {
                log.warn("Query result is empty");
                callback.handle(result);
            }
        });
    }

    public void doCallWithParams(final String sp, final JsonArray param1, final JsonArray param2, final Handler<AsyncResult<ResultSet>> callback) {
        getConnection(connectionRes -> {
            if (connectionRes.failed()) {
                log.error("---- can not get connection", connectionRes.cause());
                callback.handle(null);
            } else {
                SQLConnection connection = connectionRes.result();
                if (connection == null) {
                    log.error("---- can not get connection");
                    callback.handle(null);
                } else {
                    connection.callWithParams(sp, param1, param2, resultHandler -> {
                        if (resultHandler.failed()) {
                            log.error("Execute store " + sp + " fail", resultHandler.cause());
                        }
                        connection.close();
                        callback.handle(resultHandler);
                    });
                }
            }
        });
    }

    /**
     * Insert/update/delete
     */
    public void batchUpdateWithParams(String query, List<JsonArray> listParams, Handler<Boolean> callback) {
        getConnection(resultHandler -> {
            if (resultHandler.failed()) {
                log.error("---- can not get connection", resultHandler.cause());
                callback.handle(false);
            } else {
                SQLConnection connection = resultHandler.result();
                if (connection == null) {
                    log.error("---- can not get connection", resultHandler.cause());
                    callback.handle(false);
                } else {
                    executeBatchUpdateHelper(connection, query, listParams, 0, res -> {
                        connection.close(res1 -> {
                            // close connection success
                            log.debug("----batchUpdateWithParams close connection");

                            // return result
                            callback.handle(res);
                        });
                    });
                }
            }
        });
    }

    private void executeBatchUpdateHelper(SQLConnection connection, String query, List<JsonArray> listParams, int idx, Handler<Boolean> callback) {

        int processSize = 100;

        if (idx >= listParams.size()) {
            log.debug("----updated records: " + ((idx - 1) / processSize));
            callback.handle(true);
            return;
        }

        JsonArray param = listParams.get(idx);

        connection.updateWithParams(query, param, updateResultAsyncResult -> {

            if (updateResultAsyncResult.succeeded()) {

                int rowInsert = idx + 1;
                if (rowInsert % processSize == 0) {
                    log.debug("----executeBatchUpdateHelper updated records: " + (rowInsert / processSize));
                }

                // continue execute update
                executeBatchUpdateHelper(connection, query, listParams, idx + 1, callback);
            } else {

                log.error("----execute update database error", updateResultAsyncResult.cause());
                callback.handle(false);
            }
        });
    }

    public void batchWithParams(String query, List<JsonArray> args, Handler<Boolean> callback) {
        getConnection(connectionRes -> {
            if (connectionRes.failed()) {
                log.error("---- can not get connection", connectionRes.cause());
                callback.handle(null);
            } else {
                SQLConnection connection = connectionRes.result();
                if (connection == null) {
                    log.error("---- can not get connection");
                    callback.handle(null);
                } else {
                    connection.batchWithParams(query, args, resultHandler -> {
                        if (resultHandler.succeeded()) {
                            List<Integer> resultList = resultHandler.result();
                            log.debug("---- execute SQL affect num rows: " + resultList.size());
                            if (resultList.size() == args.size()) {
                                connection.close();
                                callback.handle(true);
                            } else {
                                connection.close();
                                callback.handle(false);
                            }
                        } else {
                            log.error("---- execute SQL error", resultHandler.cause());
                            connection.close();
                            callback.handle(false);
                        }
                    });
                }
            }
        });
    }
}