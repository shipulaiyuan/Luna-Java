package com.virtual.luna.framework.quartz.core.util;

import cn.hutool.core.date.LocalDateTimeUtil;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Quartz Cron 表达式的工具类
 */
public class CronUtils {

    /**
     * 校验 CRON 表达式是否有效
     *
     * @param cronExpression CRON 表达式
     * @return 是否有效
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * 基于 CRON 表达式，获得下 n 个满足执行的时间
     *
     * @param cronExpression CRON 表达式
     * @param n 数量
     * @return 满足条件的执行时间
     */
    public static List<LocalDateTime> getNextTimes(String cronExpression, int n) {
        // 获得 CronExpression 对象
        CronExpression cron;
        try {
            cron = new CronExpression(cronExpression);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        // 从当前开始计算，n 个满足条件的
        Date now = new Date();
        List<LocalDateTime> nextTimes = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            Date nextTime = cron.getNextValidTimeAfter(now);
            nextTimes.add(LocalDateTimeUtil.of(nextTime));
            // 切换现在，为下一个触发时间；
            now = nextTime;
        }
        return nextTimes;
    }

    /**
     * 将时间间隔（以毫秒为单位）转换为Cron表达式。
     *
     * @param intervalMillis 时间间隔（以毫秒为单位）
     * @return 对应的Cron表达式
     * @throws IllegalArgumentException 如果间隔小于一秒或不是受支持的时间单位的倍数
     */
    public static String generateCronExpression(long intervalMillis) {
        // 如果间隔小于1000毫秒（1秒），抛出异常
        if (intervalMillis < 1000) {
            throw new IllegalArgumentException("间隔必须至少为一秒。");
        }

        // 将间隔转换为秒
        long intervalSeconds = TimeUnit.MILLISECONDS.toSeconds(intervalMillis);

        // 检查间隔是否是支持的时间单位（秒、分钟、小时）的倍数
        if (intervalSeconds % 60 == 0) {
            long minutes = intervalSeconds / 60;
            if (minutes % 60 == 0) {
                long hours = minutes / 60;
                if (hours % 24 == 0) {
                    long days = hours / 24;
                    // 每隔几天执行一次
                    return String.format("0 0 0 1/%d * ?", days);
                }
                // 每隔几小时执行一次
                return String.format("0 0 0/%d * * ?", hours);
            }
            // 每隔几分钟执行一次
            return String.format("0 0/%d * * * ?", minutes);
        } else {
            // 每隔几秒执行一次
            return String.format("0/%d * * * * ?", intervalSeconds);
        }
    }

}
