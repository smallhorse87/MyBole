package com.zhitian.mybole.entity;

import com.google.gson.Gson;
import com.zhitian.mybole.activity.myactivities.PrizeActivity;

import java.util.List;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class PrizeInfo implements Cloneable, Comparable<PrizeInfo>{
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

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Gson gson = new Gson();
        String srcStr = gson.toJson(this);

        PrizeInfo tgtInfo = gson.fromJson(srcStr, PrizeInfo.class);

        return tgtInfo;
    }

    public int compareTo(PrizeInfo arg0) {
        int curLevel = Integer.parseInt(this.getPrizeLevel());
        int objLevel = Integer.parseInt(arg0.getPrizeLevel());

        return curLevel - objLevel;
    }

    public String checkSanity(){
        if(prizeLevel == null || prizeLevel.length() == 0)
            return "请选择奖品等级";

        if(name == null || name.length() == 0)
            return "请填写奖品名称";

        if(number == null || number.length() == 0)
            return "请填写奖励人数";

        if(endTime == null || endTime.length() == 0)
            return "请选择奖品有效期";

        if(imgs == null || imgs.size() == 0)
            return "请至少添加一张奖品图片";

        return null;
    }

    public boolean equals(PrizeInfo p)
    {
        if (!prizeLevel.equals(p.getPrizeLevel()))
            return false;

        if (!name.equals(p.getName()))
            return false;

        if (!number.equals(p.getNumber()))
            return false;

        if (!endTime.equals(p.getEndTime()))
            return false;

        if (!imgs.equals(p.imgs))
            return false;

        return true;
    }

    //stony hasContent
}
