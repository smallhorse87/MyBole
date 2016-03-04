package com.zhitian.mybole.activity.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.voyager.countdownbutton.CountDownButtonView;
import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.et_telphone)
    EditText etTelphone;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        bvTxtCaptcha.setCountDownListener(new CountDownButtonView.CountDownListener(){
            @Override
            public void onStart() {
                Log.d("stony", "开始计时");
            }

            @Override
            public void onFinished() {
                Log.d("stony", "结束计时");
            }
        });
    }

    @OnClick({R.id.et_telphone, R.id.bv_txt_captcha, R.id.et_captcha, R.id.tv_voice_captcha, R.id.btn_login, R.id.tv_policy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_telphone:
                break;
            case R.id.bv_txt_captcha:
                bvTxtCaptcha.startCountDown();
                break;
            case R.id.et_captcha:
                break;
            case R.id.tv_voice_captcha:
                break;
            case R.id.btn_login:
                break;
            case R.id.tv_policy:
                break;
        }
    }
}
