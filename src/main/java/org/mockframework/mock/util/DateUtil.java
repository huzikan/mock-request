package org.mockframework.mock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期Util类 
 *
 * @author deepWhite
 */
public class DateUtil {
    private final static String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获得默认的 date pattern 
     */
    public static String getDatePattern() {
        return defaultDatePattern;
    }

    /**
     * 返回预设Format的当前日期字符串 
     */
    public static String getToday() {
        Date today = new Date();
        return format(today);
    }

    /**
     * 使用预设Format格式化Date成字符串 
     */
    public static String format(Date date) {
        return date == null ? " " : format(date, getDatePattern());
    }

    /**
     * 使用参数Format格式化Date成字符串 
     */
    public static String format(Date date, String pattern) {
        return date == null ? " " : new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 使用预设格式将字符串转为Date 
     */
    public static Date parse(String strDate) throws ParseException {
        if (strDate == null || strDate.equals("")) {
            return new Date();
        }

        return parse(strDate, getDatePattern());
    }

    /**
     * 使用参数Format将字符串转为Date 
     */
    public static Date parse(String strDate, String pattern) throws ParseException {
        if (strDate == null || strDate.equals("")) {
            return new Date();
        }
        if (pattern == null || pattern.equals("")) {
            pattern = getDatePattern();
        }

        return  new SimpleDateFormat(pattern).parse(strDate);
    }

    /**
     * 在日期上增加数年
     */
    public static Date addYear(Date date, int yearNum) {
        Calendar cal = Calendar.getInstance();
        date = date == null ? new Date() : date;
        cal.setTime(date);
        cal.add(Calendar.YEAR, yearNum);

        return cal.getTime();
    }

    /**
     * 在日期上增加数月
     */
    public static Date addMonth(Date date, int monthNum) {
        Calendar cal = Calendar.getInstance();
        date = date == null ? new Date() : date;
        cal.setTime(date);
        cal.add(Calendar.MONTH, monthNum);

        return cal.getTime();
    }

    /**
     * 在日期上增加数天
     */
    public static Date addDay(Date date, int dayNum) {
        Calendar cal = Calendar.getInstance();
        date = date == null ? new Date() : date;
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, dayNum);

        return cal.getTime();
    }

    /**
     * 获取当前月份的第一天
     * @param date
     * @return
     */
    public static String getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        date = date == null ? new Date() : date;
        //设置当前日期
        cal.setTime(date);
        //设置日为一号
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //返回结果
        return format(cal.getTime());
    }

    /**
     * 获取当前月份的最后一天
     * @param date
     * @return
     */
    public static String getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        date = date == null ? new Date() : date;
        //设置当前日期
        cal.setTime(date);
        //设置日为一号
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //月份加一，得到下个月的一号
        cal.add(Calendar.MONTH, 1);
        //下一个月减一为本月最后一天
        cal.add(Calendar.DAY_OF_MONTH, -1);
        //获得月末是几号
        return format(cal.getTime());
    }

    /**
     * 获取当前日期的年、月、日、时、分、秒、星期的映射集
     * @param date
     * @return
     */
    public static Map<String, Integer> getDateMap(Date date) {
        Calendar cal = Calendar.getInstance();
        date = date == null ? new Date() : date;
        cal.setTime(date);
        //获取日期映射集
        Map<String, Integer> dateMap = new HashMap<String, Integer>();
        dateMap.put("year", cal.get(Calendar.YEAR));
        dateMap.put("month", cal.get(Calendar.MONTH) + 1);
        dateMap.put("day", cal.get(Calendar.DAY_OF_MONTH));
        dateMap.put("hour", cal.get(Calendar.HOUR_OF_DAY));
        dateMap.put("minute", cal.get(Calendar.MINUTE));
        dateMap.put("second", cal.get(Calendar.SECOND));
        dateMap.put("week", cal.get(Calendar.DAY_OF_WEEK));

        return dateMap;
    }

    /**
     * 比较两个日期的相差天数
     * @param fDate
     * @param oDate
     * @return
     */
    public static int dayDifference(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

        return Math.abs(day2 - day1);
    }
}