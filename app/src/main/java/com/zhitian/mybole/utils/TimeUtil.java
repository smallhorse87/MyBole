package com.zhitian.mybole.utils;

import android.content.Context;

import com.bigkoo.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chenxiaosong on 16/3/22.
 */
public class TimeUtil {

    static final long MILLION_SEC_PER_DAY = 24 * 60 * 60 * 1000;

    static  public String dateToYYYYMMDDHHMM(Date date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM-dd HH:mm");

        return dateFormat.format(date);
    }

    static  public String timeIntervalToYYYYMMDDHHMM(String timeInterval){
        if (timeInterval == null)
            return "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM-dd HH:mm");

        return dateFormat.format(TimestampStrToDate(timeInterval));
    }

    static public Date TimestampStrToDate(String timestampStr){
        long timestamp = Long.parseLong(timestampStr);

        return new Date(timestamp);
    }

    static public Date addDaysSince(Date date, int moreDays){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, moreDays);

        return cal.getTime();
    }

    static public long diffDaysBetweenTimestamps(String beginTimeStr, String endTimeStr){
        long beginTimestamp = Long.parseLong(beginTimeStr);
        long endTimestamp = Long.parseLong(endTimeStr);

        if (beginTimestamp >= endTimestamp)
            return -1;
        else
            return (endTimestamp - beginTimestamp) / MILLION_SEC_PER_DAY;
    }

    static public void createDatePicker(Context context, Date date, TimePickerView.OnTimeSelectListener listener){
        TimePickerView pvTime = new TimePickerView(context, TimePickerView.Type.ALL);

        //控制时间范围
        if (date == null)
            pvTime.setTime(new Date());
        else
            pvTime.setTime(date);

        pvTime.setCyclic(false);
        pvTime.setCancelable(true);

        //时间选择后回调
        pvTime.setOnTimeSelectListener(listener);

        pvTime.show();
    }

}
