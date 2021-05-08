package com.oxy.vertx;

import com.oxy.vertx.base.entities.StartUpMsg;
import com.oxy.vertx.base.utils.Logger;
import com.oxy.vertx.demo.flow.DemoStartUpFlow;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        new DemoStartUpFlow().run(new StartUpMsg(), done -> {
            if (!done.isBreakWorkFlow()) {
                log.info("Service start success");
            } else {
                log.error("Service start fail");
                System.exit(1);
            }
        });
    }
}
