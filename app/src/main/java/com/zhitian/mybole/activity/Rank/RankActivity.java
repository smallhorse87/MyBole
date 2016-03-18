package com.zhitian.mybole.activity.Rank;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhitian.mybole.R;
import com.zhitian.mybole.api.BoleApi;
import com.zhitian.mybole.api.OperationResponseHandler;
import com.zhitian.mybole.entity.PageInfo;
import com.zhitian.mybole.entity.RankInfo;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RankActivity extends AppCompatActivity {

    @Bind(R.id.listView2)
    ListView listView2;

    TextView tvActivityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        ButterKnife.bind(this);

        View header = getLayoutInflater().inflate(R.layout.header_rank, null);
        listView2.addHeaderView(header);

        String activityId = getIntent().getStringExtra("activityId");
        String activityName = getIntent().getStringExtra("activityName");

        tvActivityName = (TextView)header.findViewById(R.id.tv_activity_name);
        tvActivityName.setText("活动名称:"+activityName);

        BoleApi.getRankList(activityId, new RankApiHandler());

    }

     class RankApiHandler extends OperationResponseHandler{
        public void onJsonSuccess(Object retData){

            PageInfo pageInfo = retPageInfo((JSONObject)retData);

            List<RankInfo> list = retRankInfoList((JSONObject)retData);

            RankActivity.this.listView2.setAdapter(new RankAdaptar(list));

            Log.i("stony", list.toString());
        }

        public void onJsonFailure(int statusCode, String errMsg){
            Log.i("stony", errMsg);
        }
    }

    static class RankAdaptar extends BaseAdapter {

        List<RankInfo> infos;

        RankAdaptar (List<RankInfo> list) {

            infos = list;
        }

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder vh = null;

            if (convertView == null) {

                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rank, null);

                vh = new ViewHolder(convertView);

                convertView.setTag(vh);

            } else {

                vh = (ViewHolder)convertView.getTag();
            }

            vh.setupContent(infos.get(position));

            return convertView;
        }
    }

    static class ViewHolder {
        @Bind(R.id.tv_rank)
        TextView tvRank;
        @Bind(R.id.iv_avatar)
        SimpleDraweeView ivAvatar;
        @Bind(R.id.tv_user_name)
        TextView tvUserName;
        @Bind(R.id.tv_achievement)
        TextView tvAchievement;
        @Bind(R.id.pb_achievement)
        ProgressBar pbAchievement;
        @Bind(R.id.iv_prize_logo)
        ImageView ivPrizeLogo;
        @Bind(R.id.tv_prize)
        TextView tvPrize;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setupContent(RankInfo info) {

            tvRank.setText(Integer.valueOf(info.getRanking()).toString());
            ivAvatar.setImageURI(Uri.parse(info.getAvatar().getThumbnailImg().getUrl()));
            tvUserName.setText(info.getPlayer());
            tvAchievement.setText(info.getScore());

            tvPrize.setText(info.getPrizeLevelStr());

            if (info.getPrizeLevelLogoResId() == 0)
            {
                ivPrizeLogo.setVisibility(View.GONE);
            } else {
                ivPrizeLogo.setVisibility(View.VISIBLE);
                ivPrizeLogo.setImageResource(info.getPrizeLevelLogoResId());
            }

            pbAchievement.setProgress(30);

        }
    }
}
