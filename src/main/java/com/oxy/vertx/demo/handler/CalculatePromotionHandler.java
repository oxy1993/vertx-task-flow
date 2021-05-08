package com.oxy.vertx.demo.handler;

import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.demo.flow.CalculatePromotionFlow;
import com.oxy.vertx.demo.msg.CalculatePromotionMsg;
import io.vertx.ext.web.RoutingContext;

public class CalculatePromotionHandler extends AbstractHandler {
    private final CalculatePromotionFlow calculatePromotionFlow;

    public CalculatePromotionHandler() {
        this.calculatePromotionFlow = new CalculatePromotionFlow();
    }


    @Override
    public void handle(RoutingContext routingContext) {
        CalculatePromotionMsg calculatePromotionMsg = validateHttpRequest(routingContext, CalculatePromotionMsg.class);
        if (calculatePromotionMsg == null) {
            return;
        }
        calculatePromotionFlow.run(calculatePromotionMsg, done -> {
            String response = JsonUtils.objToString(done.getResponse());
            log.info("Calculate promotion done with response: {}", response);
            log.info("======================================================");
            sendResponse(routingContext.response(), response, 200);
        });
    }
}
