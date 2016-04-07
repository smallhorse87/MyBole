package com.zhitian.mybole.activity.myactivities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.Constants;
import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.myactivities.adaptor.ImageSelectionAdaptor;
import com.zhitian.mybole.activity.myactivities.adaptor.ImageSelectionHolder;
import com.zhitian.mybole.api.ApiResult;
import com.zhitian.mybole.api.BoleApi;
import com.zhitian.mybole.base.BaseActivity;
import com.zhitian.mybole.entity.ImageSetInfo;
import com.zhitian.mybole.entity.PrizeInfo;
import com.zhitian.mybole.model.ActivityFormModel;
import com.zhitian.mybole.ui.scanner.common.*;
import com.zhitian.mybole.utils.PrizeLevelUtil;
import com.zhitian.mybole.utils.StringUtils;
import com.zhitian.mybole.utils.TimeUtil;

import java.io.File;
import java.lang.Runnable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.brucetoo.activityanimation.widget.ViewPagerFragment;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

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
    private JsonHttpResponseHandler uploadImageHandler;

    private static final int REQUEST_SELECT_PICTURE = 0x01;
    private static final int REQUEST_TAKE_PHOTO     = 0x02;

    private View.OnFocusChangeListener locateToLastPostion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_prize;
    }

    @Override
    protected String getNavTitle() {
        return "品牌信息";
    }

    @Override
    protected void leftNavBtnHandle() {
        model.rollback();
        finish();
    }

    protected void actionBtnHandle()
    {
        //读取
        model.setPrizeName(etPrizeName.getText().toString());
        model.setPrizeCount(etCount.getText().toString());

        //检查合法性
        String errPrompt = model.checkSanityOfPrize();
        if(errPrompt != null){
            AppContext.showToast(errPrompt);
            return;
        }

        //回调函数
        uploadImageHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Gson gson = new Gson();
                ApiResult result = gson.fromJson(response.toString(), ApiResult.class);

                if (result.getRet() == 0) {
                    try {
                        JSONObject imgSetAttribData = response.getJSONObject("data");
                        ImageSetInfo info = gson.fromJson(imgSetAttribData.toString(), ImageSetInfo.class);

                        model.getImageForUploading().setImgId(info.getImgId());

                        //上传选择的图片
                        if (model.hasImageForUploading()){
                            uploadImage();
                        } else {
                            hideWaitDialog();
                            savePrize();
                        }

                    } catch (Exception e) {
                        hideWaitDialog();
                    }
                } else {
                    AppContext.showToast(result.getMsg());
                    hideWaitDialog();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AppContext.showToast("请求失败");
                hideWaitDialog();
            }
        };

        //上传选择的图片
        if (model.hasImageForUploading()){
            showWaitDialog("上传图片中...");
            uploadImage();
        } else {
            savePrize();
        }
    }

    private void uploadImage(){
        BoleApi.updateImage(Uri.parse(model.getImageForUploading().getUri()), uploadImageHandler);
    }

    private void savePrize(){
        //保存并退出
        model.savePrizeUnderEditting();

        setResult(Activity.RESULT_OK, null);
        finish();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //初始化值
        model = ActivityFormModel.getModelForEdit();

        tvPrizeLevel.setText(model.getPrizeLevel());
        etPrizeName.setText(model.getPrizeName());
        etCount.setText(model.getPrizeCount());
        tvPrizeExpriedTime.setText(TimeUtil.timeIntervalToYYYYMMDDHHMM(model.getPrizeExpiredTime()));
        enablePrizeCount(PrizeLevelUtil.prizeCountNeededWithLevel(model.getPrizeLevel()));

        locateToLastPostion = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    return;

                final EditText et = (EditText)v;
                et.setCursorVisible(false);

                et.post(new Runnable() {
                    @Override
                    public void run() {
                        et.setSelection(et.getText().length());
                        et.setCursorVisible(true);
                    }
                });
            }
        };
        etCount.setOnFocusChangeListener(locateToLastPostion);
        etPrizeName.setOnFocusChangeListener(locateToLastPostion);

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

        rvPrizeImages.setAdapter(new ImageSelectionAdaptor(model.getPrizeImages(), this));
    }

    @OnClick({R.id.ll_prize_level, R.id.ll_prize_expried_time, R.id.tv_template})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_prize_level:
                if (model.hasImageForUploading())
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
        cancelFocus();

        TimeUtil.createDatePicker(this, TimeUtil.TimestampStrToDate(model.getEndTime()), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                long timestamp = date.getTime();
                model.setPrizeExpiredTime(Long.valueOf(timestamp).toString());

                tvPrizeExpriedTime.setText(TimeUtil.dateToYYYYMMDDHHMM(date));
            }
        });
    }


    void pickForPrizeLevel(){
        cancelFocus();

        //选项选择器
        OptionsPickerView pvOptions = new OptionsPickerView(this);

        pvOptions.setPicker(PrizeLevelUtil.getPrizeNameList());

        pvOptions.setCyclic(false);

        int defaultIdx = PrizeLevelUtil.prizeLevelToPrizeIndex(model.getPrizeLevel());
        defaultIdx = defaultIdx < 0 ? 0: defaultIdx;

        pvOptions.setSelectOptions(defaultIdx);

        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                tvPrizeLevel.setText(PrizeLevelUtil.getPrizeLevelNameByIndex(options1));
                model.setPrizeLevel(PrizeLevelUtil.prizeLevelIndexToPrizelevel(options1));

                enablePrizeCount(PrizeLevelUtil.prizeCountNeededWithLevelIndex(options1));
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
        Uri mDestinationUri = Uri.fromFile(new File(getCacheDir(), StringUtils.randomFileName()));
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
            model.addPirzeImageByUriForPrize(resultUri);
            rvPrizeImages.getAdapter().notifyDataSetChanged();
        } else {

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
        cancelFocus();

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
        ArrayList<ImageSetInfo> imageSetInfos = model.getPrizeImages();
        int position = imageSetInfos.indexOf(info);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("imageSetInfos", imageSetInfos);
        bundle.putInt("position", position);
        getSupportFragmentManager().beginTransaction().replace(Window.ID_ANDROID_CONTENT, ViewPagerFragment.getInstance(bundle), "ViewPagerFragment")
                .addToBackStack(null).commit();
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

    private void cancelFocus(){
        etPrizeName.clearFocus();
        etCount.clearFocus();

        tvPrizeLevel.requestFocus();
    }
}
