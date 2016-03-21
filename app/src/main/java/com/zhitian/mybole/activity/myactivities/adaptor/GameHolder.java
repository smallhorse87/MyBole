package com.zhitian.mybole.activity.myactivities.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhitian.mybole.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class GameHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.iv_game_image)
    SimpleDraweeView ivGameImage;
    @Bind(R.id.tv_game_name)
    TextView tvGameName;
    @Bind(R.id.btn_select_game)
    Button btnSelectGame;

    GameHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }

}