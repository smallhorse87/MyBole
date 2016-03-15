package com.zhitian.mybole.activity.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhitian.mybole.R;
import com.zhitian.mybole.api.BoleApi;
import com.zhitian.mybole.api.OperationResponseHandler;
import com.zhitian.mybole.entity.DetailedStatInfo;
import com.zhitian.mybole.entity.TotalStatInfo;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailedStatActivity extends AppCompatActivity {

    @Bind(R.id.tv_left_count)
    TextView tvLeftCount;
    @Bind(R.id.tv_right_count)
    TextView tvRightCount;
    @Bind(R.id.tv_activity_name)
    TextView tvActivityName;
    @Bind(R.id.tv_view_count_today)
    TextView tvViewCountToday;
    @Bind(R.id.tv_view_count_total)
    TextView tvViewCountTotal;
    @Bind(R.id.tv_forward_count_today)
    TextView tvForwardCountToday;
    @Bind(R.id.tv_forward_count_total)
    TextView tvForwardCountTotal;
    @Bind(R.id.scrollView2)
    ScrollView scrollView2;

    String activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_stat);
        ButterKnife.bind(this);

        Intent intent=getIntent();
        activityId = intent.getStringExtra("activityId");

        scrollView2.setVisibility(ScrollView.GONE);
    }

    @Override
    protected void onResume(){
        super.onResume();

        BoleApi.getDetailedStat(activityId, new OperationResponseHandler() {
            @Override
            public void onJsonSuccess(JSONObject retData) {
                DetailedStatInfo detailedInfo = retDetailedStat(retData);

                setupContent(detailedInfo);
                scrollView2.setVisibility(ScrollView.VISIBLE);
            }

            @Override
            public void onJsonFailure(int statusCode, String errMsg) {

            }
        });

    }

    void setupContent(DetailedStatInfo info){
        tvLeftCount.setText(info.getNewPlayer());

        tvRightCount.setText(info.getPlayerCnt());

        tvActivityName.setText("活动名称："+ info.getName());

        tvViewCountToday.setText(info.getTodayView());

        tvViewCountTotal.setText(info.getViewCnt());

        tvForwardCountToday.setText(info.getTodayForward());

        tvForwardCountTotal.setText(info.getForwardCnt());

    }

}
