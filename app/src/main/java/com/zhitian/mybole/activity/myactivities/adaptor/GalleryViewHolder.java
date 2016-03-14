package com.zhitian.mybole.activity.myactivities.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.zhitian.mybole.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class GalleryViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.iv_image)
    ImageView ivImage;

    public GalleryViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);

    }

}