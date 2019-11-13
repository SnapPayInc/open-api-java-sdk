package com.wiseasy.openapi.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    public DateUtil() {
    }

    public static DateFormat dateFormat(int dateFormat) {
        switch(dateFormat) {
            case 1:
                return new SimpleDateFormat("yyyyMMdd");
            case 2:
                return new SimpleDateFormat("yyyy-MM-dd");
            case 3:
                return new SimpleDateFormat("yyyy/MM/dd");
            case 4:
                return new SimpleDateFormat("yyyy年MM月dd日");
            default:
                return new SimpleDateFormat("yyyyMMdd");
        }
    }

    public static DateFormat dateTimeFormat(int dateFormat) {
        switch(dateFormat) {
            case 1:
                return new SimpleDateFormat("yyyyMMddHHmmss");
            case 2:
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            case 3:
                return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            case 4:
                return new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            default:
                return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    }

    public static String getCurrDateStr(int dateFormat) {
        return dateFormat(dateFormat).format(new Date());
    }

    public static String date2string(Date date, int dateFormat) {
        return dateFormat(dateFormat).format(date);
    }

    public static Date string2date(String date, int dateFormat) throws ParseException {
        return dateFormat(dateFormat).parse(date);
    }

    public static boolean isBeforeDate(String time1, String time2, int dateFormat) throws ParseException {
        Date date1 = dateFormat(dateFormat).parse(time1);
        Date date2 = dateFormat(dateFormat).parse(time2);
        return date1.before(date2);
    }

    public static String rollDay(Date date, int day, int dateFormat) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(5, day);
        Date d = c.getTime();
        return dateFormat(dateFormat).format(d);
    }

    public static String getCurrDateTimeStr(int dateFormat) {
        return dateTimeFormat(dateFormat).format(new Date());
    }

    public static Date string2datetime(String dateTime, int dateFormat) throws ParseException {
        return dateTimeFormat(dateFormat).parse(dateTime);
    }

    public static String datetime2string(Date dateTime, int dateFormat) {
        return dateTimeFormat(dateFormat).format(dateTime);
    }

    public static boolean isBeforeDateTime(String time1, String time2, int dateFormat) throws ParseException {
        Date date1 = dateTimeFormat(dateFormat).parse(time1);
        Date date2 = dateTimeFormat(dateFormat).parse(time2);
        return date1.before(date2);
    }

    public static String rollSecond(Date dateTime, int second, int dateFormat) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateTime);
        c.add(13, second);
        Date d = c.getTime();
        return dateTimeFormat(dateFormat).format(d);
    }

    public static String rollMinute(Date dateTime, int min, int dateFormat) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateTime);
        c.add(12, min);
        Date d = c.getTime();
        return dateTimeFormat(dateFormat).format(d);
    }

    public static String rollDateTimeDay(Date dateTime, int day, int dateFormat) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateTime);
        c.add(5, day);
        Date d = c.getTime();
        return dateTimeFormat(dateFormat).format(d);
    }

    public static String getAppointDate(Date date, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

    public static String getYear() {
        DateFormat format = new SimpleDateFormat("yyyy");
        return format.format(new Date());
    }

    public static String getMonth() {
        DateFormat format = new SimpleDateFormat("MM");
        return format.format(new Date());
    }

    public static String getToday() {
        DateFormat format = new SimpleDateFormat("dd");
        return format.format(new Date());
    }

    public static String getTime() {
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

    public static String getDayOfWeek() {
        DateFormat format = new SimpleDateFormat("EEEE");
        return format.format(new Date());
    }

    public static int getIntervalDays(Date startDate, Date endDate) {
        return getIntervalDays(dateToCal(startDate), dateToCal(endDate));
    }

    public static GregorianCalendar dateToCal(Date date) {
        return new GregorianCalendar(Integer.parseInt(getAppointDate(date, "yyyy")), Integer.parseInt(getAppointDate(date, "MM")) - 1, Integer.parseInt(getAppointDate(date, "dd")), Integer.parseInt(getAppointDate(date, "HH")), Integer.parseInt(getAppointDate(date, "mm")), Integer.parseInt(getAppointDate(date, "ss")));
    }

    public static int getIntervalDays(GregorianCalendar g1, GregorianCalendar g2) {
        int elapsed = 0;
        GregorianCalendar gc1;
        GregorianCalendar gc2;
        if (g2.after(g1)) {
            gc2 = (GregorianCalendar)g2.clone();
            gc1 = (GregorianCalendar)g1.clone();
        } else {
            gc2 = (GregorianCalendar)g1.clone();
            gc1 = (GregorianCalendar)g2.clone();
        }

        gc1.clear(14);
        gc1.clear(13);
        gc1.clear(12);
        gc1.clear(11);
        gc2.clear(14);
        gc2.clear(13);
        gc2.clear(12);
        gc2.clear(11);

        while(gc1.before(gc2)) {
            gc1.add(5, 1);
            ++elapsed;
        }

        return elapsed;
    }
}
