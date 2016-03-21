package com.zhitian.mybole.activity.myactivities.adaptor;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhitian.mybole.R;
import com.zhitian.mybole.entity.ImageInfo;
import com.zhitian.mybole.entity.PrizeInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class GameAdaptor extends RecyclerView.Adapter<GameHolder> {

    private List<PrizeInfo> ids;

    /**
     * Adapter to make a connection between your document list and RecyclerView
     *
     * @param ids Your document list
     */
    public GameAdaptor(List<PrizeInfo> ids) {
        this.ids = ids;
    }

    @Override
    public GameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_game, parent, false);
        GameHolder viewHolder = new GameHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GameHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

}
