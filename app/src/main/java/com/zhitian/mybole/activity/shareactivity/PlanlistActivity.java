package com.zhitian.mybole.activity.shareactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.zhitian.mybole.R;
import com.zhitian.mybole.api.BoleApi;
import com.zhitian.mybole.api.OperationResponseHandler;
import com.zhitian.mybole.entity.ActivityInfo;
import com.zhitian.mybole.entity.ImageSetInfo;
import com.zhitian.mybole.entity.PlanInfo;

import org.json.JSONArray;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlanlistActivity extends AppCompatActivity {

    @Bind(R.id.listView)
    ListView listView;

    String activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planlist);
        ButterKnife.bind(this);

        activityId = getIntent().getStringExtra("activityId");

        View headerView = getLayoutInflater().inflate(R.layout.header_plan, null);

        listView.addHeaderView(headerView);

        ImageRequest.fromUri(Uri.parse("http://www.baidu.com"));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlanlistActivity.this, PublishActivity.class);

                PlanInfo info = (PlanInfo)listView.getAdapter().getItem(position);
                ImageSetInfo imageSetInfo = info.getImgs().get(0);

                intent.putExtra("imageUrl", imageSetInfo.getThumbnailImg().getUrl());

                PlanlistActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        BoleApi.getPlanlist(activityId, new OperationResponseHandler() {
            @Override
            public void onJsonSuccess(Object retData) {
                List<PlanInfo> infos = retPlanInfoList((JSONArray) retData);

                Log.i("stony", retData.toString());

                listView.setAdapter(new PlanListAdapter(infos));
            }

            @Override
            public void onJsonFailure(int statusCode, String errMsg) {
                Log.i("stony", errMsg.toString());
            }
        });

    }

    static class PlanListAdapter extends BaseAdapter {

        List<PlanInfo> planInfoList;

        public PlanListAdapter(List<PlanInfo> list) {
            planInfoList = list;
        }

        @Override
        public int getCount() {
            return planInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return planInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            PlanInfo info = planInfoList.get(position);

            return Long.parseLong(info.getPlanId());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;

            if (convertView == null) {

                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, null, false);

                viewHolder = new ViewHolder(convertView);

                convertView.setTag(viewHolder);

            } else {

                viewHolder = (ViewHolder)convertView.getTag();
            }

            viewHolder.setupContent(planInfoList.get(position));

            return convertView;
        }
    }

    static class ViewHolder {
        @Bind(R.id.iv_plan_image)
        SimpleDraweeView ivPlanImage;
        @Bind(R.id.tv_plan_title)
        TextView tvPlanTitle;
        @Bind(R.id.tv_plan_subtitle)
        TextView tvPlanSubtitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setupContent(PlanInfo info){

            ImageSetInfo imageSetInfo = info.getImgs().get(0);

            ivPlanImage.setImageURI(Uri.parse(imageSetInfo.getThumbnailImg().getUrl()));

            tvPlanTitle.setText(info.getTitle());

            tvPlanSubtitle.setText(info.getContent());
        }
    }

}
