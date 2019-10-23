package com.alon.common.utils;

import com.alon.common.constant.DateFormatterConstant;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

    /**
      * 方法表述: 得到十位数的时间戳
      * @Author zoujiulong
      * @Date 18:13 2019/6/11
      * @param       dateStr
      * @return long
    */
    public static long getTenTimeByDate(String dateStr) {
        return convertAsDate(dateStr).getTime() / 1000;
    }

    /**
      * 方法表述: 把日期字符串转换为日期类型
      * @Author zoujiulong
      * @Date 18:13 2019/6/11
      * @param       dateStr
      * @return java.util.Date
    */
    public static Date convertAsDate(String dateStr) {
        if (dateStr == null || "".equals(dateStr)) {
            return null;
        }
        DateFormat fmt = null;
        if (dateStr.matches("\\d{14}")) {
            fmt = new SimpleDateFormat(DateFormatterConstant.TIME_FORMAT_SHORT);
        } else if (dateStr
                .matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
            fmt = new SimpleDateFormat(DateFormatterConstant.TIME_FORMAT_NORMAL);
        } else if (dateStr
                .matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
            fmt = new SimpleDateFormat(DateFormatterConstant.TIME_FORMAT_ENGLISH);
        } else if (dateStr
                .matches("\\d{4}年\\d{1,2}月\\d{1,2}日 \\d{1,2}时\\d{1,2}分\\d{1,2}秒")) {
            fmt = new SimpleDateFormat(DateFormatterConstant.TIME_FORMAT_CHINA);
        } else if (dateStr.matches("\\d{8}")) {
            fmt = new SimpleDateFormat(DateFormatterConstant.DATE_FORMAT_SHORT);
        } else if (dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
            fmt = new SimpleDateFormat(DateFormatterConstant.DATE_FORMAT_NORMAL);
        } else if (dateStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            fmt = new SimpleDateFormat(DateFormatterConstant.DATE_FORMAT_ENGLISH);
        } else if (dateStr.matches("\\d{4}年\\d{1,2}月\\d{1,2}日")) {
            fmt = new SimpleDateFormat(DateFormatterConstant.DATE_FORMAT_CHINA);
        } else if (dateStr.matches("\\d{4}\\d{1,2}\\d{1,2}\\d{1,2}\\d{1,2}")) {
            fmt = new SimpleDateFormat(DateFormatterConstant.DATE_FORMAT_MINUTE);
        } else if (dateStr.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
            fmt = new SimpleDateFormat(DateFormatterConstant.TIME_FORMAT_SHORT_S);
        }
        try {
            return fmt.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                    "Date or Time String is invalid.");
        }
    }

    /**
      * 方法表述: 比较两个字符串格式的时间大小<br/>
     * 如果第二个时间大于第一个时间返回true,否则返回false
      * @Author zoujiulong
      * @Date 18:30 2019/6/11
      * @param       strFirst 第一个时间
     * @param       strSecond 第二个时间
     * @param       strFormat 时间格式化方式 eg:"yyyy-MM-dd HH:mm:ss"," yyyy-MM-dd"
      * @return boolean true-第二个时间晚于第一个时间,false-第二个时间不晚于第一个时间
    */
    public static boolean latterThan(String strFirst, String strSecond,
                                     String strFormat) {
        SimpleDateFormat ft = new SimpleDateFormat(strFormat);
        try {
            Date date1 = ft.parse(strFirst);
            Date date2 = ft.parse(strSecond);
            long quot = date2.getTime() - date1.getTime();
            if (0 < quot) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
      * 方法表述: 获取过去第几天的日期
      * @Author zoujiulong
      * @Date 18:36 2019/10/23 
      * @param       past
      * @return java.lang.String
    */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
      * 方法表述: 获取未来 第 past 天的日期
      * @Author zoujiulong
      * @Date 18:36 2019/10/23
      * @param       past
      * @return java.lang.String
    */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
      * 方法表述: 获取过去或者未来 任意天内的日期数组
      * @Author zoujiulong
      * @Date 18:36 2019/10/23
      * @param       intervals
      * @return java.util.ArrayList<java.lang.String>
    */
    public static ArrayList<String> test(int intervals ) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i <intervals; i++) {
            pastDaysList.add(getPastDate(i));
            fetureDaysList.add(getFetureDate(i));
        }
        Collections.sort(pastDaysList);
        return pastDaysList;
    }

    public static void main(String[] args) {
        System.out.println(stringToDate("2018-02-01 14:39:19", DateFormatterConstant.TIME_FORMAT_NORMAL));
        System.out.println(test(7));
    }
}
