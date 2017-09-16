package com.sikefeng.chinaren.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {

    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATE1 = "yyyy-MM";
    public static final String FORMAT_DATE2 = "dd-MM";
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATETIME_2 = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DATETIME_3 = "dd MMMM HH:mm";
    public static final String FORMAT_DATETIME_4 = "dd MMMM yyyy";
    public static final String FORMAT_DATETIME_5 = "dd MMMM HH:mm";
    public static final String FORMAT_DATETIME_6 = "dd MMMM";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_DAY = "yyyyMMdd";
    public static final String FORMAT_TIME_SHORT = "HH:mm";
    public static final String FORMAT_DAY_1 = "dd/MM/yyyy";
    public static final String FORMAT_DAY_2 = "EEEE dd MMMM";
    public static final String FORMAT_DAY_3 = "dd MMMM";

    /**
     * 将时间格式化成: "yyyyMMdd"
     *
     * @param date Date
     * @return "20140627"
     */
    public static String formatDir(Date date) {
        return format(date, FORMAT_DAY);
    }

    /**
     * 将时间格式化成: "yyyy-MM-dd"
     *
     * @param date Date
     * @return "2014-06-27"
     */
    public static String formatDate(Date date) {
        return format(date, FORMAT_DATE);
    }

    /**
     * 将时间格式化成: "yyyy-MM"
     *
     * @param date Date
     * @return "2014-06"
     */
    public static String formatDate1(Date date) {
        return format(date, FORMAT_DATE1);
    }

    /**
     * 将时间格式化成: "yyyy/MM/dd"
     *
     * @param date Date
     * @return "2014/06/27"
     */
    public static String format2(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 将时间格式化成："HH:mm:ss"
     *
     * @param date Date
     * @return "16:46:32"
     */
    public static String formatTime(Date date) {
        return format(date, FORMAT_TIME);
    }

    /**
     * 将时间格式化成："yyyy-MM-dd HH:mm:ss"
     *
     * @param date Date
     * @return "2014-06-27 16:46:32"
     */
    public static String formatDateTime(Date date) {
        return format(date, FORMAT_DATETIME);
    }

    /**
     * 获取当前时间, 并格式化成："yyyy-MM-dd HH:mm:ss" 输出
     *
     * @return "2014-06-27 16:46:32"
     */
    public static String getCurrentDateTime2Str() {
        return format(new Date(), FORMAT_DATETIME);
    }

    /**
     * 获取当前时间, 并格式成："yyyyMMddHHmmss" 输出
     *
     * @return "20140627164632"
     */
    public static String getCurrentDateTime2Str2() {
        return format(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 将<code>format["yyyy-MM-dd"]</code>字符串解析成对象
     *
     * @param date "2014-06-27"
     * @return Date
     * @throws Exception Exception
     */
    public static Date parseDate(String date) throws Exception {
        return parse(date, FORMAT_DATE);
    }

    /**
     * 将<code>format["yyyy-MM-dd HH:mm:ss"]</code>字符串解析成对象
     *
     * @param date "2014-06-27 16:46:32"
     * @return Date
     * @throws Exception Exception
     */
    public static Date parseTime(String date) throws Exception {
        return parse(date, FORMAT_DATETIME);
    }

    /**
     * 将<code>format["yyyy-MM-dd"]</code>字符串 + " 23:59:59" <br>
     * 然后再解析成对象
     *
     * @param date Date
     * @return Date
     * @throws Exception Exception
     */
    public static Date getMaxTimeByStringDate(String date) throws Exception {
        String maxTime = date + " 23:59:59";
        return parse(maxTime, FORMAT_DATETIME);
    }

    /**
     * 获取某个月的最后一天
     *
     * @param sDate1 Date
     * @return String
     */
    public static String getLastDayOfMonth(Date sDate1) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(sDate1);
        final int _LASTDAY = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay1.getTime();
        lastDate.setDate(_LASTDAY);
        return formatDate(lastDate);
    }

    /**
     * 获取当前年
     *
     * @param date Date
     * @return 2017
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     *
     * @param date Date
     * @return 04
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前天
     *
     * @param date Date
     * @return 23
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间
     *
     * @param date Date
     * @return 22
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前分钟
     *
     * @param date Date
     * @return 16
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 获取当前秒
     *
     * @param date Date
     * @return 23
     */
    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 获取当前毫秒
     *
     * @param date Date
     * @return 33
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 获取当前周
     *
     * @param date Date
     * @return 20
     */
    public static int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = Constants.VALUE_7;
        }
        return dayOfWeek;
    }

    /**
     * 日期相加
     *
     * @param date Date
     * @param day  int
     * @return Date
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * Constants.VALUE_24 * Constants.VALUE_3600 * Constants.VALUE_3600);
        return c.getTime();
    }


    /**
     * 日期相减
     *
     * @param date  Date
     * @param date1 Date
     * @return int
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (Constants.VALUE_24 * Constants.VALUE_3600 * Constants.VALUE_1000));
    }

    /**
     * 日期相减(返回秒值)
     *
     * @param date  Date
     * @param date1 Date
     * @return int
     */
    public static Long diffDateTime(Date date, Date date1) {
        return (Long) ((getMillis(date) - getMillis(date1)) / Constants.VALUE_1000);
    }


    /**
     * 获得时间戳
     *
     * @return String
     * @throws Exception Exception
     */
    public static String getTime() {
        Date date = new Date();
        return String.valueOf(date.getTime() / Constants.VALUE_1000);
    }


    /**
     * 是否是闰年
     *
     * @param year 当前年
     * @return boolean
     */
    private static boolean isLeapYear(int year) {
        return ((year % Constants.VALUE_4 == 0 && year % Constants.VALUE_100 != 0) || year % Constants.VALUE_400 == 0);
    }


    /**
     * 时间格式化方法：<br>
     * 提供将时间转换成指定格式的字符串
     *
     * @param date   Date
     * @param format String
     * @return String
     */
    public static String format(Date date, String format) {
        String result = "";
        if (null == date) {
            return result;
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }


    /**
     * 时间字符串解析方法：<br>
     * 提供将指定格式的时间字符串解析成时间对象
     *
     * @param date   Date
     * @param format String
     * @return Date
     * @throws Exception Exception
     */
    public static Date parse(String date, String format) throws Exception {
        DateFormat df = new SimpleDateFormat(format);
        return df.parse(date);
    }

    /**
     * 计算两个时间差
     *
     * @param endDate Date
     * @param nowDate Date
     * @param poor    1为天数，2位小时数，3位分钟数
     * @return long
     */
    public static long getDatePoor(Date endDate, Date nowDate, int poor) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between = time1 - time2;
        long day = between / (Constants.VALUE_24 * Constants.VALUE_60 * Constants.VALUE_60 * Constants.VALUE_1000);
        long hour = (between / (Constants.VALUE_60 * Constants.VALUE_60 * Constants.VALUE_1000) - day * Constants.VALUE_24);
        long min = ((between / (Constants.VALUE_60 * Constants.VALUE_1000)) - day * Constants.VALUE_24 * Constants.VALUE_60 - hour * Constants.VALUE_60);
        long s = (between / Constants.VALUE_1000 - day * Constants.VALUE_24 * Constants.VALUE_60 * Constants.VALUE_60 - hour * Constants.VALUE_60 * Constants.VALUE_60 - min * Constants.VALUE_60);
        long ms = (between - day * Constants.VALUE_24 * Constants.VALUE_60 * Constants.VALUE_60 * Constants.VALUE_1000 - hour * Constants.VALUE_60 * Constants.VALUE_60 * Constants.VALUE_1000
                - min * Constants.VALUE_60 * Constants.VALUE_1000 - s * Constants.VALUE_1000);
//        System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒" + ms
//                + "毫秒");

        long poorValue = 0;
        switch (poor) {
            case 1:
                poorValue = day;
                break;
            case 2:
                poorValue = day * Constants.VALUE_24 + hour;
                break;
            case Constants.VALUE_3:
                poorValue = day * Constants.VALUE_24 * Constants.VALUE_60 + hour * Constants.VALUE_60 + min;
                break;
            default:break;
        }
        return poorValue;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
//        return day + "天" + hour + "小时" + min + "分钟";
    }


    /**
     * 获取今天、昨天和具体日期时间
     *
     * @param date Date
     * @return String
     */
    public static String getChatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        if (date == null) {
            return "";
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return format1.format(date);
        } else if (current.before(today) && current.after(yesterday)) {

            return format.format(date);
        } else {
            return format.format(date);
        }
    }
}
