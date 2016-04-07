package com.zhitian.mybole.activity.myactivities.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhitian.mybole.R;
import com.zhitian.mybole.entity.ImageSetInfo;
import com.zhitian.mybole.entity.PrizeInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class ImageSelectionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ImageView ivDeleteImage;
    private SimpleDraweeView ivImage;
    private ImageView ivAddImage;

    private  ImageSetInfo info;
    private  ImageSelectionListener listener;

    public ImageSelectionHolder(View view, ImageSelectionListener listener) {
        super(view);

        ivDeleteImage = (ImageView)view.findViewById(R.id.iv_delete_image);
        ivImage       = (SimpleDraweeView)view.findViewById(R.id.iv_image);
        ivAddImage    = (ImageView)view.findViewById(R.id.iv_add_image);

        if(ivDeleteImage != null) ivDeleteImage.setOnClickListener(this);
        if(ivImage != null) ivImage.setOnClickListener(this);
        if(ivAddImage != null) ivAddImage.setOnClickListener(this);

        this.listener = listener;
    }

    void setupContent(ImageSetInfo info){
        this.info = info;

        if(ivImage != null)
            ivImage.setImageURI(info.getRealThumbnailUri());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_delete_image:
                if (info != null)
                    this.listener.deleteImage(info);
                break;

            case R.id.iv_image:
                if (info != null)
                    this.listener.navImage(info);
                break;

            case R.id.iv_add_image:
                this.listener.addImage();
                break;

            default:
                break;
        }
    }

    public interface ImageSelectionListener {
        public void addImage();
        public void deleteImage(ImageSetInfo info);
        public void navImage(ImageSetInfo info);
    }
}