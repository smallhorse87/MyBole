package com.zhitian.mybole.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.bacy.view.titlebar.TitleBar;

import com.zhitian.mybole.ui.dialog.CommonToast;
import com.zhitian.mybole.ui.dialog.DialogControl;
import com.zhitian.mybole.ui.dialog.DialogHelper;
import com.zhitian.mybole.ui.dialog.WaitDialog;

import com.zhitian.mybole.R;

public class BaseActivity extends AppCompatActivity implements
        DialogControl, VisibilityControl, View.OnClickListener {
    ImageView mCollectView;
    Boolean mIsSelected;

    private boolean _isVisible;
    private WaitDialog _waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }

        //setTranslucentStatus(true);
        init(savedInstanceState);
        setNavBar();
    }

    @Override
    protected void onPause() {
        _isVisible = false;
        hideWaitDialog();
        super.onPause();
    }

    @Override
    protected void onResume() {
        _isVisible = true;
        super.onResume();
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        if (hasKitKat() && !hasLollipop()) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
        } else if (hasLollipop()) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setNavBar()
    {
//        final TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
//
//        if (titleBar == null)
//            return;
//
//        if (getNavTitle() == null || getNavTitle().length() == 0)
//            titleBar.setVisibility(View.GONE);
//
//        titleBar.setImmersive(true);
//
//        titleBar.setBackgroundColor(Color.parseColor("#64b4ff"));
//
//        titleBar.setLeftImageResource(R.mipmap.back_green);
//        titleBar.setLeftText("返回");
//        titleBar.setLeftTextColor(Color.WHITE);
//        titleBar.setLeftClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                leftNavBtnHandle();
//            }
//        });
//
//        titleBar.setTitle(getNavTitle());
//        titleBar.setTitleColor(Color.WHITE);
//        //titleBar.setSubTitleColor(Color.WHITE);
//        titleBar.setDividerColor(Color.GRAY);

//        titleBar.setActionTextColor(Color.WHITE);
//        mCollectView = (ImageView) titleBar.addAction(new TitleBar.ImageAction(R.mipmap.collect) {
//            @Override
//            public void performAction(View view) {
//                Toast.makeText(BaseActivity.this, "点击了收藏", Toast.LENGTH_SHORT).show();
//                mCollectView.setImageResource(R.mipmap.fabu);
//                titleBar.setTitle(mIsSelected ? "文章详情\n朋友圈" : "帖子详情");
//                mIsSelected = !mIsSelected;
//            }
//        });
//
//        titleBar.addAction(new TitleBar.TextAction("发布") {
//            @Override
//            public void performAction(View view) {
//                Toast.makeText(BaseActivity.this, "点击了发布", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    protected int getLayoutId() {
        return 0;
    }

    protected String getNavTitle(){
        return "";
    }

    protected void leftNavBtnHandle(){
        finish();
    }

    protected void init(Bundle savedInstanceState) {

    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @Override
    public WaitDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    @Override
    public WaitDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    @Override
    public WaitDialog showWaitDialog(String message) {
        if (_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelper.getWaitDialog(this, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }

    @Override
    public void hideWaitDialog() {
        if (_isVisible && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean isVisible() {
        return _isVisible;
    }

}
