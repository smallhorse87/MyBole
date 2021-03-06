package com.zhitian.mybole.entity;

import android.net.Uri;

import java.util.List;

public class MerchantInfo {

    private ImageSetInfo avatar;

    private String name;
    private String category;
    private String address;
    private String tel;
    private String wechat;

    private ImageSetInfo wechatQrcode;
    private String userId;
    private float longtitude;
    private float latitude;
    private List<String> regionIds;
    private List<String> regionName;

    private String gsid;

    public void setAvatar(ImageSetInfo avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGsid(String gsid) {
        this.gsid = gsid;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public void setWechatQrcode(ImageSetInfo wechatQrcode) {
        this.wechatQrcode = wechatQrcode;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLongtitude(float longtitude) {
        this.longtitude = longtitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setRegionIds(List<String> regionIds) {
        this.regionIds = regionIds;
    }

    public void setRegionName(List<String> regionName) {
        this.regionName = regionName;
    }

    public ImageSetInfo getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getGsid() {
        return gsid;
    }

    public String getCategory() {
        return category;
    }

    public String getAddress() {
        return address;
    }

    public String getTel() {
        return tel;
    }

    public String getWechat() {
        return wechat;
    }

    public ImageSetInfo getWechatQrcode() {
        return wechatQrcode;
    }

    public String getUserId() {
        return userId;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public List<String> getRegionIds() {
        return regionIds;
    }

    public String getDistrictid() {
        List<String> regionIds = getRegionIds();

        return regionIds.get(regionIds.size() - 1);
    }

    public List<String> getRegionName() {
        return regionName;
    }

    public String getFullRegionName(){
        String fullName = "";

        for (String subRegion:regionName) {
          fullName = fullName + subRegion;
        }

        return fullName;
    }

    public Uri getReaLogoUri(){
        return getAvatar().getRealThumbnailUri();
    }

    public Uri getRealWechatQrcodeUri(){
        return getWechatQrcode().getRealThumbnailUri();
    }
}
