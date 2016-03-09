package com.zhitian.mybole.entity;

import android.net.Uri;

/**
 * Created by chenxiaosong on 16/3/5.
 */
public class ImageSetInfo {
    private String imgId;

    private Uri uri;
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

    public void setUri(Uri uri) {
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

    public Uri getUri() {
        return this.uri;
    }
}
