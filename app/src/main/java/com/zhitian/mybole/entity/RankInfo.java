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

}
