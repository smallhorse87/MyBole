package com.zhitian.mybole.activity.myactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.myactivities.adaptor.ActivitiesAdaptor;

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

        lvMyActivities.setAdapter(new ActivitiesAdaptor());
    }
}
