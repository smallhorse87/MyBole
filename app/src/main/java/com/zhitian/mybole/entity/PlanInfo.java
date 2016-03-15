package com.zhitian.mybole.entity;

import java.util.List;

/**
 * Created by chenxiaosong on 16/3/15.
 */
public class PlanInfo {

    private String planId;
    private String title;
    private String content;

    private List<ImageSetInfo> imgs;

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImgs(List<ImageSetInfo> imgs) {
        this.imgs = imgs;
    }

    public String getPlanId() {
        return planId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<ImageSetInfo> getImgs() {
        return imgs;
    }

}
