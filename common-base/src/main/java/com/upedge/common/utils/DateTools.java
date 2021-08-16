package com.upedge.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cjq on 2019/3/13.
 */
public class DateTools {

   static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    public static int getDistanceOfTwoDay(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (int)(Math.ceil((afterTime - beforeTime) / 86400000.0));
    }

    //两个日期区间的秒数
    public static long getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / 1000L;
    }

    /**
     * 近30天 开始日期
     */
   public static String getLatest30DayStart(Date date){
       Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       cal.add(Calendar.DATE, -29);
       String start=sdf.format(cal.getTime());
       return start;
   }
    /**
     * 近30天 结束日期
     */
    public static String getLatest30DayEnd(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return sdf.format(cal.getTime());
    }

    public static Map<String, Integer> mapLatest30Days(Date date) {
        Map<String, Integer> map= new HashMap<>();
        Calendar cal;
        for(int i=29;i>0;i--){
            cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            String timeStr=sdf.format(cal.getTime());
            map.put(timeStr,30-i);
        }
        cal = Calendar.getInstance();
        cal.setTime(date);
        String timeStr=sdf.format(cal.getTime());
        map.put(timeStr,30);

        return map;
    }

    /**
     * 近30天 日期列表
     */
    public static List<String> getLatest30Days(Date date) {
        List<String> list= new ArrayList();
        Calendar cal;
        for(int i=29;i>0;i--){
            cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            String timeStr=sdf.format(cal.getTime());
            list.add(timeStr);
        }
        cal = Calendar.getInstance();
        cal.setTime(date);
        String timeStr=sdf.format(cal.getTime());
        list.add(timeStr);

        return list;
    }

    // 获取前月的最后一天
    public static String getMonthEndDay(Date date){
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        String endDay = sdf.format(cale.getTime());
        return endDay;
    }

    //获取当前月第一天
    public static String getMonthStartDay(Date date){
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        String startDay = sdf.format(cale.getTime());
        return startDay;
    }

    public static Map<String, Integer> mapMonthDays(Date date) {
        Map<String, Integer> map= new HashMap<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayNumOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);;
        cal.set(Calendar.DAY_OF_MONTH, 1);// 从一号开始
        for (int i = 0; i < dayNumOfMonth; i++, cal.add(Calendar.DATE, 1)) {
            Date d = cal.getTime();
            String timeStr = sdf.format(d);
            map.put(timeStr,i+1);
        }
        return map;
    }


    /**
     * 获取当前月的天数列表
     */
    public static List<String> getMonthDays(Date date) {
        List<String> fullDayList = new ArrayList();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int dayNumOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);;
        cal.set(Calendar.DAY_OF_MONTH, 1);// 从一号开始

        for (int i = 0; i < dayNumOfMonth; i++, cal.add(Calendar.DATE, 1)) {
            Date d = cal.getTime();
            String timeStr = sdf.format(d);
            fullDayList.add(timeStr);
        }
        return fullDayList;
    }

    /**
     *
     * @param date  当天日期
     * @param type   0: 本周开始日期 1:本周结束日期
     * @return
     */
    public static String getTimeInterval(Date date, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        //所在周星期一的日期
        String imptimeBegin = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        //所在周星期日的日期
        String imptimeEnd = sdf.format(cal.getTime());
        if(type==0){
            return imptimeBegin;
        }else {
            return imptimeEnd;
        }
    }

    public static int getDateWeek(String date){
        try {
            Date d=sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
            if (1 == dayWeek) {
                return 7;
            }
            // 获得当前日期是一个星期的第几天
            int day = cal.get(Calendar.DAY_OF_WEEK);
            return day-1;
        } catch (ParseException e) {
            return -1;
        }
    }


    public static List<String> findDates(Date date)
    {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        //所在周星期一的日期
        Date dBegin = cal.getTime();
        cal.add(Calendar.DATE, 6);
        //所在周星期日的日期
        Date dEnd = cal.getTime();


        List<String> lDate = new ArrayList();
        lDate.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        while (dEnd.after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(sdf.format(calBegin.getTime()));
        }
        return lDate;
    }

    /**
     * 获取前一天日期
     */
    public  static Calendar getBeforeDay(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-1);
        return calendar;
    }

    public static void main(String[] args) throws ParseException {
//        List<String> dayStr=DateTools.getMonthDays(new Date());
//        System.out.println(dayStr);
//        System.out.println(mapMonthDays(new Date()));


        String monthStartDay = getMonthStartDay(new Date());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1=simpleDateFormat.parse("2019-05-06 12:58:47");
        Date date2=simpleDateFormat.parse("2019-05-25 18:55:33");
        System.out.println(DateTools.getDistanceOfTwoDate(date1,date2)/86400.0);


    }
}

