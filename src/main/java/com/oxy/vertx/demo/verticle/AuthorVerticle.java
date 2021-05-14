package com.oxy.vertx.demo.verticle;

import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.constant.Action;
import com.oxy.vertx.demo.enums.ErrorCode;
import com.oxy.vertx.demo.flow.GetAllAuthorsFlow;
import com.oxy.vertx.demo.msg.ExecGetAllAuthorsMsg;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import com.oxy.vertx.demo.constant.QueueName;

public class AuthorVerticle extends AbstractVerticle {
    private static final Logger log = Logger.getLogger(AuthorVerticle.class);
    @Override
    public void start(){
        vertx.eventBus().<JsonObject>consumer(QueueName.AUTHOR_QUEUE_NAME, context -> {
            if (!context.headers().contains("action")) {
                log.error("No action header specified for message with headers {} and body {}",
                        context.headers(), context.body().encodePrettily());
                context.fail(ErrorCode.NO_ACTION_SPECIFIED.ordinal(), "No action header specified");
                return;
            }

            String action = context.headers().get("action");

            switch (action) {
                case Action.GET_AUTHORS:
                    long startTime = context.body().getLong("time");
                    new GetAllAuthorsFlow().run(new ExecGetAllAuthorsMsg(),
                            done -> context.reply(JsonUtils.objToString(done.getResponse())));
                    log.info("total latency =====================> {}", System.currentTimeMillis() - startTime);
                    break;
                default:
                    context.fail(ErrorCode.BAD_ACTION.ordinal(), "Bad action: " + action);
            }
        });
    }
}
