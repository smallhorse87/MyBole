package com.zhitian.mybole.activity.myactivities.adaptor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.myactivities.ActivityCreation;
import com.zhitian.mybole.activity.myactivities.PrizeActivity;
import com.zhitian.mybole.entity.PrizeInfo;
import com.zhitian.mybole.model.ActivityFormModel;
import com.zhitian.mybole.utils.PrizeLevelUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class PrizesAdaptor extends BaseAdapter {

    List<PrizeInfo> currentList;
    ActivityFormModel myModel;
    Activity superActivity;

    public PrizesAdaptor(List<PrizeInfo> list, ActivityFormModel model, ActivityCreation activity) {
        currentList = list;
        myModel = model;
        superActivity =activity;
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

    class ViewHolder {
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

        PrizeInfo myInfo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setupContent(PrizeInfo info){
            myInfo = info;
            ivLevel.setImageResource(PrizeLevelUtil.getPrizeLevelLogoResId(info.getPrizeLevel(), "1"));
            tvLevel.setText(PrizeLevelUtil.getPrizeLevelNameByLevel(info.getPrizeLevel()));
            tvCount.setText(info.getNumber());
            tvPrizeName.setText(info.getName());
        }

        @OnClick({R.id.iv_delete})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_delete:
                    deleteCurrentPrize();
                    break;
            }
        }

        private void deleteCurrentPrize(){

            new AlertDialog.Builder(superActivity).setTitle("确认删除吗？")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myModel.removePrize(myInfo);
                            PrizesAdaptor.this.notifyDataSetChanged();
                            ((ActivityCreation)superActivity).setupAddCell();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 点击“返回”后的操作,这里不设置没有任何操作
                        }
                    }).show();
        }

    }

}
