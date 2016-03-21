package com.zhitian.mybole.activity.myactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.myactivities.adaptor.GameAdaptor;
import com.zhitian.mybole.activity.myactivities.adaptor.PrizesAdaptor;
import com.zhitian.mybole.entity.PrizeInfo;
import com.zhitian.mybole.ui.NestedListview;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityCreation extends AppCompatActivity {

    @Bind(R.id.et_activity_name)
    EditText etActivityName;
    @Bind(R.id.tv_start_time)
    TextView tvStartTime;
    @Bind(R.id.ll_start_time)
    LinearLayout llStartTime;
    @Bind(R.id.tv_end_time)
    TextView tvEndTime;
    @Bind(R.id.ll_end_time)
    LinearLayout llEndTime;
    @Bind(R.id.et_rule)
    EditText etRule;
    @Bind(R.id.tv_remaining_count)
    TextView tvRemainingCount;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.ll_add_prize)
    LinearLayout llAddPrize;
    @Bind(R.id.lv_prizes)
    NestedListview lvPrizes;
    @Bind(R.id.btn_preview)
    Button btnPreview;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.rv_games)
    RecyclerView rvGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_creation);
        ButterKnife.bind(this);

        lvPrizes.setAdapter(new PrizesAdaptor(stubPrize()));


        rvGames.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvGames.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvGames.setLayoutManager(mLayoutManager);

        rvGames.setAdapter(new GameAdaptor(stubPrize()));
    }

    @OnClick({R.id.ll_start_time, R.id.ll_end_time, R.id.ll_add_prize, R.id.btn_preview, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_start_time:
                break;
            case R.id.ll_end_time:
                break;
            case R.id.ll_add_prize:
                break;
            case R.id.btn_preview:
                break;
            case R.id.btn_submit:
                break;
        }
    }

    List<PrizeInfo> stubPrize() {

        ArrayList<PrizeInfo> infos = new ArrayList<PrizeInfo>();

        infos.add(new PrizeInfo());
        infos.add(new PrizeInfo());
        infos.add(new PrizeInfo());
        infos.add(new PrizeInfo());
        infos.add(new PrizeInfo());

        return infos;
    }
}
