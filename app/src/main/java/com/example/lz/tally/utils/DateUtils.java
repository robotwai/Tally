package com.example.lz.tally.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by liz on 16-12-20.
 */

public class DateUtils {
    /*获取当前时间时间戳*/
    public static long currentTime(){
        return System.currentTimeMillis();
    }
    /*获取某个时间前面几天零点的时间戳*/
    public static long afterTime(long time,int i){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String zeroTime = sdf.format(time);
        try {
            long longtime  = sdf.parse(zeroTime).getTime();
            return  longtime- i*3600*24*1000;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /** * 获取指定日期是星期几
     * 参数为null时表示获取当前日期是星期几
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if(date != null){
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekOfDays[w];
    }

    public static List<String> getListWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if(date != null){
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        List<String> list= new ArrayList<>();

        for (int i = w+1;i<7;i++){
            list.add(weekOfDays[i]);
        }
        for (int i =0; i<w;i++){
            list.add(weekOfDays[i]);
        }
        list.add(weekOfDays[w]);
        return list;
    }
}
