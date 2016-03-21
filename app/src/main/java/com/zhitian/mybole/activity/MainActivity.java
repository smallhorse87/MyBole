package com.zhitian.mybole.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.code.ManualVerifyActivity;
import com.zhitian.mybole.activity.myactivities.ActivityCreation;
import com.zhitian.mybole.activity.myactivities.MyActivitesActivity;
import com.zhitian.mybole.activity.statistics.TotalStatActivity;
import com.zhitian.mybole.base.BaseActivity;
import com.zhitian.mybole.ui.scanner.CaptureActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.rl_activity_creation)
    RelativeLayout rlActivityCreation;
    @Bind(R.id.rl_my_activities)
    RelativeLayout rlMyActivities;
    @Bind(R.id.rl_total_statistics)
    RelativeLayout rlTotalStatistics;
    @Bind(R.id.rl_code_verification)
    RelativeLayout rlCodeVerification;

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getNavTitle() {
        return "主菜单";
    }

    @Override
    protected void leftNavBtnHandle() {
        Toast.makeText(MainActivity.this, "点击了发布", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_activity_creation, R.id.rl_my_activities, R.id.rl_total_statistics, R.id.rl_code_verification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_activity_creation:
                Intent createIntent = new Intent(this, ActivityCreation.class);
                this.startActivity(createIntent);
                break;
            case R.id.rl_my_activities:
                Intent intent = new Intent(this, MyActivitesActivity.class);
                this.startActivity(intent);
                break;
            case R.id.rl_total_statistics:
            {
                Intent totalStatIntent = new Intent(this, TotalStatActivity.class);
                this.startActivity(totalStatIntent);
            }
                break;
            case R.id.rl_code_verification:
            {
                Intent codeIntent = new Intent(this, ManualVerifyActivity.class);
                this.startActivity(codeIntent);

//                Intent scanIntent = new Intent(this, CaptureActivity.class);
//                this.startActivity(scanIntent);
            }
                break;
        }
    }
}
