package com.oxy.vertx.demo.handler;

import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.demo.flow.RollbackPromoCounterFlow;
import com.oxy.vertx.demo.msg.RollbackPromoCounterMsg;
import io.vertx.ext.web.RoutingContext;

public class RollbackPromoCounterHandler extends AbstractHandler {
    private final RollbackPromoCounterFlow rollbackPromoCounterFlow;

    public RollbackPromoCounterHandler() {
        this.rollbackPromoCounterFlow = new RollbackPromoCounterFlow();
    }

    @Override
    public void handle(RoutingContext routingContext) {
        RollbackPromoCounterMsg rollbackPromoCounterMsg = validateHttpRequest(routingContext, RollbackPromoCounterMsg.class);
        if (rollbackPromoCounterMsg == null) {
            return;
        }
        rollbackPromoCounterFlow.run(rollbackPromoCounterMsg, done -> {
            String response = JsonUtils.objToString(done.getResponse());
            log.info("Rollback promotion counter done with response: {}", response);
            log.info("======================================================");
            sendResponse(routingContext.response(), response, 200);
        });
    }
}
