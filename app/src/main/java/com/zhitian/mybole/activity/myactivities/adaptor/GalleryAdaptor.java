package com.zhitian.mybole.activity.myactivities.adaptor;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhitian.mybole.R;
import com.zhitian.mybole.entity.ImageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class GalleryAdaptor extends RecyclerView.Adapter<GalleryHolder> {

    private List ids;

    /**
     * Adapter to make a connection between your document list and RecyclerView
     * @param ids
     * Your document list
     * */
    public GalleryAdaptor(ArrayList ids) {
        this.ids = ids;
    }

    @Override
    public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_image, parent, false);
        GalleryHolder viewHolder = new GalleryHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GalleryHolder holder, int position) {
        ImageInfo  imageInfo= (ImageInfo)ids.get(position);
        Uri    uri = Uri.parse(imageInfo.getUrl());

        holder.ivImage.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

}
