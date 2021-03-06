package com.zhitian.mybole.activity.Introduce;

import android.content.Intent;
import android.os.Bundle;

import com.zhitian.mybole.R;
import com.zhitian.mybole.activity.Introduce.view.SampleSlide;
import com.zhitian.mybole.activity.MainActivity;

public class IntroduceActivity extends IntroduceBaseActivity {

    public void init(Bundle savedInstanceState) {

        addSlide(SampleSlide.newInstance(R.layout.fragment_intro01));
        addSlide(SampleSlide.newInstance(R.layout.fragment_intro02));
        addSlide(SampleSlide.newInstance(R.layout.fragment_intro03));
        addSlide(SampleSlide.newInstance(R.layout.fragment_intro04));

        showStatusBar(false);
    }

    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged() {
        // Do something when the slide changes.
    }

}
