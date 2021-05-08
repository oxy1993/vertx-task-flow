package com.oxy.vertx.demo.handler;

import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.demo.flow.HelloWorldFlow;
import com.oxy.vertx.demo.msg.ExecHelloWorldMsg;
import io.vertx.ext.web.RoutingContext;

public class HelloWorldHandler extends AbstractHandler {
    private final HelloWorldFlow helloWorldFlow;

    public HelloWorldHandler() {
        this.helloWorldFlow = new HelloWorldFlow();
    }


    @Override
    public void handle(RoutingContext routingContext) {
        ExecHelloWorldMsg execHelloWorldMsg = validateHttpRequest(routingContext, ExecHelloWorldMsg.class);
        if (execHelloWorldMsg == null) {
            return;
        }
        helloWorldFlow.run(execHelloWorldMsg, done -> {
            String response = JsonUtils.objToString(done.getResponse());
            log.info("Calculate promotion done with response: {}", response);
            log.info("======================================================");
            sendResponse(routingContext.response(), response, 200);
        });
    }
}
