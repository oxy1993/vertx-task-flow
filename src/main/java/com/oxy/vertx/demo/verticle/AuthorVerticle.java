package com.oxy.vertx.demo.verticle;

import com.oxy.vertx.base.utils.JsonUtils;
import com.oxy.vertx.demo.flow.GetAllAuthorsFlow;
import com.oxy.vertx.demo.msg.ExecGetAllAuthorsMsg;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class AuthorVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> promise){ ;
        vertx.eventBus().<JsonObject>consumer("authors", context -> new GetAllAuthorsFlow().run(new ExecGetAllAuthorsMsg(),
                done -> context.reply(JsonUtils.objToString(done.getResponse()))));
        promise.complete();
    }
}
