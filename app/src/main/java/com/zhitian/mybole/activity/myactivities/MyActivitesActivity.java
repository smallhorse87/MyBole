package com.zhitian.mybole.activity.myactivities;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.myactivities.adaptor.ActivitiesAdaptor;
import com.zhitian.mybole.api.BoleApi;
import com.zhitian.mybole.api.OperationResponseHandler;
import com.zhitian.mybole.entity.ActivityInfo;
import com.zhitian.mybole.entity.PageInfo;
import com.zhitian.mybole.ui.xlist.XListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyActivitesActivity extends AppCompatActivity implements XListView.IXListViewListener {

    @Bind(R.id.lv_my_activities)
    XListView lvMyActivities;

    ActivitiesAdaptor adaptor;
    private Handler mHandler;

    private int page = 1;

    List<ActivityInfo> currentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activites);
        ButterKnife.bind(this);

        currentList = new ArrayList<ActivityInfo>();
        adaptor = new ActivitiesAdaptor(currentList);
        lvMyActivities.setAdapter(adaptor);

        lvMyActivities.setPullLoadEnable(true);
        lvMyActivities.setXListViewListener(this);
        mHandler = new Handler();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    public void requestForList(){
        BoleApi.getActivityList(page, new OperationResponseHandler() {
            @Override
            public void onJsonSuccess(JSONObject retData) {

                PageInfo pageInfo = retPageInfo(retData);

                List<ActivityInfo> list = retActivityList(retData);

                if (page == 1) {
                    currentList.clear();
                }

                currentList.addAll(list);

                adaptor.notifyDataSetChanged();

                onLoad();
            }

            @Override
            public void onJsonFailure(int statusCode, String errMsg) {

                if (page > 1) page--;

                onLoad();
            }
        });
    }


    private void onLoad() {
        lvMyActivities.stopRefresh();
        lvMyActivities.stopLoadMore();
        lvMyActivities.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                requestForList();
            }
        }, 100);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page ++;
                requestForList();
            }
        }, 100);
    }
}
