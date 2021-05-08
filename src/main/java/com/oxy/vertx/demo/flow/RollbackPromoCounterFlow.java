package com.oxy.vertx.demo.flow;

import com.oxy.vertx.demo.msg.RollbackPromoCounterMsg;
import com.oxy.vertx.demo.task.rollback.RollbackPromoCounterTask;
import com.oxy.vertx.demo.task.rollback.ValidateRollbackCounterRqTask;
import com.oxy.vertx.base.OxyFlow;

public class RollbackPromoCounterFlow extends OxyFlow<RollbackPromoCounterMsg> {
    public RollbackPromoCounterFlow() {
        addTask(new ValidateRollbackCounterRqTask());
        addTask(new RollbackPromoCounterTask());
    }
}
