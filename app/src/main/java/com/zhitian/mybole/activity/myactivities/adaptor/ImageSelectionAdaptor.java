package com.zhitian.mybole.activity.myactivities.adaptor;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.Constants;
import com.zhitian.mybole.R;
import com.zhitian.mybole.entity.ImageInfo;
import com.zhitian.mybole.entity.ImageSetInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class ImageSelectionAdaptor extends RecyclerView.Adapter<ImageSelectionHolder>{

    static private final int VIEW_OF_SELECTED_IMAGE   = 0;
    static private final int VIEW_OF_ADD_IMAGE_BUTTON = 1;

    private List ids;
    private ImageSelectionHolder.ImageSelectionListener listener;
    /**
     * Adapter to make a connection between your document list and RecyclerView
     * @param ids
     * Your document list
     * */
    public ImageSelectionAdaptor(List ids, ImageSelectionHolder.ImageSelectionListener listener) {
        this.ids = ids;
        this.listener = listener;
    }

    @Override
    public ImageSelectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType)
        {
            case VIEW_OF_ADD_IMAGE_BUTTON:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_add_image_button, parent, false);
                break;

            case VIEW_OF_SELECTED_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_selected_image, parent, false);
                break;
        }

        ImageSelectionHolder viewHolder = new ImageSelectionHolder(view, listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageSelectionHolder holder, int position) {

        if(position >= ids.size())
            return;

        holder.setupContent((ImageSetInfo)ids.get(position));
    }

    @Override
    public int getItemCount() {
        if (ids.size() >= Constants.APPCFG_MAX_PICTURE_PER_PRIZE)
            return ids.size();
        else
            return ids.size() + 1;
    }

    @Override
    public int getItemViewType(int postion){
        if (postion == ids.size())
            return VIEW_OF_ADD_IMAGE_BUTTON;
        else
            return VIEW_OF_SELECTED_IMAGE;
    }

}
