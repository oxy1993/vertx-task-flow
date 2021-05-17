package com.oxy.vertx.demo.services.impl;

import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.flow.GetAllAuthorsFlow;
import com.oxy.vertx.demo.flow.GetAuthorsFromDBFlow;
import com.oxy.vertx.demo.msg.ExecGetAllAuthorsMsg;
import com.oxy.vertx.demo.services.AuthorService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public class AuthorServiceImpl implements AuthorService {
    private static final Logger log = Logger.getLogger(AuthorService.class);
    @Override
    public void fetchAllAuthors(JsonObject input, Handler<AsyncResult<String>> resultHandler) {
        long time = input.getLong("time");
        new GetAllAuthorsFlow().run(new ExecGetAllAuthorsMsg(),
            done -> {
                resultHandler.handle(Future.succeededFuture(JsonUtils.objToString(done.getResponse())));
                log.info("total latency =====================> {}", System.currentTimeMillis() - time);
            });
    }

    @Override
    public void fetchAuthorsFromDB(JsonObject input, Handler<AsyncResult<String>> resultHandler) {
        long time = input.getLong("time");
        new GetAuthorsFromDBFlow().run(new ExecGetAllAuthorsMsg(),
            done -> {
                resultHandler.handle(Future.succeededFuture(JsonUtils.objToString(done.getResponse())));
                log.info("total latency =====================> {}", System.currentTimeMillis() - time);
            });
    }
}
