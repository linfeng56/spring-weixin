package com.github.linfeng.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间工具类.
 *
 * @author 黄麟峰
 * @date 2021-03-31
 */
public class DateTimeUtils {

    /**
     * 日期转长时间戳
     *
     * @param date 日期(yyyy-mm-dd)
     * @return 长时间戳(东8区)
     */
    public static Long DateToLong(String date) {
        Long ret = 0L;
        LocalDate beginDateIso = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        LocalDateTime beginDateTime = LocalDateTime.of(beginDateIso, LocalTime.of(0, 0));
        ret = beginDateTime.toEpochSecond(ZoneOffset.of("+8")) * 1000;
        return ret;
    }

    /**
     * 当前时间的长时间戳
     *
     * @return 长时间戳(东8区)
     */
    public static Long DateTimeToLong() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")) * 1000;
    }
}
