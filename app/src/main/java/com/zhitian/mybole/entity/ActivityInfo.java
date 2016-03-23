package com.zhitian.mybole.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class ActivityInfo {

    private String activityId;
    private String name;
    private String rules;
    private String startTime;
    private String endTime;
    private String playerCnt;
    private String shareLink;
    private String status;
    private String timeStatus;

    private GameInfo game;

    private List<PrizeInfo> prizes;

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setPlayerCnt(String playerCnt) {
        this.playerCnt = playerCnt;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimeStatus(String timeStatus) {
        this.timeStatus = timeStatus;
    }

    public void setGame(GameInfo game) {
        this.game = game;
    }

    public void setPrizes(List<PrizeInfo> prizes) {
        this.prizes = prizes;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getName() {
        return name;
    }

    public String getRules() {
        return rules;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getPlayerCnt() {
        return playerCnt;
    }

    public String getShareLink() {
        return shareLink;
    }

    public String getStatus() {
        return status;
    }

    public String getTimeStatusStr() {
        int timeStatus = Integer.parseInt(this.timeStatus);

        switch(timeStatus){
            case 0:
                return "未开始";

            case 1:
                return "已开始";

            case 2:
                return "已结束";

            default:
                return "未知状态";
        }
    }

    public String getTimeStatus() {
        return timeStatus;
    }

    public GameInfo getGame() {
        return game;
    }

    public List<PrizeInfo> getPrizes() {

        if (prizes != null)
            Collections.sort(prizes);

        return prizes;
    }

}
