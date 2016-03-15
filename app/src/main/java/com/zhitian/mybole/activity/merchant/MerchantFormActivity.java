package com.zhitian.mybole.activity.merchant;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.bigkoo.pickerview.OptionsPickerView;
import com.rey.material.app.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.Constants;
import com.zhitian.mybole.R;
import com.zhitian.mybole.api.BoleApi;
import com.zhitian.mybole.api.OperationResponseHandler;
import com.zhitian.mybole.base.BaseActivity;
import com.zhitian.mybole.entity.ConfigInfo;
import com.zhitian.mybole.entity.MerchantInfo;
import com.zhitian.mybole.model.MerchantFormModel;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import com.bigkoo.pickerview.TimePickerView;
import com.zhitian.mybole.ui.RegionPickerBuilder;
import com.zhitian.mybole.utils.RegionXmlUtil;
import com.zhitian.mybole.utils.StringUtils;

public class MerchantFormActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SampleActivity";

    private static final int REQUEST_SELECT_PICTURE = 0x01;
    private static final int REQUEST_TAKE_PHOTO     = 0x02;

    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage.jpeg";

    private MerchantFormModel model;

    @Bind(R.id.ll_logo)
    LinearLayout mllLogo;
    @Bind(R.id.iv_logo)
    SimpleDraweeView mIvLogo;
    @Bind(R.id.et_merchantname)
    EditText etMerchantname;
    @Bind(R.id.tv_category)
    TextView tvCategory;
    @Bind(R.id.ll_category)
    LinearLayout llCategory;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.ll_address)
    LinearLayout llAddress;
    @Bind(R.id.et_detailed_address)
    EditText etDetailedAddress;
    @Bind(R.id.et_telphone)
    EditText etTelphone;
    @Bind(R.id.et_offical_account)
    EditText etOfficalAccount;
    @Bind(R.id.iv_qrcode)
    SimpleDraweeView ivQrcode;
    @Bind(R.id.tv_qrcode_placeholder)
    TextView tvQrcodePlaceholder;
    @Bind(R.id.ll_qrcode)
    LinearLayout llQrcode;

    BottomSheetDialog pickupImageSheet;

    private Uri mDestinationUri;

    MerchantInfo info;

    private SimpleDraweeView selectedIV;

    private LocationListener llistener;
    private LocationManager  locationManager;
    private LocationProvider provider;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_merchant_form;
    }

    @Override
    protected String getNavTitle() {
        return "品牌信息";
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        info = AppContext.myInfo;

        etMerchantname.setText(info.getName());
        tvCategory.setText(info.getCategory());
        tvAddress.setText(info.getFullRegionName());
        etDetailedAddress.setText(info.getAddress());
        etTelphone.setText(info.getTel());
        etOfficalAccount.setText(info.getWechat());

        if (info.getReaLogoUri() != null){
            mIvLogo.setImageURI(info.getReaLogoUri());
        }

        if (info.getRealWechatQrcodeUri() != null){
            tvQrcodePlaceholder.setVisibility(View.GONE);
            ivQrcode.setImageURI(info.getRealWechatQrcodeUri());
        }

        //其他的：品牌logo，二维码图片，GPS地址
    }

    @Override
    protected void initListeners(Bundle savedInstanceState) {
        mDestinationUri = Uri.fromFile(new File(getCacheDir(), SAMPLE_CROPPED_IMAGE_NAME));

        model = new MerchantFormModel(info, this);

        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(serviceName);
        llistener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location)
            {
                // TODO Auto-generated method stub
                Log.i("onLocationChanged", "come in");
                if (location != null)
                {
                    Log.w("Location", "Current altitude = "
                            + location.getAltitude());
                    Log.w("Location", "Current latitude = "
                            + location.getLatitude());
                }
            }
            @Override
            public void onProviderDisabled(String provider)
            {
                // TODO Auto-generated method stub
                Log.i("onProviderDisabled", "come in");
            }
            @Override
            public void onProviderEnabled(String provider)
            {
                // TODO Auto-generated method stub
                Log.i("onProviderEnabled", "come in");
            }
            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras)
            {
                // TODO Auto-generated method stub
                Log.i("onStatusChanged", "come in");
            }
        };

        int accessFineLoactionGranted = ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int accessCoarseLoactionGranted = ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION);

        //@TargetApi(23)
        if ( Build.VERSION.SDK_INT >= 23 && accessFineLoactionGranted == PackageManager.PERMISSION_GRANTED && accessCoarseLoactionGranted == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, (float) 10.0, llistener);
        }

    }

    @Override
    protected void leftNavBtnHandle() {
        AppContext.showToast("点击了返回键");
    }

    protected void actionBtnHandle() {
        AppContext.showToast("点击了右侧键");

        //其他的：品牌logo，二维码图片，类别，地址（省市区），gps地址
        model.setName(etMerchantname.getText().toString());
        model.setAddress(etDetailedAddress.getText().toString());
        model.setTel(etTelphone.getText().toString());
        model.setWechat(etOfficalAccount.getText().toString());

        model.submitForm();
    }

    @Override
    public void onResume(){
        super.onResume();

        startDownloadSystemConfig();
    }

    @Override
    public  void onPause(){

        pickupImageSheet.dismissImmediately();

        super.onPause();
    }

    private void pickForRegion(){
        //选项选择器：地区id，地区名称
        OptionsPickerView regionPicker = RegionPickerBuilder.getPicker(this, new RegionPickerBuilder.RegionPickerListener() {
            @Override
            public void onRegionSelect(String districtId, String province, String city, String district) {
                model.setRegionNames(province, city, district);
                model.setDistrictId(districtId);

                tvAddress.setText(info.getFullRegionName());
            }
        });

        RegionPickerBuilder.setDefaulSelection(info.getDistrictid(), regionPicker);

        regionPicker.show();
    }

    private void pickForCategory(){
        //选项选择器
        OptionsPickerView pvOptions = new OptionsPickerView(this);

        pvOptions.setPicker((ArrayList) AppContext.cfgInfo.getCategory());

        pvOptions.setCyclic(false);

        pvOptions.setSelectOptions(1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String categoryName = AppContext.cfgInfo.getCategory().get(options1);
                tvCategory.setText(categoryName);
                model.setCategory(categoryName);
            }

        });

        pvOptions.show();
    }

    private void pickForDate(){
        TimePickerView         pvTime = new TimePickerView(this, TimePickerView.Type.ALL);

        //控制时间范围
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);

        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {

            }
        });

        pvTime.show();
    }

    private void pickFromCamera(){
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), Constants.PHOTONAME)));
        startActivityForResult(intentFromCapture,
                REQUEST_TAKE_PHOTO);
    }

    private void pickFromGallery() {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT_PICTURE) {
            final Uri selectedUri = data.getData();
            if (selectedUri != null) {
                startCropActivity(data.getData());
            } else {
                Toast.makeText(MerchantFormActivity.this, "获取图片失败", Toast.LENGTH_SHORT).show();
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

    private void startDownloadSystemConfig()
    {
        if(AppContext.cfgInfo == null){

            BoleApi.getSystemConfig(new OperationResponseHandler(){

                public void onJsonSuccess(Object retData){
                    ConfigInfo info = retSystemConfig((JSONObject)retData);

                    if (RegionXmlUtil.doesUpdateNeeded(info.getRegions().getUpdateAt())){
                        RegionXmlUtil.downloadRegionXml(info.getRegions().getUrl());
                    }
                }
                public void onJsonFailure(int statusCode, String errMsg){

                }

            });
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

        uCrop.start(MerchantFormActivity.this);
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            selectedIV.setImageURI(resultUri);

            if (selectedIV == mIvLogo) {
                model.setAvatar(resultUri);

            } else if(selectedIV == ivQrcode) {
                model.setWechatQr(resultUri);

            }

        } else {
            //Toast.makeText(SampleActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show(); stony
        }
    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            Toast.makeText(MerchantFormActivity.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MerchantFormActivity.this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.ll_logo, R.id.et_merchantname, R.id.ll_category, R.id.ll_address, R.id.et_detailed_address, R.id.et_telphone, R.id.et_offical_account, R.id.ll_qrcode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_logo:
            {
                selectedIV = mIvLogo;

                pickupImageSheet = new BottomSheetDialog(this);
                View sheetView = LayoutInflater.from(this).inflate(R.layout.sheet_pickup_image, null);
                Button photoBtn = (Button)sheetView.findViewById(R.id.btn_pic_camera);
                photoBtn.setOnClickListener(this);

                Button galleryBtn = (Button)sheetView.findViewById(R.id.btn_pic_gallery);
                galleryBtn.setOnClickListener(this);

                Button exitBtn = (Button)sheetView.findViewById(R.id.btn_pic_exit);
                exitBtn.setOnClickListener(this);

                pickupImageSheet.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
                pickupImageSheet.setContentView(sheetView);
                pickupImageSheet.show();
            }
                break;

            case R.id.et_merchantname:
                break;

            case R.id.ll_category:
                pickForCategory();
                break;
            case R.id.ll_address:
                pickForRegion();
                break;
            case R.id.et_detailed_address:
                break;
            case R.id.et_telphone:
                break;
            case R.id.et_offical_account:
                break;
            case R.id.ll_qrcode:
            {
                selectedIV = ivQrcode;

                pickupImageSheet = new BottomSheetDialog(this);
                View sheetView = LayoutInflater.from(this).inflate(R.layout.sheet_pickup_image, null);
                Button photoBtn = (Button)sheetView.findViewById(R.id.btn_pic_camera);
                photoBtn.setOnClickListener(this);

                Button galleryBtn = (Button)sheetView.findViewById(R.id.btn_pic_gallery);
                galleryBtn.setOnClickListener(this);

                Button exitBtn = (Button)sheetView.findViewById(R.id.btn_pic_exit);
                exitBtn.setOnClickListener(this);

                pickupImageSheet.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
                pickupImageSheet.setContentView(sheetView);
                pickupImageSheet.show();
            }
                break;

            case R.id.btn_pic_gallery:
                pickFromGallery();
                pickupImageSheet.dismiss();
                break;

            case R.id.btn_pic_camera:
                pickFromCamera();
                pickupImageSheet.dismiss();
                break;

            case R.id.btn_pic_exit:
                pickupImageSheet.dismiss();
                break;
        }
    }
}
