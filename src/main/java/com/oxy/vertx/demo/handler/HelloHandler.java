package com.oxy.vertx.demo.handler;

import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.demo.flow.HelloFlow;
import com.oxy.vertx.demo.msg.ExecHelloMsg;
import io.vertx.ext.web.RoutingContext;

public class HelloHandler extends AbstractHandler {
    private final HelloFlow helloFlow;

    public HelloHandler() {
        this.helloFlow = new HelloFlow();
    }

    @Override
    public void handle(RoutingContext routingContext) {
        ExecHelloMsg execHelloMsg;
        execHelloMsg = validateHttpRequest(routingContext, ExecHelloMsg.class);
        if (execHelloMsg == null) {
            return;
        }

        helloFlow.run(execHelloMsg, done -> {
            String response = JsonUtils.objToString(done.getResponse());
            log.info("Hello world done with response: {}", response);
            log.info("======================================================");
            sendResponse(routingContext.response(), response, 200);
        });
    }
}
