package com.zhitian.mybole.activity.myactivities.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhitian.mybole.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class ImageSelectionHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.iv_image)
    SimpleDraweeView ivImage;

    public ImageSelectionHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);

    }

}