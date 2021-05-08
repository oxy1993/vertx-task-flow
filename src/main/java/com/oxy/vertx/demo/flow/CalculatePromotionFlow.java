package com.oxy.vertx.demo.flow;

import com.oxy.vertx.demo.task.calculate.BuildPromotionRespTask;
import com.oxy.vertx.demo.task.calculate.CalculatePromotionTask;
import com.oxy.vertx.demo.task.calculate.ValidatePromotionReqTask;
import com.oxy.vertx.base.OxyFlow;
import com.oxy.vertx.demo.msg.CalculatePromotionMsg;

import javax.inject.Singleton;

@Singleton
public class CalculatePromotionFlow extends OxyFlow<CalculatePromotionMsg> {
    public CalculatePromotionFlow() {
        addTask(new ValidatePromotionReqTask());
        addTask(new CalculatePromotionTask());
        addTask(new BuildPromotionRespTask());
    }
}
