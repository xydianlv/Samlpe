package com.example.wyyu.gitsamlpe.test.video.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2020-07-28.
 **/

public class CMBottomProgress extends LinearLayout {

    public CMBottomProgress(Context context) {
        super(context);
        initProgress();
    }

    public CMBottomProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initProgress();
    }

    public CMBottomProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initProgress();
    }

    public interface OnBottomBarListener {

        // 是否在滑动进度条
        void onTouchSeekBar(boolean touched, int progress);

        // 点击右下角图标，0-打开全屏，1-退出全屏
        void onClickIcon(int type);
    }

    private TextView textCurrent;
    private SeekBar seekBar;
    private TextView textDuration;
    private ImageView clickIcon;

    private OnBottomBarListener bottomBarListener;
    private boolean touchedSeekBar;
    private int userProgress;
    private int iconType;

    private void initProgress() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_cm_bottom_progress, this);

        initValue();
        initView();
        initClick();
    }

    private void initValue() {
        touchedSeekBar = false;
        iconType = 0;
    }

    private void initView() {
        textCurrent = findViewById(R.id.cm_bottom_progress_current);
        textDuration = findViewById(R.id.cm_bottom_progress_duration);
        seekBar = findViewById(R.id.cm_bottom_progress_seek);
        clickIcon = findViewById(R.id.cm_bottom_progress_icon);
    }

    private void initClick() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    userProgress = progress;
                }
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {
                if (bottomBarListener != null) {
                    bottomBarListener.onTouchSeekBar(true, 0);
                }
                touchedSeekBar = true;
            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {
                if (bottomBarListener != null) {
                    bottomBarListener.onTouchSeekBar(false, userProgress);
                }
                touchedSeekBar = false;
                userProgress = 0;
            }
        });

        clickIcon.setOnClickListener(v -> {
            if (bottomBarListener != null) {
                bottomBarListener.onClickIcon(iconType);
            }
        });
    }

    /**
     * 设置滑动条滑动监听
     *
     * @param bottomBarListener 监听器
     */
    public void setBottomBarListener(OnBottomBarListener bottomBarListener) {
        this.bottomBarListener = bottomBarListener;
    }

    /**
     * 为进度条设置数据
     *
     * @param videoDuration 视频总时长，单位为秒
     * @param iconType 图标类型，0-打开全屏，1-退出全屏
     */
    public void setProgressValue(int videoDuration, int iconType) {
        if (textDuration != null) {
            textDuration.setText(CMUtils.stringForTime(videoDuration));
        }
        if (clickIcon != null) {
            clickIcon.setImageResource(
                iconType == 0 ? R.mipmap.icon_cm_full_screen : R.mipmap.icon_cm_out_screen);
        }
        refreshProgress(0, 0);
        this.iconType = iconType;
    }

    /**
     * 刷新当前播放进度
     *
     * @param position 当前播放时长，单位为毫秒
     * @param progress 当前播放百分比，取值 [0,100]
     */
    public void refreshProgress(long position, int progress) {
        if (textCurrent != null) {
            textCurrent.setText(CMUtils.stringForTime(position));
        }
        if (seekBar != null && !touchedSeekBar) {
            seekBar.setProgress(progress);
        }
    }
}
