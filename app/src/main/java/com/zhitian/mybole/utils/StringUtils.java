package com.zhitian.mybole.utils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenxiaosong on 16/3/5.
 */
public class StringUtils {

    public static boolean isValidTel(String telnum)
    {
        Pattern p=Pattern.compile("^1(3|4|5|7|8)\\d{9}$");
        Matcher m=p.matcher(telnum);
        return m.matches();
    }

    public static boolean isValidPasscode(String passcode)
    {
        Pattern p=Pattern.compile("^\\d{4}$");
        Matcher m=p.matcher(passcode);
        return m.matches();
    }

    public static String randomFileName(){

        long timeInterval = System.currentTimeMillis()/1000;

        String timeIntervalStr = Long.valueOf(timeInterval).toString();

        return timeIntervalStr+".jpeg";
    }
}
