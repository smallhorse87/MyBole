package com.zhitian.mybole.activity.myactivities.adaptor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.shareactivity.PlanlistActivity;
import com.zhitian.mybole.entity.ActivityInfo;
import com.zhitian.mybole.entity.ImageSetInfo;
import com.zhitian.mybole.entity.PrizeInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public final class ItemViewHolder {
    @Bind(R.id.tv_activity_name)
    TextView tvActivityName;
    @Bind(R.id.tv_activity_state)
    TextView tvActivityState;
    @Bind(R.id.tv_game_name)
    TextView tvGameName;
    @Bind(R.id.tv_player_count)
    TextView tvPlayerCount;
    @Bind(R.id.btn_share)
    Button btnShare;
    @Bind(R.id.btn_statistic)
    Button btnStatistic;
    @Bind(R.id.btn_ranking)
    Button btnRanking;
    @Bind(R.id.rv_prize_images)
    RecyclerView rvPrizeImages;

    private LinearLayoutManager mLayoutManager;
    private GalleryAdaptor  mAdapter;
    private ArrayList       myDataset;

    private ActivityInfo    info;

    public ItemViewHolder(View view, final Context context) {
        ButterKnife.bind(this, view);

        rvPrizeImages.setOverScrollMode(View.OVER_SCROLL_NEVER);

        rvPrizeImages.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPrizeImages.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        myDataset = new ArrayList();
        mAdapter  = new GalleryAdaptor(myDataset);
        rvPrizeImages.setAdapter(mAdapter);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlanlistActivity.class);

                intent.putExtra("activityId", info.getActivityId());

                context.startActivity(intent);
            }
        });

    }

    public void setupContent(ActivityInfo info){
        this.info = info;

        tvActivityName.setText(info.getName());
        tvActivityState.setText(info.getTimeStatusStr());
        tvGameName.setText(info.getGame().getName());
        tvPlayerCount.setText(info.getPlayerCnt() + "人参与");

        buildUri(info.getPrizes());
        mAdapter.notifyDataSetChanged();
    }

    private void buildUri(List<PrizeInfo> prizes) {
        myDataset.clear();

        for (PrizeInfo prizeItem : prizes) {
            for (ImageSetInfo imageItem : prizeItem.getImgs()) {
                myDataset.add(imageItem.getThumbnailImg());
            }
        }

    }
}