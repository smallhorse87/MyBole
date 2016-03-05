package com.zhitian.mybole.activity.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.voyager.countdownbutton.CountDownButtonView;
import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.R;
import com.zhitian.mybole.api.BoleApi;
import com.zhitian.mybole.api.ApiResult;
import com.zhitian.mybole.base.BaseActivity;
import com.zhitian.mybole.entity.MerchantInfo;
import com.zhitian.mybole.ui.ClearEditText;
import com.zhitian.mybole.utils.StringUtils;
import com.zhitian.mybole.utils.TDevice;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_telphone)
    ClearEditText etTelphone;
    @Bind(R.id.bv_txt_captcha)
    CountDownButtonView bvTxtCaptcha;
    @Bind(R.id.et_captcha)
    EditText etCaptcha;
    @Bind(R.id.tv_voice_captcha)
    TextView tvVoiceCaptcha;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_policy)
    TextView tvPolicy;

    private  CountDownButtonView.CountDownListener countDownListener;
    private  JsonHttpResponseHandler txtCaptchaHandler;
    private  JsonHttpResponseHandler voiceCaptchaHandler;
    private  JsonHttpResponseHandler LoginHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected String getNavTitle(){
        return "主菜单";
    }

    @Override
    protected void leftNavBtnHandle(){
        AppContext.showToast("点击了发布");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        btnLogin.setEnabled(false);
        tvVoiceCaptcha.setVisibility(View.GONE);

        etTelphone.setText(AppContext.getTelnum());

    }

    @Override
    protected void initListeners(Bundle savedInstanceState){
        txtCaptchaHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Gson gson = new Gson();
                ApiResult result = gson.fromJson(response.toString(), ApiResult.class);
                Log.d("stony", result.getMsg());

                if (result.getRet() == 0) {
                    try {
                        JSONObject retData = response.getJSONObject("data");
                        AppContext.showToast(retData.getString("captcha"));
                    } catch (Exception e) {

                    }
                } else {
                    AppContext.showToast(result.getMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AppContext.showToast("请求失败");
            }
        };

        voiceCaptchaHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Gson gson = new Gson();
                ApiResult result = gson.fromJson(response.toString(), ApiResult.class);
                Log.d("stony", result.getMsg());

                if (result.getRet() == 0) {
                    try {
                        JSONObject retData = response.getJSONObject("data");
                        AppContext.showToast(retData.getString("captcha"));
                    } catch (Exception e) {

                    }
                } else {
                    AppContext.showToast(result.getMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AppContext.showToast("请求失败");
            }
        };

        countDownListener = new CountDownButtonView.CountDownListener() {
            @Override
            public void onFinished() {

            }

            @Override
            public void onTick(int lefttime) {
                if (bvTxtCaptcha.time - bvTxtCaptcha.leftTime > 10)
                    tvVoiceCaptcha.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStart() {
                btnLogin.setEnabled(true);

                BoleApi.getTxtPasscodeForLogin(etTelphone.getText().toString(), txtCaptchaHandler);
            }
        };

        LoginHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Gson gson = new Gson();
                ApiResult result = gson.fromJson(response.toString(), ApiResult.class);
                Log.d("stony", result.getMsg());

                if (result.getRet()==0){
                    try {
                        JSONObject merchantInfoJson = response.getJSONObject("data").getJSONObject("merchantInfo");
                        String     gsid             = response.getJSONObject("data").getString("gsid");

                        MerchantInfo merchantInfo   = gson.fromJson(merchantInfoJson.toString(), MerchantInfo.class);

                        AppContext.saveLoginInfo(merchantInfo.getUserId(), gsid, etTelphone.getText().toString());

                        Log.d("stony", merchantInfo.getAddress());

                        hideWaitDialog();
                        AppContext.showToast("登录成功");

                    } catch (Exception e){

                    }
                } else {
                    AppContext.showToast(result.getMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideWaitDialog();
                AppContext.showToast("请求失败");
            }
        };
    }

    //事件处理
    @OnClick({R.id.et_telphone, R.id.bv_txt_captcha, R.id.et_captcha, R.id.tv_voice_captcha, R.id.btn_login, R.id.tv_policy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_telphone:
                break;
            case R.id.bv_txt_captcha:
                captchaBtnPressed();
                break;
            case R.id.et_captcha:
                break;
            case R.id.tv_voice_captcha:
                voiceCaptchaPressed();
                break;
            case R.id.btn_login:
                loginBtnPressed();
                break;
            case R.id.tv_policy:
                break;
        }
    }

    void captchaBtnPressed(){

        if (!StringUtils.isValidTel(etTelphone.getText().toString())) {
            AppContext.showToast("手机格式不对");
            return;
        }

        if(!TDevice.hasInternet()){
            AppContext.showToast("没有网络");
            return;
        }

        bvTxtCaptcha.setCountDownListener(countDownListener);
        bvTxtCaptcha.startCountDown();
    }

    void voiceCaptchaPressed() {

        if (!StringUtils.isValidTel(etTelphone.getText().toString()))
        {
            AppContext.showToast("手机格式不对");
            return;
        }

        AppContext.showToast("发送请求");

        BoleApi.getVoicePasscodeForLogin(etTelphone.getText().toString(), voiceCaptchaHandler);

    }

    void loginBtnPressed() {

        if (!StringUtils.isValidTel(etTelphone.getText().toString()))
        {
            AppContext.showToast("手机格式不对");
            return;
        }

        if (!StringUtils.isValidPasscode(etCaptcha.getText().toString()))
        {
            AppContext.showToast("验证码格式不对");
            return;
        }

        showWaitDialog("发送请求");

        BoleApi.loginWithCaptcha(etTelphone.getText().toString(), etCaptcha.getText().toString(),LoginHandler);

    }

}
