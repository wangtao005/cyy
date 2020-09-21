package org.mics.token.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;


public class DateTimeUtil {

    /**
     * 获取第二天的凌晨三点 - 主要用于JWT过期时间
     *
     * @return 日期
     */
    public static Date getThreeOclockAMOfTheNextDay() {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        LocalDateTime nextDay = currentLocalDateTime.plusDays(1);
        LocalDateTime threeOclockAMOfTheNextDay = LocalDateTime.of(nextDay.getYear(), nextDay.getMonth(), nextDay.getDayOfMonth(), 3, 0, 0);
        return convertLocalDateTimeToDate(threeOclockAMOfTheNextDay);
    }

    /**
     * 获取第七天的凌晨三点 - 主要用于JWT过期时间
     *
     * @return 日期
     */
    public static Date getThreeOclockAMOfSeventhDay() {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        LocalDateTime nextDay = currentLocalDateTime.plusDays(7);
        LocalDateTime threeOclockAMOfSeventhDay = LocalDateTime.of(nextDay.getYear(), nextDay.getMonth(), nextDay.getDayOfMonth(), 3, 0, 0);
        return convertLocalDateTimeToDate(threeOclockAMOfSeventhDay);
    }

    /**
     * 转换java8的LocalDateTime为Date对象
     *
     * @param localDateTime java8
     * @return Date
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }
}
