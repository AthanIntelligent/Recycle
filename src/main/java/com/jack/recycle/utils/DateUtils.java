package com.jack.recycle.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    /**
     * 获取指定格式pattern的日期
     * @return
     */
    public static String getFormatDate(String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String now = simpleDateFormat.format(new Date());
        return now;
    }
}
