package com.zhitian.mybole.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenxiaosong on 16/3/5.
 */
public class ImageInfo implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.width);
        dest.writeString(this.height);
        dest.writeString(this.url);
    }

    public ImageInfo() {
    }

    protected ImageInfo(Parcel in) {
        this.width = in.readString();
        this.height = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<ImageInfo> CREATOR = new Parcelable.Creator<ImageInfo>() {
        @Override
        public ImageInfo createFromParcel(Parcel source) {
            return new ImageInfo(source);
        }

        @Override
        public ImageInfo[] newArray(int size) {
            return new ImageInfo[size];
        }
    };
}
