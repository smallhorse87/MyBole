package com.zhitian.mybole.activity.myactivities.adaptor;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhitian.mybole.R;
import com.zhitian.mybole.entity.GameInfo;
import com.zhitian.mybole.entity.ImageSetInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class GameHolder extends RecyclerView.ViewHolder {

    GameInfo gameInfo;

    @Bind(R.id.iv_game_image)
    SimpleDraweeView ivGameImage;
    @Bind(R.id.tv_game_name)
    TextView tvGameName;
    @Bind(R.id.tv_radio_unselected)
    TextView tvRadioUnselected;
    @Bind(R.id.iv_radio_selected)
    ImageView ivRadioSelected;
    @Bind(R.id.rl_radio)
    RelativeLayout rlRadio;

    RadioListener listener;

    GameHolder(View view, RadioListener listener) {
        super(view);
        ButterKnife.bind(this, view);

        this.listener = listener;
    }

    void setupContent(GameInfo info) {

        gameInfo = info;

        tvGameName.setText(info.getName());

        ImageSetInfo imageset = info.getImgs().get(0);
        ivGameImage.setImageURI(Uri.parse(imageset.getThumbnailImg().getUrl()));

        setRadio(gameInfo.getSelected());
    }

    @OnClick(R.id.rl_radio)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_radio: {
                radioSelected();
            }
            break;
        }
    }

    private void radioSelected() {
        if (gameInfo.getSelected() == true) return;

        gameInfo.setSelected(true);

        setRadio(gameInfo.getSelected());

        if (listener != null)
            listener.radioSelected(gameInfo);
    }

    private void setRadio(boolean selected) {
        tvRadioUnselected.setVisibility(View.GONE);
        ivRadioSelected.setVisibility(View.GONE);

        if (selected){
            ivRadioSelected.setVisibility(View.VISIBLE);
        } else {
            tvRadioUnselected.setVisibility(View.VISIBLE);
        }
    }

    public interface RadioListener {
        void radioSelected(GameInfo info);
    }
}