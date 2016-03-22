package com.zhitian.mybole.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chenxiaosong on 16/3/22.
 */
public class TimeUtil {

    static final long MILLION_SEC_PER_DAY = 24 * 60 * 60 * 1000;

    static  public String DateToYYYYMMDDHHMM(Date date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM-dd HH:mm");

        return dateFormat.format(date);
    }

    static public Date TimestampStrToDate(String timestampStr){
        long timestamp = Long.parseLong(timestampStr);

        return new Date(timestamp);
    }

    static public Date AddDaysSince(Date date, int moreDays){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, moreDays);

        return cal.getTime();
    }

    static public long DiffDaysBetweenTimestamps(String beginTimeStr, String endTimeStr){
        long beginTimestamp = Long.parseLong(beginTimeStr);
        long endTimestamp = Long.parseLong(endTimeStr);

        if (beginTimestamp >= endTimestamp)
            return -1;
        else
            return (endTimestamp - beginTimestamp) / MILLION_SEC_PER_DAY;
    }
}
