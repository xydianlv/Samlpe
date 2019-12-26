package com.example.wyyu.gitsamlpe.framework.video.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import cn.jzvd.JZUtils;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019-12-26.
 **/

public class PlayerViewProgress extends LinearLayout {

    public PlayerViewProgress(Context context) {
        super(context);
        initProgress();
    }

    public PlayerViewProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initProgress();
    }

    public PlayerViewProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        LayoutInflater.from(getContext()).inflate(R.layout.layout_player_view_progress, this);

        textCurrent = findViewById(R.id.video_progress_current);
        textDuration = findViewById(R.id.video_progress_duration);

        seekBar = findViewById(R.id.video_progress_seek);
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

        clickIcon = findViewById(R.id.video_progress_icon);
        clickIcon.setOnClickListener(v -> {
            if (bottomBarListener != null) {
                bottomBarListener.onClickIcon(iconType);
            }
        });

        touchedSeekBar = false;
        iconType = 0;
    }

    /**
     * 为进度条设置数据
     *
     * @param videoDuration 视频总时长，单位为秒
     * @param iconType 图标类型，0-打开全屏，1-退出全屏
     */
    public void setProgressValue(int videoDuration, int iconType) {
        if (textDuration != null) {
            textDuration.setText(JZUtils.stringForTime(videoDuration * 1000));
        }
        if (clickIcon != null) {
            clickIcon.setImageResource(
                iconType == 0 ? R.drawable.jz_enlarge : cn.jzvd.R.drawable.jz_shrink);
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
            textCurrent.setText(JZUtils.stringForTime(position));
        }
        if (seekBar != null && !touchedSeekBar) {
            seekBar.setProgress(progress);
        }
    }

    /**
     * 设置滑动条滑动监听
     *
     * @param bottomBarListener 监听器
     */
    public void setBottomBarListener(OnBottomBarListener bottomBarListener) {
        this.bottomBarListener = bottomBarListener;
    }
}
