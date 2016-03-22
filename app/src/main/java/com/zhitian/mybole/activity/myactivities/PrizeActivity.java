package com.zhitian.mybole.activity.myactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhitian.mybole.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrizeActivity extends AppCompatActivity {

    @Bind(R.id.tv_prize_level)
    TextView tvPrizeLevel;
    @Bind(R.id.ll_prize_level)
    LinearLayout llPrizeLevel;
    @Bind(R.id.et_prize_name)
    EditText etPrizeName;
    @Bind(R.id.et_count)
    EditText etCount;
    @Bind(R.id.tv_prize_expried_time)
    TextView tvPrizeExpriedTime;
    @Bind(R.id.ll_prize_expried_time)
    LinearLayout llPrizeExpriedTime;
    @Bind(R.id.rv_prize_images)
    RecyclerView rvPrizeImages;
    @Bind(R.id.tv_template)
    TextView tvTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_prize_level, R.id.ll_prize_expried_time, R.id.tv_template})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_prize_level:
                break;
            case R.id.ll_prize_expried_time:
                break;
            case R.id.tv_template:
                break;
        }
    }
}
