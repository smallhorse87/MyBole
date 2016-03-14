package com.zhitian.mybole.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zhitian.mybole.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyActivitesActivity extends AppCompatActivity {

    @Bind(R.id.lv_my_activities)
    ListView lvMyActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activites);
        ButterKnife.bind(this);

        lvMyActivities.setAdapter(new BaseAdapter() {
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

                ViewHolder vh = null;

                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_activity, null, false);

                    //vh = new ViewHolder(convertView);

                    convertView.setTag(vh);

                } else {

                    vh = (ViewHolder)convertView.getTag();
                }

                return convertView;
            }
        });
    }

    static class ViewHolder {
        @Bind(R.id.tv_activity_name)
        TextView tvActivityName;
        @Bind(R.id.tv_activity_state)
        TextView tvActivityState;
        @Bind(R.id.tv_game_name)
        TextView tvGameName;
        @Bind(R.id.tv_player_count)
        TextView tvPlayerCount;
        @Bind(R.id.btn_share)
        Button btnShare;
        @Bind(R.id.btn_statistic)
        Button btnStatistic;
        @Bind(R.id.btn_ranking)
        Button btnRanking;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
