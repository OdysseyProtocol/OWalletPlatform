package com.coinwallet.common.util;



import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {

    public static String defaultDatePattern = "yyyy-MM-dd";

    public static String yMdHmsDatePattern = "yyyy-MM-dd HH:mm:ss";

    public static String hhmmss = "HH:mm:ss";

    /**
     */
    public static String getDatePattern() {
        return defaultDatePattern;
    }

    /**
     */
    public static String getToday() {
        Date today = new Date();
        return format(today);
    }

    /**
     */
    public static String formatYMDHMS(Date date) {
        return date == null ? " " : format(date, yMdHmsDatePattern);
    }
    /**
     */
    public static String format(Date date) {
        return date == null ? " " : format(date, getDatePattern());
    }

    /**
     */
    public static String format(Date date, String pattern) {
        return date == null ? " " : new SimpleDateFormat(pattern).format(date);
    }


    /**
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    public static String getLastDayOfMonth(String year, String month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    }


    public static Date addDay(Date date, int n) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, n);
        date = calendar.getTime();
        return date;
    }

    public static Date getDayWithAssignTime(int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        Date date = c.getTime();
        return date;
    }


    public static Date parse(String strDate) throws ParseException {
        return StringUtils.isBlank(strDate) ? null : parse(strDate,
                getDatePattern());
    }


    public static Date parse(String strDate, String pattern)
            throws ParseException {
        return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(
                pattern).parse(strDate);
    }

    public static Integer getAge(Date birthday) {
        Calendar calendar = new GregorianCalendar();
        Date now = new Date();
        calendar.setTime(now);
        int nowYear = calendar.get(Calendar.YEAR);
        calendar.setTime(birthday);
        int birthdayYear = calendar.get(Calendar.YEAR);

        return nowYear - birthdayYear;
    }

    public static Date addMinute(Date time, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.MINUTE, minute);
        Date date = c.getTime();
        return date;
    }

    public static Date addHour(Date time, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.HOUR_OF_DAY, hour);
        Date date = c.getTime();
        return date;
    }


    public static String formatStringDate(String time) {
        if (time == null) return null;
        return time.substring(0, 10);
    }
    

    public static String getYesterday(Date date,String format){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DAY_OF_MONTH, -1);
    	date = calendar.getTime();
    	return format(date, format);
    }

    public static TimeInterval createDateInterval(Long source, Integer diffWithSource, Integer interval){
        TimeInterval timeInterval = new TimeInterval();
        Date sourceDate = new Date(source);//源时间
        Date sourceDateZeroOclock = org.apache.commons.lang3.time.DateUtils.truncate(sourceDate, Calendar.DATE);//保留日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(sourceDateZeroOclock);
        cal.add(Calendar.DATE,diffWithSource);//相差天数
        timeInterval.setStartTime(cal.getTime());
        cal.add(Calendar.DATE,interval);//时间跨度
        timeInterval.setEndTime(cal.getTime());
        return timeInterval;
    }

    

    public static Date getCurrentBeforeOrAfter(Date date,int days){
    	if(date == null){
    		return null;
    	}
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DAY_OF_MONTH, days);
    	date = calendar.getTime();
    	return date;
    }

    /**
     *
     * @param date
     * @return
     */
    public static String getEnglishDate(Date date) {
        DateFormat df = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
        return df.format(date);
    }

    /**
     * @param timeStamp
     * @return
     */
    public static Date TimeStamp2Date(Long timeStamp) throws ParseException {
        Long longTime = timeStamp * 1000;
        String date = new SimpleDateFormat(yMdHmsDatePattern).format(new Date(longTime));
        return parse(date,yMdHmsDatePattern);
    }
}
