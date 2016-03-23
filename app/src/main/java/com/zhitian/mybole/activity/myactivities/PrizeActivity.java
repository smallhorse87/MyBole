package com.zhitian.mybole.activity.myactivities;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.R;
import com.zhitian.mybole.model.ActivityFormModel;
import com.zhitian.mybole.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrizeActivity extends AppCompatActivity {

    @Bind(R.id.tv_prize_level)
    TextView tvPrizeLevel;
    @Bind(R.id.ll_prize_level)
    LinearLayout llPrizeLevel;
    @Bind(R.id.et_prize_name)
    EditText etPrizeName;
    @Bind(R.id.et_count)
    EditText etCount;
    @Bind(R.id.tv_prize_expried_time)
    TextView tvPrizeExpriedTime;
    @Bind(R.id.ll_prize_expried_time)
    LinearLayout llPrizeExpriedTime;
    @Bind(R.id.rv_prize_images)
    RecyclerView rvPrizeImages;
    @Bind(R.id.tv_template)
    TextView tvTemplate;
    @Bind(R.id.tv_count_negative)
    TextView tvCountNegative;


    ActivityFormModel model;
    ArrayList<String> prizeLevelNameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize);
        ButterKnife.bind(this);

        //初始化值
        model = ActivityFormModel.getModelForEdit();
        initPrizeLevelNameList();

        //视图
        tvTemplate.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    void setupImagesSelection(){

    }

    @OnClick({R.id.ll_prize_level, R.id.ll_prize_expried_time, R.id.tv_template})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_prize_level:
                pickForPrizeLevel();
                break;

            case R.id.ll_prize_expried_time:
                pickForDate();
                break;

            case R.id.tv_template:

                break;
        }
    }

    void pickForDate(){
        TimeUtil.createDatePicker(this, TimeUtil.TimestampStrToDate(model.getEndTime()), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                setEndTime(date);
            }
        });
    }

    private void setEndTime(Date date){
        long timestamp = date.getTime();
        model.setEndTime(Long.valueOf(timestamp).toString());

        tvPrizeExpriedTime.setText(TimeUtil.dateToYYYYMMDDHHMM(date));
    }

    void pickForPrizeLevel(){

        //选项选择器
        OptionsPickerView pvOptions = new OptionsPickerView(this);

        pvOptions.setPicker(prizeLevelNameList);

        pvOptions.setCyclic(false);

        pvOptions.setSelectOptions(1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String levelName = prizeLevelNameList.get(options1);
                tvPrizeLevel.setText(levelName);

                enablePrizeCount(!(options1 == prizeLevelNameList.size() - 1));

            }
        });
        pvOptions.show();
    }

    private void enablePrizeCount(boolean enabled){
        tvCountNegative.setVisibility(View.GONE);
        etCount.setVisibility(View.GONE);

        if (enabled){
            etCount.setVisibility(View.VISIBLE);
        } else {
            tvCountNegative.setVisibility(View.VISIBLE);
        }
    }

    void initPrizeLevelNameList(){
        prizeLevelNameList.add("一等奖");
        prizeLevelNameList.add("二等奖");
        prizeLevelNameList.add("三等奖");
        prizeLevelNameList.add("优秀奖");
        prizeLevelNameList.add("鼓励奖");
    }
}
