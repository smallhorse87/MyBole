package com.zhitian.mybole.activity.shareactivity;

import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhitian.mybole.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishActivity extends AppCompatActivity {

    @Bind(R.id.tv_change_image)
    TextView tvChangeImage;
    @Bind(R.id.ib_wechat_friend)
    ImageButton ibWechatFriend;
    @Bind(R.id.ib_wechat_circle)
    ImageButton ibWechatCircle;
    @Bind(R.id.iv_share_image)
    SimpleDraweeView ivShareImage;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_content_count)
    TextView tvContentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);

        String imageUrl = getIntent().getStringExtra("imageUrl");
        ivShareImage.setImageURI(Uri.parse(imageUrl));

        tvChangeImage.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        showLeftCount(etContent.getText());

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showLeftCount(s);
            }
        });
    }

    private void showLeftCount(Editable s){
        int leftCount = 200 - s.length();
        tvContentCount.setText(Integer.valueOf(leftCount).toString());
    }

    @OnClick({R.id.tv_change_image, R.id.ib_wechat_friend, R.id.ib_wechat_circle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_change_image:
                break;
            case R.id.ib_wechat_friend:
                break;
            case R.id.ib_wechat_circle:
                break;
        }
    }
}
