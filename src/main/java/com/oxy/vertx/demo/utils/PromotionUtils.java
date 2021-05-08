package com.oxy.vertx.demo.utils;

import com.oxy.vertx.demo.msg.CalculatePromotionMsg;

import java.util.Calendar;
import java.util.Date;

public class PromotionUtils {

    public static String buildRedisKey(CalculatePromotionMsg input) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int month = cal.get(Calendar.MONTH);
        int year = cal.getWeekYear();
        String thisMonth = String.valueOf(month) + year;
        return String.format("%s_%s", input.getUser(), thisMonth);
    }
}
