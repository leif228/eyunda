/**
 * 平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */
package com.hangyi.eyunda.pay.pinanpay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期常用方法类
 * @author kongqi
 * @version $Id: DateUtil.java, v 0.1 2013-8-1 下午8:14:15 kongqi Exp $
 */
public class DateUtils {
    /**
     * 日期格式:数据库日期格式(yyyyMMdd)
     */
    public static SimpleDateFormat FORMAT_DATE_DB          = new SimpleDateFormat("yyyyMMdd");

    /**
     * 日期格式：时间格式(HH:mm:ss)
     */
    public static SimpleDateFormat FORMAT_TIME_PAGE        = new SimpleDateFormat("HH:mm:ss");

    /**
     * 日期格式:页面日期格式(yyyy-MM-dd)
     */
    public static SimpleDateFormat FORMAT_DATE_PAGE        = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 日期格式:本地日期明码格式(yyyy年MM月dd HH:mm:ss)
     */
    public static SimpleDateFormat FORMAT_LOCAL            = new SimpleDateFormat(
                                                               "yyyy年MM月dd HH:mm:ss");

    /**
     * 日期格式:本地日期明码格式(yyyy-MM-dd HH:mm:ss)
     */
    public static SimpleDateFormat FORMAT_FULL_DATETIME    = new SimpleDateFormat(
                                                               "yyyy-MM-dd HH:mm:ss");
    
    /**
     * 日期格式:本地日期明码格式(yyyyMMddHHmmss)
     */
    public static SimpleDateFormat FORMAT_MERCHANT_HTTP    = new SimpleDateFormat(
                                                               "yyyyMMddHHmmss");

    /**
     * String(yyyy-MM-dd) -> Date
     * 
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parsePageDate(String strDate) {
        if (strDate == null) {
            return null;
        }

        try {
            return FORMAT_DATE_PAGE.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException("将字符串" + strDate + "解析为" + FORMAT_DATE_DB.toPattern()
                                       + "格式的日期时发生异常:", e);
        }
    }

    /**
     * String(yyyy-MM-dd HH:mm:ss) -> Date
     * 
     * @param dateTime
     *            时间字符串(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static Date parseFullDateTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }

        try {
            return FORMAT_FULL_DATETIME.parse(dateTime);
        } catch (ParseException e) {
            throw new RuntimeException("将字符串" + dateTime + "解析为" + FORMAT_FULL_DATETIME.toPattern()
                                       + "格式的日期时发生异常:", e);
        }
    }

    /**
     * Date -> String(yyyy-MM-dd HH:mm:ss)
     * 
     * @param date
     * @return
     */
    public static String formatFullDate(Date date) {
        if (date == null) {
            return "";
        }

        return FORMAT_FULL_DATETIME.format(date);
    }

    /**
     * 把日期，时间转化为格式：yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     *            日期，格式：yyyyMMdd
     * @param time
     *            时间，格式：HHmmss
     * @return
     */
    public static String getDateTime() {
        return FORMAT_FULL_DATETIME.format(new Date());
    }

    /**
     * 取得当前日期字符串; 日期格式:yyyyMMdd
     * 
     * @return
     */
    public static String getCurrentDate() {
        return FORMAT_DATE_DB.format(new Date());
    }

    /**
     * 取得当前日期字符串; 日期格式:yyyy-MM-dd
     * 
     * @return
     */
    public static String getCurrentPageDate() {
        return FORMAT_DATE_PAGE.format(new Date());
    }



    /**
     * Date -->> yyyy年MM月dd HH:mm:ss
     * 
     * @param date
     */
    public static String formatLocalDate(Date date) {
        return FORMAT_LOCAL.format(date);
    }

    /**
     * HH:mm:ss ->> HHmmss
     * 
     * @param pageTime
     * @return
     */
    public static String pageTimeToDbTime(String pageTime) {
        return pageTime.replaceAll(":", "");
    }

    /**
     * 将日期转换为指定格式
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static String formateDate2Str(Date date, String pattern) {
        SimpleDateFormat s = new SimpleDateFormat(pattern);
        return s.format(date);
    }

    /**
     * 将日期中的2007-1-1转化为20070101格式
     * 
     * @param datestr
     * @return
     */
    public static String dateStringFormat(String datestr) {
        if (datestr == null || datestr.equals(""))
            return null;
        String[] str1 = datestr.split("-");
        if (str1.length == 3) {
            if (str1[1].length() == 1) {
                str1[1] = "0" + str1[1];
            }
            if (str1[2].length() == 1) {
                str1[2] = "0" + str1[2];
            }
        } else
            return datestr;
        datestr = str1[0] + str1[1] + str1[2];
        return datestr;
    }

