package com.zhitian.mybole.entity;

import com.zhitian.mybole.R;

/**
 * Created by chenxiaosong on 16/3/16.
 */
public class RankInfo {

    private String name;
    private int ranking;

    private ImageSetInfo avatar;
    private String player;
    private String score;
    private String prizeLevel;
    private String status;
    private boolean isUse;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public ImageSetInfo getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageSetInfo avatar) {
        this.avatar = avatar;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPrizeLevel() {
        return prizeLevel;
    }

    public void setPrizeLevel(String prizeLevel) {
        this.prizeLevel = prizeLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIsUse() {
        return isUse;
    }

    public void setIsUse(boolean isUse) {
        this.isUse = isUse;
    }

    public int convertPrizeLevelToInt(){

        if(prizeLevel == null || prizeLevel.length() == 0)
        {
            return 0;
        } else {
            return Integer.parseInt(prizeLevel);
        }

    }

    public String getPrizeLevelStr() {
        int level = convertPrizeLevelToInt();

        switch (level){
            case 1:
                return "一等奖";

            case 2:
                return "二等奖";

            case 3:
                return "三等奖";

            case 4:
                return "优秀奖";

            case 5:
                return "鼓励奖";

            default:
                return "";
        }

    }

    public int getPrizeLevelLogoResId() {
        int level = convertPrizeLevelToInt();

        //0:未结束 1:已结束
        int status = Integer.parseInt(this.status);

        switch (level){
            case 1:
                if (status == 1)
                    return R.mipmap.icon_prize_number1_normal;
                else
                    return R.mipmap.icon_prize_number1_disabled;

            case 2:
                if (status == 1)
                    return R.mipmap.icon_prize_number2_normal;
                else
                    return R.mipmap.icon_prize_number2_disabled;

            case 3:
                if (status == 1)
                    return R.mipmap.icon_prize_number3_normal;
                else
                    return R.mipmap.icon_prize_number3_disabled;

            case 4:
                if (status == 1)
                    return R.mipmap.icon_prize_number4_normal;
                else
                    return R.mipmap.icon_prize_number4_disabled;

            case 5:
                if (status == 1)
                    return R.mipmap.icon_prize_number5_normal;
                else
                    return R.mipmap.icon_prize_number5_disabled;

            default:
                return 0;
        }

    }
}
