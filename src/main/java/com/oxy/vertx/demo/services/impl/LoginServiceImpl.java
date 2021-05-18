package com.oxy.vertx.demo.services.impl;

import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.demo.flow.LoginFlow;
import com.oxy.vertx.demo.msg.LoginMsg;
import com.oxy.vertx.demo.services.LoginService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public class LoginServiceImpl implements LoginService {
    @Override
    public void login(JsonObject input, Handler<AsyncResult<String>> resultHandler) {
        new LoginFlow().run(new LoginMsg("oxy", "123"),
                done -> resultHandler.handle(Future.succeededFuture(JsonUtils.objToString(done.getResponse()))));
    }
}
