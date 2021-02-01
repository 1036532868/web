package com.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateTimeUtil {

    /**
     * 获取当前时间所在的区间以及之后的 当天的区间, 最多4个,
     * 最晚的区间为 22:00 - 24:00
     * @return 时间菜单
     */
    public static List<Date> getDateMenus() {
        //定义一个 List<Date>, 储存所有时间段
        List<Date> dates = getDates(12);
        //判断当前时间属于那个时间范围
        Date now = new Date();
        for (Date startDate : dates) {

            //开始时间 <= 当前时间 < 开始时间 + 2小时
            if (startDate.getTime() <= now.getTime() &&
                    now.getTime() < addDateHour(startDate, 2).getTime()) {

                now = startDate;
                break;

            }
        }

        List<Date> dateMenus = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            Date date = addDateHour(now, i * 2);

            if (date != null) {
                dateMenus.add(date);
            }

        }

        return dateMenus;
    }

    /**
     * 获得当天的 12 个区间
     * @param partNum 区间的数量
     * @return
     */
    public static List<Date> getDates(int partNum) {

        List<Date> dates = new ArrayList<>();
        //循环12次
        Date date = toDayStartHour(new Date()); //凌晨
        for (int i = 0; i < partNum; i++) {

            //每次递增2小时,将每次递增的时间存入 List<Date> 中
            dates.add(addDateHour(date, i * 2));

        }

        return dates;
    }

    /**
     * 获得当天的凌晨0点时间
     * @param date
     * @return
     */
    public static Date toDayStartHour(Date date) {

        String nowString = format(date, "yyyy-MM-dd HH:mm:ss");

        String[] nowArray = nowString.split(" ");
        String dayStartHourString = nowArray[0] + " 00:00:00";

        Date dayStartHours = parse(dayStartHourString, "yyyy-MM-dd HH:mm:ss");

        return dayStartHours;
    }

    /**
     * 为日期增加 多少小时
     * @param date
     * @param stride
     * @return
     */
    public static Date addDateHour(Date date, int stride){

        String dayStartDateString = format(date, "yyyy-MM-dd HH:mm:ss");
        // yyyy-MM-dd, HH:mm:ss
        String[] dayAndTime = dayStartDateString.split(" ");
        // HH, mm, ss
        String[] timeArray = dayAndTime[1].split(":");

        if (timeArray[0].startsWith("0")){

            timeArray[0] = timeArray[0].substring(1);

        }

        int dateInt = Integer.valueOf(timeArray[0]);
        int newHour = dateInt + stride;

        String newHourString = String.valueOf(newHour);
        if (newHour < 10){
            newHourString = "0" + newHourString;
        }

        // 如果新的小时大于23,则返回 null
        if (newHour > 23){
            return null;
        }

        String resultDateString = dayAndTime[0] + " " + newHourString +":"+ timeArray[1] +":"+ timeArray[2];

        Date resultDate = parse(resultDateString, "yyyy-MM-dd HH:mm:ss");

        return resultDate;
    }

    public static String getSysTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();
        String dateStr = sdf.format(date);

        return dateStr;

    }

    /**
     * 将日期转换为
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(date);
    }

    public static Date parse(String dateString, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static void main(String[] args) {
        List<Date> l = getDateMenus();
        for (Date date : l) {
            System.err.println(date.toString());
        }
    }

}
