package com.zhitian.mybole.activity.myactivities.adaptor;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zhitian.mybole.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class GalleryAdaptor extends RecyclerView.Adapter<GalleryViewHolder> {

    private List ids;

    /**
     * Adapter to make a connection between your document list and RecyclerView
     * @param ids
     * Your document list
     * */
    public GalleryAdaptor(ArrayList ids) {
        this.ids = ids;
    }

    /**
     * @param integer
     * Your mipmap resources id to add at the beginning of the list
     * */
    public void addYourFirstItemInTheList(Integer integer){
        ids.add(0,integer);
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_image, parent, false);
        GalleryViewHolder viewHolder = new GalleryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        holder.ivImage.setImageResource((Integer) ids.get(position));
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

}
