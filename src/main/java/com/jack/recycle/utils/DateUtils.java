package com.jack.recycle.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    /**
     * 获取指定格式pattern的日期
     * @return
     */
    public static String getFormatDate(String pattern){
        return LocalDateTime.now().toLocalDate().format(DateTimeFormatter.ofPattern(pattern));
    }
}
