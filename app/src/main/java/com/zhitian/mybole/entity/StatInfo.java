package com.zhitian.mybole.entity;

/**
 * Created by chenxiaosong on 16/3/15.
 */
public class StatInfo {
    private String activityId;
    private String name;
    private String playerCnt;
    private String viewCnt;
    private String forwardCnt;

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayerCnt(String playerCnt) {
        this.playerCnt = playerCnt;
    }

    public void setViewCnt(String viewCnt) {
        this.viewCnt = viewCnt;
    }

    public void setForwardCnt(String forwardCnt) {
        this.forwardCnt = forwardCnt;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getName() {
        return name;
    }

    public String getPlayerCnt() {
        return playerCnt;
    }

    public String getViewCnt() {
        return viewCnt;
    }

    public String getForwardCnt() {
        return forwardCnt;
    }
}