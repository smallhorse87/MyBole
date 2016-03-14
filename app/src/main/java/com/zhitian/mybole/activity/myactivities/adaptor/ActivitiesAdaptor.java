package com.zhitian.mybole.activity.myactivities.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhitian.mybole.R;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class ActivitiesAdaptor extends BaseAdapter {

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return "10";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemViewHolder vh = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_activity, null, false);

            vh = new ItemViewHolder(convertView, parent.getContext());

            convertView.setTag(vh);

        } else {

            vh = (ItemViewHolder) convertView.getTag();
        }

        return convertView;
    }

}
