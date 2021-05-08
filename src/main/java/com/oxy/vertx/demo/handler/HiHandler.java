package com.oxy.vertx.demo.handler;

import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.demo.flow.HiFlow;
import com.oxy.vertx.demo.msg.ExecHiMsg;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.RoutingContext;

public class HiHandler extends AbstractHandler {
    private final HiFlow hiFlow;

    public HiHandler() {
        this.hiFlow = new HiFlow();
    }

    @Override
    public void handle(RoutingContext routingContext) {
        ExecHiMsg execHiMsg = new ExecHiMsg();

        MultiMap params = routingContext.request().params();
        if (params != null && !params.isEmpty()) {
            if (!params.get("name").equals("")) {
                execHiMsg.setName(params.get("name"));
            }
        }

        hiFlow.run(execHiMsg, done -> {
            String response = JsonUtils.objToString(done.getResponse());
            log.info("Hi done with response: {}", response);
            log.info("======================================================");
            sendResponse(routingContext.response(), response, 200);
        });
    }
}
