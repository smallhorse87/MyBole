package com.zhitian.mybole.entity;

/**
 * Created by chenxiaosong on 16/3/5.
 */
public class ImageInfo {
    private String width;
    private String height;
    private String url;

    public void setWidth(String width) {
        this.width = width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public String getUrl() {
        return url;
    }

    public boolean equals(ImageInfo p){

        if (!width.equals(p.getWidth()))
            return false;

        if (!height.equals(p.getHeight()))
            return false;

        if (!url.equals(p.getUrl()))
            return false;

        return true;
    }
}
