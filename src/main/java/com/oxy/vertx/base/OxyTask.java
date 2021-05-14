package com.oxy.vertx.base;

import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.base.entities.IRequest;
import io.vertx.core.Handler;

public abstract class OxyTask<T extends IRequest> {

    protected final Logger log = Logger.getLogger(this.getClass());

    public void run(T input, Handler<T> nextTask) {
//        log.info("{} is running...", this.getClass().getSimpleName());
        exec(input, output -> {
            if (nextTask != null) {
                nextTask.handle(output);
            }
        });
    }

    public void run(T input) {
        run(input, null);
    }

    protected abstract void exec(T input, Handler<T> nextTask);
}