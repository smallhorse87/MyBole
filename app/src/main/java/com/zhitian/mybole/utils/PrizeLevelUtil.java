package com.zhitian.mybole.utils;

import android.support.v4.app.NotificationCompatSideChannelService;

import com.zhitian.mybole.R;

import java.util.ArrayList;

/**
 * Created by chenxiaosong on 16/4/6.
 */
public class PrizeLevelUtil {
    static ArrayList levelNameList = null;

    static public String prizeLevelIndexToPrizelevel(int levelIndex){
        ArrayList nameList = getPrizeNameList();

        if(levelIndex < 0 || levelIndex >= nameList.size())
        {
            return "";
        } else {
            return Integer.valueOf(levelIndex+1).toString();
        }

    }
    static public int prizeLevelToPrizeIndex(String level){

        if(level == null || level.length() == 0)
        {
            return -1;
        } else {

            return Integer.parseInt(level) - 1;
        }

    }

    static public String getPrizeLevelNameByLevel(String level) {
        int levelIdx = prizeLevelToPrizeIndex(level);

        return getPrizeLevelNameByIndex(levelIdx);
    }

    static public String getPrizeLevelNameByIndex(int levelIdx) {

        ArrayList nameList = getPrizeNameList();

        if (levelIdx < 0 || levelIdx > nameList.size())
            return "";

        return (String)nameList.get(levelIdx);

    }

    static public boolean prizeCountNeededWithLevel(String level){

        int levelIndex = prizeLevelToPrizeIndex(level);

        return prizeCountNeededWithLevelIndex(levelIndex);
    }

    static public boolean prizeCountNeededWithLevelIndex(int levelIndex){

        ArrayList nameList = getPrizeNameList();
        if (levelIndex == nameList.size()-1)
            return false;
        else
            return true;
    }

    static public ArrayList getPrizeNameList(){

        if (levelNameList == null){
            levelNameList = new ArrayList<String>();

            levelNameList.add("一等奖");
            levelNameList.add("二等奖");
            levelNameList.add("三等奖");
            levelNameList.add("优秀奖");
            levelNameList.add("鼓励奖");
        }

        return levelNameList;
    }

    static public int getPrizeLevelLogoResId(String level, String statusStr) {
        int levelIdx = prizeLevelToPrizeIndex(level);

        //0:未结束 1:已结束
        int status = Integer.parseInt(statusStr);

        switch (levelIdx){
            case 0:
                if (status == 1)
                    return R.mipmap.icon_prize_number1_normal;
                else
                    return R.mipmap.icon_prize_number1_disabled;

            case 1:
                if (status == 1)
                    return R.mipmap.icon_prize_number2_normal;
                else
                    return R.mipmap.icon_prize_number2_disabled;

            case 2:
                if (status == 1)
                    return R.mipmap.icon_prize_number3_normal;
                else
                    return R.mipmap.icon_prize_number3_disabled;

            case 3:
                if (status == 1)
                    return R.mipmap.icon_prize_number4_normal;
                else
                    return R.mipmap.icon_prize_number4_disabled;

            case 4:
                if (status == 1)
                    return R.mipmap.icon_prize_number5_normal;
                else
                    return R.mipmap.icon_prize_number5_disabled;

            default:
                return 0;
        }

    }
}
