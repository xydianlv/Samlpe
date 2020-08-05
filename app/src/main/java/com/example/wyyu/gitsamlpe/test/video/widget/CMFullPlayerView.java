package com.example.wyyu.gitsamlpe.test.video.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.WeakHandler;
import com.example.wyyu.gitsamlpe.framework.window.PressListenerView;
import com.example.wyyu.gitsamlpe.test.video.data.VideoItem;
import com.example.wyyu.gitsamlpe.test.video.player.CMVideoPlayer;
import com.example.wyyu.gitsamlpe.test.video.player.ICMVideoPlayer;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-07-28.
 **/

public class CMFullPlayerView extends FrameLayout implements ICMVideoPlayer.MediaPlayListener {

    public CMFullPlayerView(@NonNull Context context) {
        super(context);
        initPlayer();
    }

    public CMFullPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPlayer();
    }

    public CMFullPlayerView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPlayer();
    }

    // 全屏播放器 退出全屏事件 监听
    public interface OnFullScreenPlayListener {

        void onQuite();
    }

    // 展示播放按钮
    private static final int MSG_HIDE_START_SELECTED = 0;
    // 判断界面滑动
    private static final int MIN_VALUE_JUDGE_SLIDE = 40;
    // 屏幕高度
    private static final int SCREEN_HEIGHT = UIUtils.getScreenHeight();
    // 屏幕宽度
    private static final int SCREEN_WIDTH = UIUtils.getScreenWidth();
    // 进度条外可触碰区域
    private static final int DIVIDE_TOUCH = SCREEN_HEIGHT - UIUtils.dpToPx(120.0f);

    // 播放状态监听回调
    private OnFullScreenPlayListener playListener;

    // Texture 容器
    private FrameLayout textureContainer;
    // 开始按钮
    private ImageView buttonStart;
    // 加载动画
    private ImageView viewLoading;
    // 底部可拖动进度条
    private CMBottomProgress videoProgress;
    // 底部进度条
    private ProgressBar bottomProgress;
    // 快进快退展示
    private CMFullPlayerDialog playerDialog;

    // 播放器状态
    private @CMPlayerStatus int playerStatus;
    // 计时器，隐藏播放按钮
    private WeakHandler handler;
    // 加载动画
    private AnimationDrawable loadingDrawable;

    // 系统音量
    private AudioManager audioManager;
    // 最大音量值
    private int audioVolumeMax;

    // 是否在滑动进度
    private boolean onSlideProgress;
    // 是否在滑动音量
    private boolean onSlideVolume;
    // 是否在滑动亮度
    private boolean onSlideBrightness;
    // EventDown 事件的 X 坐标
    private float eventDownX;
    // EventDown 事件的 Y 坐标
    private float eventDownY;

    private void initPlayer() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_cm_full_player_view, this);

        initValue();
        initView();
    }

    private void initValue() {
        playerStatus = CMPlayerStatus.STATIC;
        handler = new WeakHandler(msg -> {
            if (msg.what == MSG_HIDE_START_SELECTED) {
                if (playerStatus == CMPlayerStatus.PLAYING_SHOW_ICON) {
                    playerStatus = CMPlayerStatus.PLAYING_HIDE_ICON;
                    refreshPlayerShow();
                }
            }
        });

        audioManager = (AudioManager) getContext().getApplicationContext()
            .getSystemService(Context.AUDIO_SERVICE);
        audioVolumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        onSlideProgress = false;
        onSlideVolume = false;
        onSlideBrightness = false;
    }

    private void initView() {
        textureContainer = findViewById(R.id.cm_full_player_texture_container);
        bottomProgress = findViewById(R.id.cm_full_player_progress_bottom);
        playerDialog = findViewById(R.id.cm_full_player_dialog);

        buttonStart = findViewById(R.id.cm_full_player_start);
        buttonStart.setOnClickListener(v -> {
            if (playerStatus == CMPlayerStatus.PAUSE_SHOW_ICON) {
                CMVideoPlayer.getPlayer().start();
            } else if (playerStatus == CMPlayerStatus.PLAYING_SHOW_ICON) {
                CMVideoPlayer.getPlayer().pause();
            }
        });

        videoProgress = findViewById(R.id.cm_full_player_progress);
        videoProgress.setBottomBarListener(new CMBottomProgress.OnBottomBarListener() {
            @Override public void onTouchSeekBar(boolean touched, int progress) {
                if (touched) {
                    if (handler != null) {
                        handler.removeMessages(MSG_HIDE_START_SELECTED);
                    }
                } else {
                    CMVideoPlayer.getPlayer().seekTo(progress);
                    if (handler != null) {
                        handler.sendEmptyMessageDelayed(MSG_HIDE_START_SELECTED, 3000);
                    }
                }
            }

            @Override public void onClickIcon(int type) {
                if (type == 1) {
                    detachPlayerShow();
                }
            }
        });

        viewLoading = findViewById(R.id.cm_full_player_loading);
        viewLoading.setImageResource(R.drawable.anim_video_loading);

        Drawable drawable = viewLoading.getDrawable();
        if (drawable instanceof AnimationDrawable) {
            loadingDrawable = (AnimationDrawable) drawable;
            loadingDrawable.setOneShot(false);
        }

        PressListenerView listenerView = findViewById(R.id.cm_full_player_press_listener);
        listenerView.setOnPressListener(new PressListenerView.OnPressListener() {
            @Override public boolean onPressDown() {
                return true;
            }

            @Override public boolean onPressUp() {
                if (playerStatus == CMPlayerStatus.PLAYING_HIDE_ICON) {
                    playerStatus = CMPlayerStatus.PLAYING_SHOW_ICON;
                    refreshPlayerShow();
                    if (handler != null) {
                        handler.removeMessages(MSG_HIDE_START_SELECTED);
                        handler.sendEmptyMessageDelayed(MSG_HIDE_START_SELECTED, 3000);
                    }
                    return true;
                }
                if (playerStatus == CMPlayerStatus.PLAYING_SHOW_ICON) {
                    playerStatus = CMPlayerStatus.PLAYING_HIDE_ICON;
                    refreshPlayerShow();
                    if (handler != null) {
                        handler.removeMessages(MSG_HIDE_START_SELECTED);
                    }
                    return true;
                }
                if (playerStatus == CMPlayerStatus.PAUSE_HIDE_ICON) {
                    playerStatus = CMPlayerStatus.PAUSE_SHOW_ICON;
                    refreshPlayerShow();
                    return true;
                }
                if (playerStatus == CMPlayerStatus.PAUSE_SHOW_ICON) {
                    playerStatus = CMPlayerStatus.PAUSE_HIDE_ICON;
                    refreshPlayerShow();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.cm_full_player_back).setOnClickListener(v -> detachPlayerShow());
    }

    /**
     * 刷新播放器布局展示
     */
    private void refreshPlayerShow() {
        boolean showSeek = playerStatus == CMPlayerStatus.PLAYING_SHOW_ICON
            || playerStatus == CMPlayerStatus.PAUSE_SHOW_ICON;
        boolean showProgress = playerStatus == CMPlayerStatus.PLAYING_HIDE_ICON
            || playerStatus == CMPlayerStatus.PAUSE_HIDE_ICON;
        boolean showStart = playerStatus == CMPlayerStatus.PLAYING_SHOW_ICON
            || playerStatus == CMPlayerStatus.PAUSE_SHOW_ICON;
        boolean startSelected = playerStatus == CMPlayerStatus.PLAYING_SHOW_ICON
            || playerStatus == CMPlayerStatus.PLAYING_HIDE_ICON
            || playerStatus == CMPlayerStatus.LOADING;

        videoProgress.setVisibility(showSeek ? VISIBLE : GONE);
        bottomProgress.setVisibility(showProgress ? VISIBLE : GONE);
        buttonStart.setVisibility(showStart ? VISIBLE : GONE);
        buttonStart.setSelected(startSelected);

        if (playerStatus == CMPlayerStatus.LOADING) {
            viewLoading.setVisibility(VISIBLE);
            if (loadingDrawable != null) {
                loadingDrawable.start();
            }
        } else {
            viewLoading.setVisibility(GONE);
            if (loadingDrawable != null) {
                loadingDrawable.stop();
            }
        }
    }

    private void slideProgress(float deltaX) {
        if (playerDialog == null) {
            return;
        }
        long duration = CMVideoPlayer.getPlayer().getDuration();
        long position = CMVideoPlayer.getPlayer().getPosition();

        playerDialog.showProgress(deltaX <= 0 ? 0 : 1,
            position + (long) (duration * (deltaX / SCREEN_WIDTH)), duration);
    }

    private boolean slideVolume(float deltaY) {
        int volumeValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float percent = -1.0f * deltaY / SCREEN_HEIGHT;
        int currentVolume = volumeValue + (int) (audioVolumeMax * percent);
        float currentPercent = (float) currentVolume / audioVolumeMax + percent;

        if (currentVolume >= audioVolumeMax) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioVolumeMax, 0);
            currentPercent = 1.0f;
        } else if (currentVolume <= 0) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            currentPercent = 0.0f;
        } else {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
        }
        if (playerDialog != null) {
            playerDialog.showVolume((int) (100 * currentPercent));
        }
        return currentVolume != volumeValue;
    }

    private void slideBrightness(float deltaY) {
        Window window = CMPlayerViewManager.getAppCompActivity(getContext()).getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        if (params.screenBrightness < 0) {
            try {
                // 通过 Settings.System 方法获取到的 screenBrightness 取值范围为 [0,255]
                params.screenBrightness =
                    (float) Settings.System.getInt(getContext().getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS) / 255;
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        }
        float result = params.screenBrightness + (-1.0f * deltaY / SCREEN_HEIGHT);

        if (result > 1.0f) {
            params.screenBrightness = 1.0f;
            result = 1.0f;
        } else if (result <= 0.01f) {
            // 这个地方设置为0的话，系统调为自动亮度，因此设置为 0.01f
            params.screenBrightness = 0.01f;
            result = 0.0f;
        } else {
            params.screenBrightness = result;
        }
        window.setAttributes(params);
        if (playerDialog != null) {
            playerDialog.showBrightness((int) (result * 100));
        }
    }

    @Override public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getRawY() >= DIVIDE_TOUCH) {
            return super.dispatchTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                eventDownX = event.getX();
                eventDownY = event.getY();
                onSlideProgress = false;
                onSlideVolume = false;
                onSlideBrightness = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getX() - eventDownX;
                float deltaY = event.getY() - eventDownY;
                if (onSlideProgress) {
                    slideProgress(deltaX);
                } else if (onSlideVolume) {
                    eventDownY = slideVolume(deltaY) ? eventDownY + deltaY : eventDownY;
                } else if (onSlideBrightness) {
                    slideBrightness(deltaY);
                    eventDownY = eventDownY + deltaY;
                } else if (Math.abs(deltaX) > MIN_VALUE_JUDGE_SLIDE) {
                    onSlideProgress = true;
                    slideProgress(deltaX);
                } else if (Math.abs(deltaY) > MIN_VALUE_JUDGE_SLIDE
                    && eventDownX > SCREEN_WIDTH / 2) {
                    onSlideVolume = true;
                    slideVolume(deltaY);
                    eventDownY = eventDownY + deltaY;
                } else if (Math.abs(deltaY) > MIN_VALUE_JUDGE_SLIDE
                    && eventDownX < SCREEN_WIDTH / 2) {
                    onSlideBrightness = true;
                    slideBrightness(deltaY);
                    eventDownY = eventDownY + deltaY;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!onSlideProgress && !onSlideVolume && !onSlideBrightness) {
                    break;
                }
                if (onSlideProgress && playerDialog != null) {
                    CMVideoPlayer.getPlayer().seekTo(playerDialog.getVideoPosition());
                }
                if (playerDialog != null) {
                    playerDialog.hideDialog();
                }
                return true;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override public void onStateChange(int status) {
        switch (status) {
            case ICMVideoPlayer.MediaStatus.COMPLETE:
                detachPlayerShow();
                break;
            case ICMVideoPlayer.MediaStatus.PLAYING:
                if (playerStatus == CMPlayerStatus.PAUSE_SHOW_ICON) {
                    playerStatus = CMPlayerStatus.PLAYING_SHOW_ICON;
                    if (handler != null) {
                        handler.sendEmptyMessageDelayed(MSG_HIDE_START_SELECTED, 3000);
                    }
                } else {
                    playerStatus = CMPlayerStatus.PLAYING_HIDE_ICON;
                }
                break;
            case ICMVideoPlayer.MediaStatus.LOADING:
                playerStatus = CMPlayerStatus.LOADING;
                break;
            case ICMVideoPlayer.MediaStatus.PAUSE:
                playerStatus = CMPlayerStatus.PAUSE_SHOW_ICON;
                break;
            case ICMVideoPlayer.MediaStatus.PREPARE:
                playerStatus = CMPlayerStatus.LOADING;
                break;
            case ICMVideoPlayer.MediaStatus.ERROR:
            case ICMVideoPlayer.MediaStatus.RESET:
                detachPlayerShow();
                break;
        }
        refreshPlayerShow();
        CMPlayerViewManager.judgeScreenOn(getContext(), status);
    }

    @Override public void onProgress(long position, int progress) {
        if (videoProgress != null) {
            videoProgress.refreshProgress(position, progress);
        }
        if (bottomProgress != null) {
            bottomProgress.setProgress(progress);
        }
    }

    /**
     * 从界面上移除全屏播放器
     */
    public void detachPlayerShow() {
        if (textureContainer != null) {
            textureContainer.removeAllViews();
        }
        Activity activity = CMPlayerViewManager.getAppCompActivity(getContext());
        if (activity == null) {
            return;
        }
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ViewGroup decorView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        View fullPlayer = decorView.findViewById(R.id.cm_player_id_fullscreen);
        if (fullPlayer != null) {
            decorView.removeView(fullPlayer);
        }

        CMVideoPlayer.getPlayer().detachPlayListener(this);
        CMPlayerViewManager.detachPlayer();

        if (playListener != null) {
            playListener.onQuite();
        }
    }

    /**
     * 传入可供该控件全屏播放的数据
     *
     * @param textureView 公用的 TextureView
     * @param videoItem 视频数据集合
     * @param playListener 全屏播放状态监听
     */
    public void attachPlayer(@NonNull TextureView textureView, @NonNull VideoItem videoItem,
        @CMPlayerStatus int playerStatus, @NonNull OnFullScreenPlayListener playListener) {
        if (textureContainer == null) {
            detachPlayerShow();
            return;
        }
        // 视频宽高比
        float aspectVideo = (float) videoItem.width / videoItem.height;
        // 屏幕宽高比
        float aspectWindow =
            aspectVideo > 1.0f ? (float) UIUtils.getScreenHeight() / UIUtils.getScreenWidth()
                : (float) UIUtils.getScreenWidth() / UIUtils.getScreenHeight();
        // 视频高度
        int videoHeight;
        // 视频宽度
        int videoWidth;

        if (aspectVideo > 1.0f) {
            if (aspectVideo > aspectWindow) {
                videoWidth = UIUtils.getScreenHeight();
                videoHeight = (int) (videoWidth / aspectVideo);
            } else {
                videoHeight = UIUtils.getScreenWidth();
                videoWidth = (int) (videoHeight * aspectVideo);
            }
        } else {
            if (aspectVideo > aspectWindow) {
                videoWidth = UIUtils.getScreenWidth();
                videoHeight = (int) (videoWidth / aspectVideo);
            } else {
                videoHeight = UIUtils.getScreenHeight();
                videoWidth = (int) (videoHeight * aspectVideo);
            }
        }

        LayoutParams params = new LayoutParams(videoWidth, videoHeight, Gravity.CENTER);
        this.textureContainer.removeAllViews();
        if (textureView.getParent() instanceof ViewGroup) {
            ((ViewGroup) textureView.getParent()).removeView(textureView);
        }
        this.textureContainer.addView(textureView, params);

        CMVideoPlayer.getPlayer().attachPlayListener(videoItem.id, this);
        CMPlayerViewManager.attachPlayer(this);

        if (playerStatus == CMPlayerStatus.PLAYING_SHOW_ICON) {
            this.playerStatus = CMPlayerStatus.PLAYING_HIDE_ICON;
        } else {
            this.playerStatus = playerStatus;
        }
        refreshPlayerShow();

        if (videoProgress != null) {
            videoProgress.setProgressValue((int) videoItem.duration, 1);
        }

        this.playListener = playListener;
    }
}
