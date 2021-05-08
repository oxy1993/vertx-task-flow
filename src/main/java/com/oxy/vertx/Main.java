package com.oxy.vertx;

import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.flow.PromotionStartUpFlow;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        new PromotionStartUpFlow().run(new StartUpMsg(), done -> {
            if (!done.isBreakWorkFlow()) {
                log.info("Promotion service start success");
            } else {
                log.error("Promotion service start fail");
                System.exit(1);
            }
        });
    }
}
