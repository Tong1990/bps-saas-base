package com.tony.saas.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018-8-23.
 */
public class DateUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String HH_MM_SS = "HH:mm:ss";

    public static void main(String[] args) {
        Date ss = DateUtil.parseStringToDate( "2017-08-10 10:08", YYYY_MM_DD_HH_MM );
        System.out.println( ss );
    }


    /**
     * 获取当前时间
     * @return
     */
    public static Timestamp getCurrentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取当前时间Long
     * @return
     */
    public static Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前日期
     * @return
     */
    public static Date getCurrentDate() {
        return new Date( getCurrentTimeMillis() );
    }

    /**
     * 毫秒转日期
     * @param millis
     * @return
     */
    public static Date parseMillisToDate(Long millis) {
        return new Date(millis);
    }


    /**
     * 毫秒转时间戳
     * @param millis
     * @return
     */
    public static Timestamp parseMillisToTimestamp(Long millis) {
        return new Timestamp( millis );
    }

    /**
     * date转timestamp
     * @param date
     * @return
     */
    public static Timestamp parseDateToTimestamp(Date date){
        return new Timestamp(date.getTime());
    }

    /**
     * 日期字符串转日期
     * @param data
     * @param format
     * @return
     */
    public static Date parseStringToDate(String data,String format) {

        String sFormat =format;
        if( sFormat == null || sFormat.length() == 0 ){
            sFormat = YYYY_MM_DD;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sFormat);
        java.util.Date date = null;
        if (data == null){
            return date;
        }

        try {
            date = simpleDateFormat.parse(data);
            return date;

        } catch (Exception ex) {
            return date;
        }
    }

    /**
     * 默认输出yyyy-MM-dd
     */
    public static String parseDateToString(Date date,String format) {

        String sFormat =format;
        if( sFormat == null || sFormat.length() == 0 ){
            sFormat = YYYY_MM_DD;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sFormat);
        try {
            return simpleDateFormat.format(date);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 日期+毫秒
     * @param date
     * @param millis
     * @return
     */
    public static Date addMillisToDate(Date date,Long millis) {
        return new Date(date.getTime()+millis);
    }

    /**
     * 增加天数到日期
     * @param date
     * @param days
     * @return
     */
    public static Date addDaysToDate(Date date,Integer days) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, days);
            return cal.getTime();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 在当前日期上+毫秒
     * @param millis
     * @return
     */
    public static Date addMillisToCurrentDate(Long millis) {
        return new Date(getCurrentTimeMillis()+millis);
    }


    /**
     * 输出Date (*小时间隔 前：负数 后：正数 )
     *
     */
    public static Date addHoursToDate(Date date, Integer hours) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR, hours);
            return cal.getTime();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 输出Date (*小时间隔 前：负数 后：正数 )
     *
     */
    public static Timestamp addHoursToTimestamp(Timestamp time, Integer hours) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            cal.add(Calendar.HOUR, hours);
            return new Timestamp(cal.getTimeInMillis());
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 输出Date (*小时间隔 前：负数 后：正数 )
     *
     */
    public static Date addMonthsToDate(Date dateIn, Integer months) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateIn);
            cal.add(Calendar.MONTH, months);
            return cal.getTime();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 输出Date (*小时间隔 前：负数 后：正数 )
     *
     */
    public static Date addMinuteToDate(Date dateIn, Integer minute) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateIn);
            cal.add(Calendar.MINUTE, minute);
            return cal.getTime();
        } catch (Exception ex) {
            return null;
        }
    }




    /**
     * 日期比较函数(dt1>dt2返回1 dt1<dt2返回-1 dt1=dt2返回0)
     * @param dt1 比较日期1
     * @param dt2 比较日期2
     * @return int
     */
    public static int compareDate(Date dt1, Date dt2) throws Exception{
        if (dt1.getTime() > dt2.getTime()) {
            return 1;
        } else if (dt1.getTime() < dt2.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getFirstDayOfYear(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获取当年的第一天
     * @param
     * @return
     */
    public static Date getFirstDayOfCurrentYear(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getFirstDayOfYear(currentYear);
    }

    /**
     * 获取当年的第一个月
     * @return String
     */
    public static String getFirstMonthOfCurrentYear(){
        return new SimpleDateFormat("yyyy-MM").format(getFirstDayOfCurrentYear());
    }


    /**
     * 获取当前的时间字符串格式(精确到年月日时分秒)
     * @return String
     */
    public static String getCurrentTimeString(){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }


    public static String getCurrentDateString(String format) {
        return parseDateToString( getCurrentDate(), format );
    }

    /**
     * 获取当前月份
     * @return String
     */
    public static String getCurrentMonth(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        return new SimpleDateFormat("yyyy-MM").format(c.getTime());
    }

    /**
     * 获取日期所在月的第一天
     * @param dateIn
     * @return
     */
    public static Date getFirstDayOfMonth(Date dateIn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
//		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取日期所在月的最后一天
     * @param dateIn
     * @return
     */
    public static Date getLastDayOfMonth(Date dateIn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
//		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取某天的开始时间
     * @param dateIn
     * @return
     */
    public static Date getFirstTimeOfDay(Date dateIn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某天的结束时间
     * @param dateIn
     * @return
     */
    public static Date getLastTimeOfDay(Date dateIn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /***
     * 对日期字符串月份增减函数
     * @param formatter 日期格式字符串(格式：yyyy-MM或yyyy-MM-dd或yyyy-MM-dd hh:mm:ss)
     * @param i 月份增加或减少参数,值等于0月份不变
     * @return 日期字符串
     */
    public static String dateFormat(String dateStr,String formatter,int i) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, i);
        date = cl.getTime();
        return sdf.format(date);
    }

    /**
     * 获取本周第一天
     * @return
     */
    public static Date getWeekStartDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date = cal.getTime();
        return date;
    }

    /**
     * 获取前几天的日期
     * @param n
     * @param pattern
     */
    public static String beforeDaysToNowDate(int n, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(calendar.DAY_OF_YEAR) - n);
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(calendar.getTime());
    }

    /**
     * 获取前几个小时的日期
     * @param n
     */
    public static String beforeHourToNowDate(int n) {
        Calendar calendar = Calendar.getInstance();
        /* HOUR_OF_DAY 指示一天中的小时 */
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - n);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(n+"个小时前的时间：" + df.format(calendar.getTime()));
//        System.out.println("当前的时间：" + df.format(new Date()));
        return df.format(calendar.getTime());
    }

    /**
     * 获取前几分钟的日期
     * @param n
     */
    public static String beforeMinuteToNowDate(int n) {
        Calendar calendar = Calendar.getInstance();
        /* HOUR_OF_DAY 指示一天中的小时 */
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - n);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(n+"分钟前的时间：" + df.format(calendar.getTime()));
//        System.out.println("当前的时间：" + df.format(new Date()));
        return df.format(calendar.getTime());
    }

    /**
     * 方法描述：<br>
     * 	秒数转化为时分秒
     * @author: chenjingshen
     * @param time
     * @return
     * @date: 2017年12月5日
     */
    public static String seccondToTime(Integer time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}
