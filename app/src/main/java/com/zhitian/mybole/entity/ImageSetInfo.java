package com.zhitian.mybole.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenxiaosong on 16/3/5.
 */
public class ImageSetInfo implements Parcelable {
    private String imgId;

    //存放选择的本地图片
    private String uri;
    /**
     * width : 180
     * height : 180
     * url : http://m.bodimall.com/Uploads/Picture/2016-01-27/thumbnail_56a829cb1c37e.png
     */

    private ImageInfo thumbnailImg;
    /**
     * width : 440
     * height : 440
     * url : http://m.bodimall.com/Uploads/Picture/2016-01-27/middle_56a829cb1c37e.png
     */

    private ImageInfo middleImg;
    /**
     * width : 669
     * height : 668
     * url : http://m.bodimall.com/Uploads/Picture/2016-01-27/large_56a829cb1c37e.png
     */

    private ImageInfo largeImg;
    /**
     * width : 669
     * height : 668
     * url : http://m.bodimall.com/Uploads/Picture/2016-01-27/56a829cb1c37e.png
     */

    private ImageInfo originalImg;

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public void setThumbnailImg(ImageInfo thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }

    public void setMiddleImg(ImageInfo middleImg) {
        this.middleImg = middleImg;
    }

    public void setLargeImg(ImageInfo largeImg) {
        this.largeImg = largeImg;
    }

    public void setOriginalImg(ImageInfo originalImg) {
        this.originalImg = originalImg;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImgId() {
        return imgId;
    }

    public ImageInfo getThumbnailImg() {
        return thumbnailImg;
    }

    public ImageInfo getMiddleImg() {
        return middleImg;
    }

    public ImageInfo getLargeImg() {
        return largeImg;
    }

    public ImageInfo getOriginalImg() {
        return originalImg;
    }

    public String getUri() {
        return this.uri;
    }

    public boolean equals(ImageSetInfo p){
        if (!imgId.equals(p.getImgId()))
            return false;

        if (!this.thumbnailImg.equals(p.thumbnailImg))
            return false;

        if (!this.middleImg.equals(p.middleImg))
            return false;

        if (!this.largeImg.equals(p.largeImg))
            return false;

        if (!this.originalImg.equals(p.originalImg))
            return false;

        return true;
    }

    public boolean pendingForUpload() {
        if(imgId == null && uri != null)
            return true;
        else
            return false;
    }

    public Uri getRealThumbnailUri(){
        if ( getUri() != null)
            return Uri.parse(getUri());
        else if (getThumbnailImg().getUrl() != null)
            return Uri.parse(getThumbnailImg().getUrl());
        else
            return null;
    }

    public Uri getRealLargeUri(){
        if ( getUri() != null)
            return Uri.parse(getUri());
        else if (getThumbnailImg().getUrl() != null)
            return Uri.parse(getThumbnailImg().getUrl());
        else
            return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imgId);
        dest.writeString(this.uri);
        dest.writeParcelable(this.thumbnailImg, flags);
        dest.writeParcelable(this.middleImg, flags);
        dest.writeParcelable(this.largeImg, flags);
        dest.writeParcelable(this.originalImg, flags);
    }

    public ImageSetInfo() {
    }

    protected ImageSetInfo(Parcel in) {
        this.imgId = in.readString();
        this.uri = in.readString();
        this.thumbnailImg = in.readParcelable(ImageInfo.class.getClassLoader());
        this.middleImg = in.readParcelable(ImageInfo.class.getClassLoader());
        this.largeImg = in.readParcelable(ImageInfo.class.getClassLoader());
        this.originalImg = in.readParcelable(ImageInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<ImageSetInfo> CREATOR = new Parcelable.Creator<ImageSetInfo>() {
        @Override
        public ImageSetInfo createFromParcel(Parcel source) {
            return new ImageSetInfo(source);
        }

        @Override
        public ImageSetInfo[] newArray(int size) {
            return new ImageSetInfo[size];
        }
    };
}
