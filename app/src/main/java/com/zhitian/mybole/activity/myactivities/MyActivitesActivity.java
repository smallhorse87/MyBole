package com.zhitian.mybole.activity.myactivities;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.myactivities.adaptor.ActivitiesAdaptor;
import com.zhitian.mybole.api.BoleApi;
import com.zhitian.mybole.api.OperationResponseHandler;
import com.zhitian.mybole.entity.ActivityInfo;
import com.zhitian.mybole.entity.PageInfo;

import org.json.JSONObject;

import java.util.List;

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

    @Override
    public void onResume(){
        super.onResume();

        BoleApi.getActivityList(new OperationResponseHandler() {
            @Override
            public void onJsonSuccess(JSONObject retData) {

                PageInfo pageInfo = retPageInfo(retData);

                List<ActivityInfo> list = retActivityList(retData);

            }

            @Override
            public void onJsonFailure(int statusCode, String errMsg) {

            }
        });
    }
}
