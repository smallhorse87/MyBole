package com.zhitian.mybole.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.Introduce.IntroduceActivity;
import com.zhitian.mybole.activity.login.LoginActivity;
import com.zhitian.mybole.activity.merchant.MerchantFormActivity;

import android.os.Handler;

public class SplashActivity extends Activity {
    ImageView mCollectView;
    Boolean mIsSelected;

    private static final String SPLASH_SCREEN = "SplashScreen";
    public static final int MAX_WATTING_TIME = 3000;// 停留时间3秒

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(SPLASH_SCREEN); // 统计页面
//        MobclickAgent.onResume(this); // 统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(SPLASH_SCREEN); // 保证 onPageEnd 在onPause
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectTo();
            }
        }, MAX_WATTING_TIME);


//        if (hasKitKat() && !hasLollipop()) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//        } else if (hasLollipop()) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }

//        final TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
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
//                finish();
//            }
//        });
//
//        titleBar.setTitle("文章详情");
//        titleBar.setTitleColor(Color.WHITE);
//        titleBar.setSubTitleColor(Color.WHITE);
//        titleBar.setDividerColor(Color.GRAY);
//
//        titleBar.setActionTextColor(Color.WHITE);
//        mCollectView = (ImageView) titleBar.addAction(new TitleBar.ImageAction(R.mipmap.collect) {
//            @Override
//            public void performAction(View view) {
//                Toast.makeText(SplashActivity.this, "点击了收藏", Toast.LENGTH_SHORT).show();
//                mCollectView.setImageResource(R.mipmap.fabu);
//                titleBar.setTitle(mIsSelected ? "文章详情\n朋友圈" : "帖子详情");
//                mIsSelected = !mIsSelected;
//            }
//        });
//
//        titleBar.addAction(new TitleBar.TextAction("发布") {
//            @Override
//            public void performAction(View view) {
//                Toast.makeText(SplashActivity.this, "点击了发布", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void redirectTo() {
        //Intent intent = new Intent(this, IntroduceActivity.class);
        Intent intent = new Intent(this, LoginActivity.class);
        //Intent intent = new Intent(this, MerchantFormActivity.class);
        startActivity(intent);
        finish();
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
