package com.example.wyyu.gitsamlpe.framework.video.view;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.jzvd.JZUtils;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019-12-26.
 **/

public class TFullScreenPlayerControl extends LinearLayout {

    public TFullScreenPlayerControl(Context context) {
        super(context);
        initPlayerControl();
    }

    public TFullScreenPlayerControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPlayerControl();
    }

    public TFullScreenPlayerControl(Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPlayerControl();
    }

    private ImageView imageView;
    private TextView textPre;
    private TextView textNext;
    private ProgressBar progressBar;

    private void initPlayerControl() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_full_screen_player_control, this);

        imageView = findViewById(R.id.video_player_dialog_image);
        textPre = findViewById(R.id.video_player_dialog_text_pre);
        textNext = findViewById(R.id.video_player_dialog_text_next);
        progressBar = findViewById(R.id.video_player_dialog_progress);
    }

    /**
     * 刷新视频进度
     *
     * @param slideType 距离触屏时滑动的位移，0-回退播放，1-快进播放
     * @param position 滑动触发的播放，单位毫秒
     * @param duration 视频总时长，单位毫秒
     */
    public void showProgress(int slideType, long position, long duration) {
        setVisibility(VISIBLE);
        if (imageView != null) {
            imageView.setImageResource(
                slideType == 0 ? R.drawable.jz_backward_icon : R.drawable.jz_forward_icon);
        }
        if (textPre != null) {
            textPre.setText(JZUtils.stringForTime(position));
            textPre.setVisibility(VISIBLE);
        }
        if (textNext != null) {
            textNext.setText(String.format("/%s", JZUtils.stringForTime(duration)));
        }
        if (progressBar != null) {
            progressBar.setProgress((int) (100 * ((float) position / duration)));
        }
    }

    /**
     * 滑动调节的音频百分比
     *
     * @param position 百分比，取值 [0,100]
     */
    public void showVolume(int position) {
        setVisibility(VISIBLE);
        position = position < 0 ? 0 : (position > 100 ? 100 : position);
        if (imageView != null) {
            imageView.setImageResource(
                position <= 0 ? R.drawable.jz_close_volume : R.drawable.jz_add_volume);
        }
        if (textPre != null) {
            textPre.setVisibility(GONE);
        }
        if (textNext != null) {
            String percent = "" + position + "%";
            textNext.setText(percent);
        }
        if (progressBar != null) {
            progressBar.setProgress(position);
        }
    }

    /**
     * 滑动调节的亮度百分比
     *
     * @param position 百分比，取值 [0,100]
     */
    public void showBrightness(int position) {
        setVisibility(VISIBLE);
        position = position < 0 ? 0 : (position > 100 ? 100 : position);
        if (imageView != null) {
            imageView.setImageResource(R.drawable.jz_brightness_video);
        }
        if (textPre != null) {
            textPre.setVisibility(GONE);
        }
        if (textNext != null) {
            String percent = "" + position + "%";
            textNext.setText(percent);
        }
        if (progressBar != null) {
            progressBar.setProgress(position);
        }
    }

    /**
     * 触屏结束，隐藏 Dialog
     */
    public void hideDialog() {
        setVisibility(GONE);
    }
}
