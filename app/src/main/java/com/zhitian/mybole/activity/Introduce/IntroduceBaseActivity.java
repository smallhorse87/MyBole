package com.zhitian.mybole.activity.Introduce;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.Introduce.adapter.PagerAdapter;
import com.zhitian.mybole.activity.Introduce.view.IndicatorController;

import java.util.List;
import java.util.Vector;

public abstract class IntroduceBaseActivity extends AppCompatActivity {
    protected PagerAdapter mPagerAdapter;
    protected ViewPager pager;
    protected List<Fragment> fragments = new Vector<>();
    protected int slidesNumber;
    protected IndicatorController mController;
    protected View skipButton;
    protected int savedCurrentItem;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_introduce);

        skipButton = findViewById(R.id.skip);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(this.mPagerAdapter);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                onSkipPressed();
            }
        });

        /**
         *  ViewPager.setOnPageChangeListener is now deprecated. Use addOnPageChangeListener() instead of it.
         */
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (slidesNumber > 1)
                    mController.selectPosition(position);

                setButtonState(skipButton, true);
                onSlideChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setCurrentItem(savedCurrentItem); //required for triggering onPageSelected for first page

        init(savedInstanceState);
        slidesNumber = fragments.size();

        if (slidesNumber > 0) {
            initIndicatorContainer();
        }
    }

    private void initIndicatorContainer() {
        if (mController == null)
            mController = new IndicatorController();

        FrameLayout indicatorContainer = (FrameLayout) findViewById(R.id.indicator_container);
        indicatorContainer.addView(mController.newInstance(this));

        mController.initialize(slidesNumber);

    }

    public void addSlide(@NonNull Fragment fragment) {
        fragments.add(fragment);
        mPagerAdapter.notifyDataSetChanged();
    }

    @NonNull
    public List<Fragment> getSlides() {
        return mPagerAdapter.getFragments();
    }

    private void setButtonState(View button, boolean show) {
        if (show) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.INVISIBLE);
        }
    }


    public abstract void init(@Nullable Bundle savedInstanceState);

    public abstract void onSkipPressed();

    public abstract void onDonePressed();

    public abstract void onSlideChanged();

    @Override
    public boolean onKeyDown(int code, KeyEvent kvent) {
        if (code == KeyEvent.KEYCODE_ENTER || code == KeyEvent.KEYCODE_BUTTON_A || code == KeyEvent.KEYCODE_DPAD_CENTER) {
            ViewPager vp = (ViewPager) this.findViewById(R.id.view_pager);
            if (vp.getCurrentItem() == vp.getAdapter().getCount() - 1) {
                onDonePressed();
            } else {
                vp.setCurrentItem(vp.getCurrentItem() + 1);
            }
            return false;
        }
        return super.onKeyDown(code, kvent);
    }


    /**
     * Allows for setting statusbar visibility (true by default)
     *
     * @param isVisible put true to show status bar, and false to hide it
     */
    public void showStatusBar(boolean isVisible) {
        if (!isVisible) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

}
