package com.zhitian.mybole.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.zhitian.mybole.R;
import com.zhitian.mybole.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getNavTitle(){
        return "主菜单";
    }

    @Override
    protected void leftNavBtnHandle(){
        Toast.makeText(MainActivity.this, "点击了发布", Toast.LENGTH_SHORT).show();
    }

}
