package com.zhitian.mybole.entity;

import java.util.List;

/**
 * Created by chenxiaosong on 16/3/15.
 */
public class TotalStatInfo {

    private int newPlayer;
    private String playerCnt;

    private List<StatInfo> activityStatistics;

    public void setNewPlayer(int newPlayer) {
        this.newPlayer = newPlayer;
    }

    public void setPlayerCnt(String playerCnt) {
        this.playerCnt = playerCnt;
    }

    public void setActivityStatistics(List<StatInfo> activityStatistics) {
        this.activityStatistics = activityStatistics;
    }

    public int getNewPlayer() {
        return newPlayer;
    }

    public String getPlayerCnt() {
        return playerCnt;
    }

    public List<StatInfo> getActivityStatistics() {
        return activityStatistics;
    }

}
