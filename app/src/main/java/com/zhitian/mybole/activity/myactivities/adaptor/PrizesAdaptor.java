package com.zhitian.mybole.activity.myactivities.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhitian.mybole.R;
import com.zhitian.mybole.entity.PrizeInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class PrizesAdaptor extends BaseAdapter {

    List<PrizeInfo> currentList;

    public PrizesAdaptor(List<PrizeInfo> list) {
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

        ViewHolder vh = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prize, null, false);

            vh = new ViewHolder(convertView);

            convertView.setTag(vh);

        } else {

            vh = (ViewHolder) convertView.getTag();
        }

        vh.setupContent(currentList.get(position));

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_level)
        ImageView ivLevel;
        @Bind(R.id.tv_level)
        TextView tvLevel;
        @Bind(R.id.tv_count)
        TextView tvCount;
        @Bind(R.id.tv_prize_name)
        TextView tvPrizeName;
        @Bind(R.id.iv_delete)
        ImageView ivDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setupContent(PrizeInfo info){

        }
    }
}
