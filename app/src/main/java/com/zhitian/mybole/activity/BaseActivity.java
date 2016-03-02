package com.zhitian.mybole.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bacy.view.titlebar.TitleBar;
import com.zhitian.mybole.R;

public class BaseActivity extends Activity {
    ImageView mCollectView;
    Boolean mIsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }

        //setTranslucentStatus(true);
        setNavBar();
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
        final TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);

        if (titleBar == null)
            return;

        if (getNavTitle() == null || getNavTitle().length() == 0)
            titleBar.setVisibility(View.GONE);

        titleBar.setImmersive(true);

        titleBar.setBackgroundColor(Color.parseColor("#64b4ff"));

        titleBar.setLeftImageResource(R.mipmap.back_green);
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftNavBtnHandle();
            }
        });

        titleBar.setTitle(getNavTitle());
        titleBar.setTitleColor(Color.WHITE);
        //titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);

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
}
