package com.zhitian.mybole.activity.myactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.zhitian.mybole.Constants;
import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.myactivities.adaptor.GameAdaptor;
import com.zhitian.mybole.activity.myactivities.adaptor.PrizesAdaptor;
import com.zhitian.mybole.api.BoleApi;
import com.zhitian.mybole.api.OperationResponseHandler;
import com.zhitian.mybole.entity.GameInfo;
import com.zhitian.mybole.entity.PrizeInfo;
import com.zhitian.mybole.model.ActivityFormModel;
import com.zhitian.mybole.ui.NestedListview;
import com.zhitian.mybole.utils.TimeUtil;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityCreation extends AppCompatActivity {

    @Bind(R.id.et_activity_name)
    EditText etActivityName;
    @Bind(R.id.tv_start_time)
    TextView tvStartTime;
    @Bind(R.id.ll_start_time)
    LinearLayout llStartTime;
    @Bind(R.id.tv_end_time)
    TextView tvEndTime;
    @Bind(R.id.ll_end_time)
    LinearLayout llEndTime;
    @Bind(R.id.et_rule)
    EditText etRule;
    @Bind(R.id.tv_remaining_count)
    TextView tvRemainingCount;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.ll_add_prize)
    LinearLayout llAddPrize;
    @Bind(R.id.lv_prizes)
    NestedListview lvPrizes;
    @Bind(R.id.btn_preview)
    Button btnPreview;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.rv_games)
    RecyclerView rvGames;

    List<GameInfo> gameList = new ArrayList<GameInfo>();
    OperationResponseHandler getGameListHandler = null;

    ActivityFormModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_creation);

        //初始化数据
        model = new ActivityFormModel();

        //视图相关
        ButterKnife.bind(this);

        lvPrizes.setAdapter(new PrizesAdaptor(model.getPrizes()));
        etActivityName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Constants.APPCFG_ACTIVIT_NAME_CHAR_COUNT)});

        setupGameCheckBox();
        setupPeriod();
        setupRules();

        //回调相关
        configHandlers();

        //kick off api request
        BoleApi.getGameList(getGameListHandler);
    }

    void setupGameCheckBox(){
        rvGames.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvGames.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvGames.setLayoutManager(mLayoutManager);

        rvGames.setAdapter(new GameAdaptor(gameList, model));
    }

    void setupRules(){
        etRule.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Constants.APPCFG_ACTIVIT_RULE_CHAR_COUNT)});
        showLeftCount(etRule.getText());

        etRule.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showLeftCount(s);
            }
        });
    }

    void setupPeriod(){
        Date now = new Date();

        setStartTime(now);

        setEndTime(TimeUtil.addDaysSince(now, Constants.APPCFG_ACTIVIT_DEFAULT_PEORID));
    }

    void configHandlers(){
        getGameListHandler = new OperationResponseHandler() {
            @Override
            public void onJsonSuccess(Object retData) {
                gameList.clear();
                gameList.addAll(retGameInfoList((JSONArray) retData));

                rvGames.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onJsonFailure(int statusCode, String errMsg) {
                Log.i("stony", "failed");
            }
        };

    }

    @OnClick({R.id.ll_start_time, R.id.ll_end_time, R.id.ll_add_prize, R.id.btn_preview, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_start_time:
                pickStartTime();
                break;
            case R.id.ll_end_time:
                pickEndTime();
                break;
            case R.id.ll_add_prize:
                model.prepareForEdit(null);
                Intent intent = new Intent(this, PrizeActivity.class);
                this.startActivity(intent);
                break;
            case R.id.btn_preview:
                preview();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void pickStartTime(){
        TimeUtil.createDatePicker(this, TimeUtil.TimestampStrToDate(model.getStartTime()), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                //设置并显示
                setStartTime(date);
            }
        });
    }

    private void pickEndTime(){
        TimeUtil.createDatePicker(this, TimeUtil.TimestampStrToDate(model.getEndTime()), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                //设置并显示
                setEndTime(date);
            }
        });
    }

    private void preview(){

        String failReason = checkAndSetBeforeRequest();

        if (failReason != null)
            Toast.makeText(this, failReason, Toast.LENGTH_LONG).show();

        //提交
    }

    private void submit(){
        String failReason = checkAndSetBeforeRequest();

        if (failReason != null)
            Toast.makeText(this, failReason, Toast.LENGTH_LONG).show();

        //提交

    }

    private void setStartTime(Date date){
        long timestamp = date.getTime();
        model.setStartTime(Long.valueOf(timestamp).toString());

        tvStartTime.setText(TimeUtil.dateToYYYYMMDDHHMM(date));
    }

    private void setEndTime(Date date){
        long timestamp = date.getTime();
        model.setEndTime(Long.valueOf(timestamp).toString());

        tvEndTime.setText(TimeUtil.dateToYYYYMMDDHHMM(date));
    }

    private String checkAndSetBeforeRequest(){
        if(model.getGame() == null)
            return "请选择一款游戏";

        if (etActivityName.getText().toString().length() == 0)
            return "请输入活动名称";

        if (TimeUtil.diffDaysBetweenTimestamps(model.getStartTime(), model.getEndTime()) < 0)
            return "开始时间不能晚于结束时间";

        if (TimeUtil.diffDaysBetweenTimestamps(model.getStartTime(), model.getEndTime()) > Constants.APPCFG_ACTIVIT_MAX_PEORID)
            return "活动时间不能超过7天";

        if (model.getPrizes() == null || model.getPrizes().size() == 0)
            return "请至少添加一个奖品";

        model.setName(etActivityName.getText().toString());
        model.setName(etRule.getText().toString());

        return null;
    }

    private void showLeftCount(Editable s){
        int leftCount = Constants.APPCFG_ACTIVIT_RULE_CHAR_COUNT - s.length();
        tvRemainingCount.setText(Integer.valueOf(leftCount).toString());
    }
}
