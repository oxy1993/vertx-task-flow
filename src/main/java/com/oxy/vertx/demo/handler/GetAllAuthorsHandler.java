package com.oxy.vertx.demo.handler;

import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.demo.flow.GetAllAuthorsFlow;
import com.oxy.vertx.demo.msg.ExecGetAllAuthorsMsg;
import io.vertx.ext.web.RoutingContext;

public class GetAllAuthorsHandler extends AbstractHandler{
    private final GetAllAuthorsFlow getAllAuthorsFlow;

    public GetAllAuthorsHandler() {
        this.getAllAuthorsFlow = new GetAllAuthorsFlow();
    }

    @Override
    public void handle(RoutingContext routingContext) {
        getAllAuthorsFlow.run(new ExecGetAllAuthorsMsg(), done -> {
            String response = JsonUtils.objToString(done.getResponse());
            log.info("Get all authors done with response: {}", response);
            log.info("======================================================");
            sendResponse(routingContext.response(), response, 200);
        });
    }
}
