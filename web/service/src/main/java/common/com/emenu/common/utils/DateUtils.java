package com.emenu.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期时间工具类
 *
 * @author: zhangteng
 * @time: 2014-10-13 16:36
 */
public class DateUtils {

    public static final int SECONDS_OF_ONE_MONTH = 30 * 24 * 60 * 60;

    public static final int SECONDS_OF_FIVE_MINUTE = 5 * 60;

    private static final SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");
    private static final SimpleDateFormat WEEK_OF_DAY_FORMAT = new SimpleDateFormat("E");
    private static final SimpleDateFormat YEAR_MONTH_DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final int SECOND_IN_DAY = 60 * 60 * 24;

    private static final long MILLIS_IN_DAY = 1000L * SECOND_IN_DAY;

    /**
     * 获得当前时间
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    public static Calendar calendar() {
        Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return cal;
    }

    /**
     * 格式化日期时间
     * 日期时间格式yyy-MM-dd HH:mm
     *
     * @return
     */
    public static String formatDatetime(Date date) {
        return DEFAULT_FORMAT.format(date);
    }

    /**
     * 获取当前日期
     * 日期时间格式yyyy.MM.dd
     *
     * @return
     */
    public static String simpleDateFormat() {
        return SIMPLE_DATE_FORMAT.format(new Date());
    }

    public static String weekOfDayFormat(Date date) {
        return WEEK_OF_DAY_FORMAT.format(date);
    }

    public static String yearMonthDayFormat(Date date) {
        return YEAR_MONTH_DAY_FORMAT.format(date);
    }
    /**
     * 计算时间差
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static String calculateDiffTime(Date beginTime, Date endTime) {
        long between = 0;
        long hour = 0;
        long min = 0;
        try {
            between = (endTime.getTime() - beginTime.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        hour = (between / (60 * 60 * 1000));
        min = ((between / (60 * 1000)) - hour * 60);
        return (hour + ":" + min);
    }


    public static Date getTodayStartTime() {
        Calendar calendar = new GregorianCalendar();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getTodayEndTime() {
        Calendar calendar = new GregorianCalendar();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(final Date date1, final Date date2) {
        final long interval = date1.getTime() - date2.getTime();
        return interval < MILLIS_IN_DAY
                && interval > -1L * MILLIS_IN_DAY
                && toDay(date1.getTime()) == toDay(date2.getTime());
    }

    public static String formatDate(Date date) {
        return sdf.format(date);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static String formatDate(Date date, DateFormat dateFormat) {
        return dateFormat.format(date);
    }

    public static Date getFirstDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = -1 * (dayOfWeek == 1 ? 6 : dayOfWeek - 2);
        calendar.add(Calendar.DAY_OF_MONTH, dayOfWeek);

        return calendar.getTime();
    }

    public static Date getLastDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek == 1 ? 0 : (7 - dayOfWeek + 1);
        calendar.add(Calendar.DAY_OF_MONTH, dayOfWeek);

        return calendar.getTime();
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        return DateUtils.formatDate(date, new SimpleDateFormat(format));
    }

    private static long toDay(long mills) {
        return (mills + TimeZone.getDefault().getOffset(mills)) / MILLIS_IN_DAY;
    }

    public static void main(String[] args) {
        System.out.println(weekOfDayFormat(new Date()));
    }
}
