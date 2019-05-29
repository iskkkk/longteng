package com.alon.common.utils;

import com.alon.common.constant.DateFormatterConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ClassName DateFormUtils
 * @Description 时间转换
 * @Author 一股清风
 * @Date 2019/5/23 10:29
 * @Version 1.0
 **/
public class DateFormUtils {
    /**
      * 方法表述: 将时间戳转换为时间date
      * @Author 一股清风
      * @Date 10:31 2019/5/23
      * @param       time
      * @return java.lang.String
    */
    public static Date stampToDate(Long time,String formatter){
        Date date = new Date(time * 1000L);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return stringToDate(sd.format(date),formatter);
    }

    /**
      * 方法表述: string-->date
      * @Author 一股清风
      * @Date 10:48 2019/5/23
      * @param       time
     * @param       formatter
      * @return java.util.Date
    */
    public static Date stringToDate(String time,String formatter){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatter);
        LocalDateTime parse = LocalDateTime.parse(time, dtf);
        return LocalDateTimeToUdate(parse);
    }

    /**
      * 方法表述:  java.time.LocalDateTime --> java.util.Date
      * @Author 一股清风
      * @Date 10:48 2019/5/23
      * @param       localDateTime
      * @return java.util.Date
    */
    public static Date LocalDateTimeToUdate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
      * 方法表述: 获取当前时间戳
      * @Author 一股清风
      * @Date 10:28 2019/5/29
      * @param       
      * @return java.lang.String
    */
    public static String getNowTime() {
        LocalDate localDate = LocalDate.now();
        Timestamp timestamp= Timestamp.valueOf(LocalDateTime.now());
        return String.valueOf(timestamp.getTime()/1000);
    }

    public static void main(String[] args) {
        System.out.println(stringToDate("2018-02-01 14:39:19", DateFormatterConstant.YEAR_MONTH_DAY_HH_MM_SS));
    }
}
