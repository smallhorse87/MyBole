package com.zhitian.mybole.entity;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class PageInfo {
    /**
     * totalPage : 3
     * size : 15
     * currentPage : 1
     * retCnt : 15
     * lastTime : 1457953713
     */

    private String totalPage;
    private String size;
    private String currentPage;
    private int retCnt;
    private String lastTime;

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public void setRetCnt(int retCnt) {
        this.retCnt = retCnt;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public String getSize() {
        return size;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public int getRetCnt() {
        return retCnt;
    }

    public String getLastTime() {
        return lastTime;
    }
}
