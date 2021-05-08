package com.oxy.vertx.demo.task.calculate;

import com.oxy.vertx.base.conf.CommonDbConfig;
import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.msg.GetSetRedisValueMsg;
import com.oxy.vertx.base.redis.GetRedisHelperTask;
import com.oxy.vertx.demo.msg.CalculatePromotionMsg;
import com.oxy.vertx.demo.msg.PromotionResponseMsg;
import com.oxy.vertx.demo.utils.PromotionUtils;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculatePromotionTask extends OxyTask<CalculatePromotionMsg> {
    private final GetRedisHelperTask getRedisHelperTask;
    private final UpdateDiscountTimeTask updateDiscountTimeTask;
    private final SaveCacheForRollbackTask saveCacheForRollbackTask;
    private int maxDiscountTime;
    private JsonObject ruleConfig;
    private JsonArray paymentMethodAllowed;
    private Date startDate;
    private Date endDate;

    public CalculatePromotionTask() {
        this.getRedisHelperTask = new GetRedisHelperTask();
        this.updateDiscountTimeTask = new UpdateDiscountTimeTask();
        this.saveCacheForRollbackTask = new SaveCacheForRollbackTask();
    }

    @Override
    protected void exec(CalculatePromotionMsg input, Handler<CalculatePromotionMsg> nextTask) {
        assignConfig();
        PromotionResponseMsg responseMsg = input.createResponse(PromotionResponseMsg.class);
        if (isNotSatisfyPromotionConditions(input)) {
            log.warn("This payment is not satisfy the promotion condition conditions");
            responseMsg.setResult(0);
            responseMsg.setFinalAmount(input.getOriginalAmount());
            nextTask.handle(input);
            return;
        }
        log.info("Promotion time from {} to {}", startDate, endDate);
        GetSetRedisValueMsg getSetRedisValueMsg = new GetSetRedisValueMsg();
        getSetRedisValueMsg.setKey(PromotionUtils.buildRedisKey(input));
        getRedisHelperTask.run(getSetRedisValueMsg, done -> {
            if (done.getResponse().getResult() != 0) {
                input.fail(done.getResponse().getResult());
                nextTask.handle(input);
                return;
            }
            int discountTime = done.getValue() == null ? 0 : Integer.parseInt(done.getValue());
            responseMsg.setTotalDiscountTimeThisMouth(discountTime);
            input.putExtra("discountTime", discountTime);
            String promotionKey = String.valueOf(input.getOriginalAmount());
            if (discountTime < maxDiscountTime && ruleConfig.containsKey(promotionKey)) {
                responseMsg.setFinalAmount(ruleConfig.getLong(promotionKey, input.getOriginalAmount()));
                log.info("User [{}] NOT exceed number of discount this mouth", input.getUser());
                updateDiscountTimeTask.run(input, isDone -> saveCacheForRollbackTask.run(input, nextTask));
            } else {
                log.warn("Original amount = {} or User [{}] exceed number of discount in this mouth", promotionKey, input.getUser());
                responseMsg.setFinalAmount(input.getOriginalAmount());
                nextTask.handle(input);
            }
        });
    }

    private boolean isNotInPromotionTime() {
        long currentTime = System.currentTimeMillis();
        return startDate == null || endDate == null || startDate.getTime() >= currentTime || currentTime >= endDate.getTime();
    }

    private boolean isNotSatisfyPromotionConditions(CalculatePromotionMsg input) {
        return isNotInPromotionTime() || !paymentMethodAllowed.contains(input.getPaymentMethod());
    }

    private void assignConfig() {
        JsonObject promotionConfig = new JsonObject(CommonDbConfig.getConfig("promotion_config", new JsonObject().toString()));
        this.ruleConfig = promotionConfig.getJsonObject("rule", new JsonObject());
        this.maxDiscountTime = promotionConfig.getInteger("maxDiscountTime", 10);
        this.paymentMethodAllowed = promotionConfig.getJsonArray("payment_method_allowed", new JsonArray());
        try {
            this.startDate = new SimpleDateFormat("yyyy-MM-dd").parse(promotionConfig.getString("start", "2050-01-01"));
            this.endDate = new SimpleDateFormat("yyyy-MM-dd").parse(promotionConfig.getString("end", "1971-01-01"));
        } catch (ParseException e) {
            log.error("Parse date time error", e);
        }
        log.info("Assign new promotion config from DB success");
    }
}
