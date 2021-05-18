package com.oxy.vertx.demo.task.login;

import com.oxy.vertx.base.WarriorTask;
import com.oxy.vertx.base.utils.JWTUtils;
import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.demo.msg.LoginMsg;
import com.oxy.vertx.demo.msg.LoginResMsg;
import io.vertx.core.Handler;
import io.vertx.ext.auth.jwt.JWTAuth;

public class ExecLoginTask extends WarriorTask<LoginMsg> {
    @Override
    protected void exec(LoginMsg input, Handler<LoginMsg> nextTask) {
        LoginResMsg resMsg = input.createResponse(LoginResMsg.class);
        JWTAuth provider = JWTUtils.getInstance().getProvider();
        resMsg.setToken(provider.generateToken(JsonUtils.objToJsonObj(input)));
        nextTask.handle(input);
    }
}