    public static Date addDay(Date oriDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(oriDate);
        cal.add(Calendar.DAY_OF_MONTH, amount);
        return cal.getTime();
    }

    /**
     * 取得指定格式的当前时间
     * 
     * @param pattern
     * @return
     */
    public static String getTime(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 取得指定时间的偏移时间
     * 
     * @param transferTime
     *            原始时间（yyyy-MM-dd HH:ss:mm）
     * @param calendarType
     *            偏移单位（Calendar的常量）
     * @param i
     *            偏移量
     * @return
     */
    public static String getExcursionTime(String transferTime, int calendarType, int i) {
        Date parseFullDateTime = parseFullDateTime(transferTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseFullDateTime);
        calendar.add(calendarType, i);
        return FORMAT_FULL_DATETIME.format(calendar.getTime());
    }

    /**
     * 取得当前时间的偏移时间
     * 
     * @param transferTime
     *            原始时间（yyyy-MM-dd HH:ss:mm）
     * @param calendarType
     *            偏移单位（Calendar的常量）
     * @param i
     *            偏移量
     * @return
     */
    public static String getExcursionTime(int calendarType, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendarType, i);
        return FORMAT_FULL_DATETIME.format(calendar.getTime());
    }

    /**
     * 取得指定时间的偏移时间
     * 
     * @param calendarType
     *            偏移单位（Calendar的常量）
     * @param i
     *            偏移量
     * @param 日期格式
     * @return
     */
    public static String getExcursionTime(int calendarType, int i, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendarType, i);
        return new SimpleDateFormat(pattern).format(calendar.getTime());
    }

    /**
     * 取得当前时间的偏移时间 格式为（yyyyMMdd）+1为往后一天，-1为向前一天，0为当天
     */
    public static String getAboutDate(int i) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.DAY_OF_MONTH, i);
        String mDateTime = FORMAT_DATE_DB.format(rightNow.getTime());
        return mDateTime;
    }

    /**
     * yyyy-MM-dd HH:mm:ss转换成毫秒数
     * 
     * @param new_date_f
     * @return
     */
    public static long conversion(String new_date_f) {
        try {
            long millisecond = FORMAT_FULL_DATETIME.parse(new_date_f).getTime();
            return millisecond;
        } catch (ParseException e) {
            throw new RuntimeException("将字符串" + new_date_f + "按照" + FORMAT_DATE_PAGE
                                       + "格式进行转换毫秒时发生异常:", e);
        }
    }

    /**
     * 当月第一天
     * @return
     */
    public static String monthStart() {
        Calendar curCal = Calendar.getInstance();
        curCal.set(Calendar.DAY_OF_MONTH, 1);
        Date beginTime = curCal.getTime();
        String sTime = FORMAT_DATE_PAGE.format(beginTime) + " 00:00:00";
        return sTime;
    }

    /**
     * 当月最后一天
     * @return
     */
    public static String monthEnd() {
        Calendar curCal = Calendar.getInstance();
        curCal.set(Calendar.DATE, 1);
        curCal.roll(Calendar.DATE, -1);
        Date endTime = curCal.getTime();
        String eTime = FORMAT_DATE_PAGE.format(endTime) + " 23:59:59";
        return eTime;
    }

    /**
     * 
     * @param protoDate
     * @param dayOffset
     * @return
     */
    public static Date getOffsetDate(Date protoDate,int dayOffset){   
        Calendar cal = Calendar.getInstance();   
        cal.setTime(protoDate);   
        cal.add(Calendar.DATE, -dayOffset);
        return cal.getTime();   
    }  
    
    public static Date getHttpDate(String dateStr){
        try {
            return FORMAT_MERCHANT_HTTP.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("将字符串" + dateStr + "按照" + FORMAT_MERCHANT_HTTP.toPattern()
                                       + "格式进行转换日期时发生异常:", e);
        }
    }
    
    public static String getHttpDateStr(Date date){
        return FORMAT_MERCHANT_HTTP.format(date);
    }
    
    public static void main(String[] args) {
    }
}
