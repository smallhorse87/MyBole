package com.zhitian.mybole.activity.merchant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import com.rey.material.app.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
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
import com.zhitian.mybole.base.BaseActivity;
import com.zhitian.mybole.entity.MerchantInfo;
import com.zhitian.mybole.model.MerchantFormModel;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

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
    public  void onPause(){

        pickupImageSheet.dismissImmediately();

        super.onPause();
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
                break;
            case R.id.ll_address:
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
