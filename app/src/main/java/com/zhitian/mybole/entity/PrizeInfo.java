package com.zhitian.mybole.entity;

import java.util.List;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class PrizeInfo {
    private String prizeLevel;
    private String name;
    private String number;
    private String endTime;
    /**
     * imgId : 1121
     * thumbnailImg : {"width":"180","height":"180","url":"http://m.bodimall.com/Uploads/Picture/2016-02-16/thumbnail_56c2bbd3d9669.png"}
     * middleImg : {"width":"440","height":"441","url":"http://m.bodimall.com/Uploads/Picture/2016-02-16/middle_56c2bbd3d9669.png"}
     * largeImg : {"width":"800","height":"800","url":"http://m.bodimall.com/Uploads/Picture/2016-02-16/large_56c2bbd3d9669.png"}
     * originalImg : {"width":"800","height":"800","url":"http://m.bodimall.com/Uploads/Picture/2016-02-16/56c2bbd3d9669.png"}
     */

    private List<ImageSetInfo> imgs;

    public void setPrizeLevel(String prizeLevel) {
        this.prizeLevel = prizeLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setImgs(List<ImageSetInfo> imgs) {
        this.imgs = imgs;
    }

    public String getPrizeLevel() {
        return prizeLevel;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEndTime() {
        return endTime;
    }

    public List<ImageSetInfo> getImgs() {
        return imgs;
    }
}
