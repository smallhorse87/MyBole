package com.zhitian.mybole.activity.myactivities.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhitian.mybole.R;
import com.zhitian.mybole.entity.ActivityInfo;

import java.util.List;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class ActivitiesAdaptor extends BaseAdapter {

    List<ActivityInfo> currentList;

    public ActivitiesAdaptor(List<ActivityInfo> list) {
        currentList = list;
    }

    @Override
    public int getCount() {
        return currentList.size();
    }

    @Override
    public Object getItem(int position) {
        return currentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ActivitiesHolder vh = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_activity, null, false);

            vh = new ActivitiesHolder(convertView, parent.getContext());

            convertView.setTag(vh);

        } else {

            vh = (ActivitiesHolder) convertView.getTag();
        }

        vh.setupContent(currentList.get(position));

        return convertView;
    }

}
