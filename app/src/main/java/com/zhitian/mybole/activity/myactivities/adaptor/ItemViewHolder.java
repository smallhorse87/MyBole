package com.zhitian.mybole.activity.myactivities.adaptor;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.R;

import java.util.ArrayList;

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

    public ItemViewHolder(View view, Context context) {
        ButterKnife.bind(this, view);

        myDataset=buildMockData();
        rvPrizeImages.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvPrizeImages.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new GalleryAdaptor(myDataset);
        mAdapter.addYourFirstItemInTheList(new Integer(R.mipmap.relativity4));
        rvPrizeImages.setAdapter(mAdapter);

    }

    private ArrayList buildMockData() {
        ArrayList myDataset = new ArrayList();
        myDataset.add(new Integer(R.mipmap.relativity1));
        myDataset.add(new Integer(R.mipmap.relativity2));
        myDataset.add(new Integer(R.mipmap.relativity3));
        myDataset.add(new Integer(R.mipmap.relativity4));
        myDataset.add(new Integer(R.mipmap.relativity5));
        myDataset.add(new Integer(R.mipmap.relativity6));
        return myDataset;
    }
}