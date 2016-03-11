package com.zhitian.mybole.model;


import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.api.ApiResult;
import com.zhitian.mybole.api.BoleApi;
import com.zhitian.mybole.base.BaseActivity;
import com.zhitian.mybole.entity.ImageInfo;
import com.zhitian.mybole.entity.ImageSetInfo;
import com.zhitian.mybole.entity.MerchantInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by chenxiaosong on 16/3/9.
 */
public class MerchantFormModel {

    protected MerchantInfo mMerchant;
    protected BaseActivity mBaseActivity;
    private   Boolean      isModified = false;

    private ImageSetInfo uploadingImage;

    private JsonHttpResponseHandler  uploadImageHandler;
    private  JsonHttpResponseHandler submitMerchantFormHandler;

    public MerchantFormModel(MerchantInfo info, BaseActivity baseActivity){
        mMerchant = info;
        mBaseActivity = baseActivity;

        uploadImageHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Gson gson = new Gson();
                ApiResult result = gson.fromJson(response.toString(), ApiResult.class);

                if (result.getRet() == 0) {
                    try {
                        JSONObject imgSetAttribData = response.getJSONObject("data");
                        ImageSetInfo info = gson.fromJson(imgSetAttribData.toString(), ImageSetInfo.class);

                        uploadingImage.setImgId(info.getImgId());

                        submitForm();

                    } catch (Exception e) {
                        mBaseActivity.hideWaitDialog();
                    }
                } else {
                    AppContext.showToast(result.getMsg());
                    mBaseActivity.hideWaitDialog();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AppContext.showToast("请求失败");
                mBaseActivity.hideWaitDialog();
            }
        };

        submitMerchantFormHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Gson gson = new Gson();
                ApiResult result = gson.fromJson(response.toString(), ApiResult.class);

                if (result.getRet() == 0) {
                    try {
                        JSONObject merchantInfoData = response.getJSONObject("data");
                        MerchantInfo info = gson.fromJson(merchantInfoData.toString(), MerchantInfo.class);
                        Log.i("stony", info.getAddress());

                        mBaseActivity.hideWaitDialog();

                    } catch (Exception e) {
                        Log.e("err", e.toString());
                    }
                } else {
                    AppContext.showToast(result.getMsg());
                    mBaseActivity.hideWaitDialog();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AppContext.showToast("请求失败");
                mBaseActivity.hideWaitDialog();
            }
        };
    }

    public void submitForm() {

        mMerchant.setWechat("ichuangyebang"); //stony chen

        String errMsg = checkBeforeSubmit();

        if (errMsg != null)
            return;

        uploadingImage = getImageForUpload();

        mBaseActivity.showWaitDialog();

        if (uploadingImage != null)
            BoleApi.updateImage(uploadingImage.getUri(), uploadImageHandler);
        else
            BoleApi.submitMerchantInfo(mMerchant, submitMerchantFormHandler);
    }

    protected String checkBeforeSubmit() {

        if(!hasAvatar())
        {
            return "请完善产品信息";
        }

        if (mMerchant.getName() == null || mMerchant.getName().length() == 0 || mMerchant.getName().length() > 40)
        {
            return "请完善产品信息";
        }

        if (mMerchant.getCategory() == null || mMerchant.getName().length() == 0)
        {
            return "请完善产品信息";
        }

        if (mMerchant.getRegionName() == null || mMerchant.getRegionName().size() == 0)
        {
            return "请完善产品信息";
        }

        if (mMerchant.getAddress() == null || mMerchant.getAddress().length() == 0 || mMerchant.getAddress().length() > 40)
        {
            return "请完善产品信息";
        }

        if (mMerchant.getTel() == null || mMerchant.getTel().length() == 0 || mMerchant.getTel().length() > 20)
        {
            return "请完善产品信息";
        }

        if (mMerchant.getWechat() == null || mMerchant.getWechat().length() == 0 || mMerchant.getWechat().length() > 40)
        {
            return "请完善产品信息";
        }

        if (!hasWechatQrcode())
        {
            return "请完善产品信息";
        }

        return null;
    }

    public void setAvatar(Uri uri){
        mMerchant.setAvatar(new ImageSetInfo());

        mMerchant.getAvatar().setUri(uri);

        isModified = true;
    }

    public void setName(String name){
        mMerchant.setName(name);
        isModified = true;
    }

    public void setCategory(String category){
        mMerchant.setCategory(category);
        isModified = true;
    }

    public void setRegionIds(String proviceId, String cityId, String districtId){

        List<String> regionIds = new ArrayList<String>();

        regionIds.add(proviceId);
        regionIds.add(cityId);
        regionIds.add(districtId);

        mMerchant.setRegionIds(regionIds);
        isModified = true;
    }

    public void setAddress (String address){
        mMerchant.setAddress(address);
        isModified = true;
    }

    public void setTel (String tel){
        mMerchant.setTel(tel);
        isModified = true;
    }

    public void setWechat (String wechat){
        mMerchant.setWechat(wechat);
        isModified = true;
    }

    public void setWechatQr (Uri uri){
        if(mMerchant.getWechatQrcode()==null)
        {
            mMerchant.setWechatQrcode(new ImageSetInfo());
        }

        mMerchant.getWechatQrcode().setUri(uri);

        isModified = true;
    }

    private boolean hasWechatQrcode(){
        return hasImageInfo(mMerchant.getWechatQrcode());
    }

    private boolean hasAvatar(){
        return hasImageInfo(mMerchant.getAvatar());
    }

    private boolean hasImageInfo(ImageSetInfo info){
        if (info ==null)  return false;

        if (info.getImgId() == null && info.getUri() == null) return false;

        return true;
    }

    private ImageSetInfo getImageForUpload(){
        //检查是否头像需要上传
        if (mMerchant.getAvatar().pendingForUpload())
            return mMerchant.getAvatar();

        //检查是否二维码需要上传
        if (mMerchant.getWechatQrcode().pendingForUpload())
            return mMerchant.getWechatQrcode();

        return null;
    }

}
