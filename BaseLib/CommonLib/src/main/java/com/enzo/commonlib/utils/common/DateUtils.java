package com.enzo.commonlib.utils.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 文 件 名: DateUtils
 * 创 建 人: xiaofangyin
 * 创建日期: 2016/6/3
 * 邮   箱: xiaofangyinwork@163.com
 */
public class DateUtils {

    /**
     * 日期格式：yyyy-MM-dd HH:mm
     **/
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式：HH:mm
     **/
    public static final String DF_MM_DD_HH_MM = "MM-dd HH:mm";

    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String DF_HH_MM = "HH:mm";

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * X<60s：刚刚
     * 60s<=X<1h：X分钟前
     * 1h<=X<今日24:00：今天 hh:mm
     * 今日24:00<=X<昨日24:00：昨天 hh:mm
     * 昨日24:00<=X：MM-dd hh:mm
     * 跨年：yyyy-MM-dd
     *
     * @param time 时间戳
     * @return 日期
     */
    public static String formatFriendly(Long time) {
        Date date = new Date(time);
        long diff = new Date().getTime() - date.getTime();
        long r;
        if (isLastYear(date)) {
            return formatDateTime(date, DF_YYYY_MM_DD);
        }
        if (isBeforeYesterday(date)) {
            return formatDateTime(date, DF_YYYY_MM_DD_HH_MM);
        }
        if (isYesterday(date)) {
            return "昨天" + " " + formatDateTime(date, DF_HH_MM);
        }
        if (isToday(date) && diff > hour) {
            return "今天" + " " + formatDateTime(date, DF_HH_MM);
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    private static boolean isToday(Date date) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean isYesterday(Date date) {
        Calendar now = Calendar.getInstance();
        Date nowDate = new Date(System.currentTimeMillis());
        now.setTime(nowDate);

        Calendar pre = Calendar.getInstance();
        pre.setTime(date);

        if (pre.get(Calendar.YEAR) == (now.get(Calendar.YEAR))) {
            int diffDay = now.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == 1) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBeforeYesterday(Date date) {
        Calendar now = Calendar.getInstance();
        Date nowDate = new Date(System.currentTimeMillis());
        now.setTime(nowDate);

        Calendar pre = Calendar.getInstance();
        pre.setTime(date);

        if (pre.get(Calendar.YEAR) == (now.get(Calendar.YEAR))) {
            int diffDay = now.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay >= 2) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLastYear(Date date) {
        Calendar now = Calendar.getInstance();
        Date nowDate = new Date(System.currentTimeMillis());
        now.setTime(nowDate);

        Calendar pre = Calendar.getInstance();
        pre.setTime(date);
        return now.get(Calendar.YEAR) - pre.get(Calendar.YEAR) >= 1;
    }

    /**
     * 将日期以指定格式格式化
     *
     * @param dateL 日期
     * @return
     */
    public static String formatDateTime(long dateL, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(new Date(dateL));
    }

    /**
     * 将日期以指定格式格式化
     *
     * @param date 日期
     * @return
     */
    public static String formatDateTime(Date date, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(date);
    }
}
