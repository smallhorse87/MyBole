package com.zhitian.mybole.activity.myactivities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.Constants;
import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.myactivities.adaptor.ImageSelectionAdaptor;
import com.zhitian.mybole.activity.myactivities.adaptor.ImageSelectionHolder;
import com.zhitian.mybole.base.BaseActivity;
import com.zhitian.mybole.entity.ImageSetInfo;
import com.zhitian.mybole.entity.PrizeInfo;
import com.zhitian.mybole.model.ActivityFormModel;
import com.zhitian.mybole.utils.TimeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrizeActivity extends BaseActivity implements ImageSelectionHolder.ImageSelectionListener{
    private static final String TAG = "PrizeActivity";

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

    private static final int REQUEST_SELECT_PICTURE = 0x01;
    private static final int REQUEST_TAKE_PHOTO     = 0x02;
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage.jpeg";

    private Uri mDestinationUri;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_prize;
    }

    @Override
    protected String getNavTitle() {
        return "品牌信息";
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_prize);
        ButterKnife.bind(this);

        //初始化值
        mDestinationUri = Uri.fromFile(new File(getCacheDir(), SAMPLE_CROPPED_IMAGE_NAME));
        model = ActivityFormModel.getModelForEdit();
        initPrizeLevelNameList();

        //视图
        tvTemplate.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        setupImagesSelection();
    }

    void setupImagesSelection(){
        rvPrizeImages.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvPrizeImages.setHasFixedSize(false);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPrizeImages.setLayoutManager(mLayoutManager);

        rvPrizeImages.setAdapter(new ImageSelectionAdaptor(model.getImages(), this));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT_PICTURE) {
            final Uri selectedUri = data.getData();
            if (selectedUri != null) {
                startCropActivity(data.getData());
            } else {
                Toast.makeText(PrizeActivity.this, "获取图片失败", Toast.LENGTH_SHORT).show();
            }
        } else if(resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP){
            handleCropResult(data);

        } else if(resultCode == RESULT_OK && requestCode == REQUEST_TAKE_PHOTO){
            final  Uri selectedUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), Constants.PHOTONAME));
            startCropActivity(selectedUri);
        }

        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void startCropActivity(@NonNull Uri uri) {
        UCrop uCrop = UCrop.of(uri, mDestinationUri);

        uCrop.withAspectRatio(1, 1);
        uCrop.withMaxResultSize(1080, 1080);

        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        uCrop.withOptions(options);

        uCrop.start(PrizeActivity.this);
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            model.addImageByUriForPrize(resultUri);
            rvPrizeImages.getAdapter().notifyDataSetChanged();
        } else {
            //Toast.makeText(SampleActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show(); stony
        }
    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            Toast.makeText(PrizeActivity.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(PrizeActivity.this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addImage(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_SELECT_PICTURE);
        }
    }

    @Override
    public void deleteImage(ImageSetInfo info){
        model.deleteImageInfo(info);
        rvPrizeImages.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void navImage(ImageSetInfo info){

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
