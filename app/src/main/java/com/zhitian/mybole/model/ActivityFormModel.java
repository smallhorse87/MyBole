package com.zhitian.mybole.model;

import com.zhitian.mybole.entity.ActivityInfo;
import com.zhitian.mybole.entity.GameInfo;
import com.zhitian.mybole.entity.PrizeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxiaosong on 16/3/22.
 */
public class ActivityFormModel {

    private ActivityInfo activityInfo;

    private boolean isModified = false;

    public ActivityFormModel(){
        activityInfo = new ActivityInfo();
    }

    public ActivityFormModel(ActivityInfo info){
        activityInfo = info;
    }

    public void setName(String name) {
        isModified = true;
        activityInfo.setName(name);
    }

    public void setRules(String rules) {
        isModified = true;
        activityInfo.setRules(rules);
    }

    public void setStartTime(String startTime) {
        isModified = true;
        activityInfo.setStartTime(startTime);
    }

    public void setEndTime(String endTime) {
        isModified = true;
        activityInfo.setEndTime(endTime);
    }

    public void setGame(GameInfo game) {
        isModified = true;
        activityInfo.setGame(game);
    }

    public void addPrize(PrizeInfo prize) {
        if (activityInfo.getPrizes() == null)
            activityInfo.setPrizes(new ArrayList<PrizeInfo>());

        activityInfo.getPrizes().add(prize);
    }

    public String getName() {
        return activityInfo.getName();
    }

    public String getRules() {
        return activityInfo.getRules();
    }

    public String getStartTime() {
        return activityInfo.getStartTime();
    }

    public String getEndTime() {
        return activityInfo.getEndTime();
    }

    public GameInfo getGame() {
        return activityInfo.getGame();
    }

    public List<PrizeInfo> getPrizes() {
        if (activityInfo.getPrizes() == null)
            activityInfo.setPrizes(new ArrayList<PrizeInfo>());

        return activityInfo.getPrizes();
    }
}
