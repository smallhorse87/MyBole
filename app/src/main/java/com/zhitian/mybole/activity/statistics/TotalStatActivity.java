package com.zhitian.mybole.activity.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhitian.mybole.R;
import com.zhitian.mybole.api.BoleApi;
import com.zhitian.mybole.api.OperationResponseHandler;
import com.zhitian.mybole.entity.StatInfo;
import com.zhitian.mybole.entity.TotalStatInfo;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TotalStatActivity extends AppCompatActivity {

    @Bind(R.id.lv_total_statistics)
    ListView lvTotalStatistics;

    HeaderHolder headerHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_stat);
        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        LinearLayout header = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.header_total_stat, null);
        lvTotalStatistics.addHeaderView(header);
        headerHolder = new HeaderHolder(header);

        lvTotalStatistics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TotalStatActivity.this, DetailedStatActivity.class);
                intent.putExtra("activityId", Long.valueOf(id).toString());
                TotalStatActivity.this.startActivity(intent);
            }
        });

        BoleApi.getTotalStat(new OperationResponseHandler() {
            @Override
            public void onJsonSuccess(Object retData) {
                TotalStatInfo totalInfo = retStatsList((JSONObject)retData);

                headerHolder.setContent(totalInfo);

                StatListAdapter adapter = new StatListAdapter(totalInfo.getActivityStatistics());
                lvTotalStatistics.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onJsonFailure(int statusCode, String errMsg) {

            }
        });
    }

    class StatListAdapter extends BaseAdapter {

        List<StatInfo> statInfoList;

        public StatListAdapter(List<StatInfo> InfoList) {
            statInfoList = InfoList;
        }

        @Override
        public int getCount() {
            return statInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return statInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            String activityIdStr = statInfoList.get(position).getActivityId();

            return Long.parseLong(activityIdStr);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CellHolder cellHolder = null;

            if (convertView == null) {

                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stat, null, false);

                cellHolder = new CellHolder(convertView);

                convertView.setTag(cellHolder);
            }

            cellHolder = (CellHolder)convertView.getTag();

            cellHolder.setupContent(statInfoList.get(position));

            return convertView;
        }

    }

    static class CellHolder {
        @Bind(R.id.tv_activity_name)
        TextView tvActivityName;
        @Bind(R.id.tv_player_count)
        TextView tvPlayerCount;
        @Bind(R.id.tv_view_count)
        TextView tvViewCount;
        @Bind(R.id.tv_forward_count)
        TextView tvForwardCount;

        CellHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setupContent(StatInfo info){
            tvActivityName.setText(info.getName());
            tvPlayerCount.setText(info.getPlayerCnt());
            tvViewCount.setText(info.getViewCnt());
            tvForwardCount.setText(info.getForwardCnt());
        }
    }

    static class HeaderHolder {
        @Bind(R.id.tv_left_count)
        TextView tvLeftCount;
        @Bind(R.id.tv_left_title)
        TextView tvLeftTitle;
        @Bind(R.id.tv_right_count)
        TextView tvRightCount;
        @Bind(R.id.tv_right_title)
        TextView tvRightTitle;

        HeaderHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setContent(TotalStatInfo totalStatInfo) {

            tvLeftCount.setText(Integer.valueOf(totalStatInfo.getNewPlayer()).toString());

            tvRightCount.setText(totalStatInfo.getPlayerCnt());
        }
    }
}
