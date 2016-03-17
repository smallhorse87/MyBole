package com.zhitian.mybole.activity.code;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.zhitian.mybole.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManualVerifyActivity extends AppCompatActivity {

    @Bind(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_verify);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onClick() {
        Intent intent = new Intent(this, VerifyResultActivity.class);
        this.startActivity(intent);
    }
}
