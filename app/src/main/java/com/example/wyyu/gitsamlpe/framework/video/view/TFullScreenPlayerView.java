package com.example.wyyu.gitsamlpe.framework.video.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.wyyu.gitsamlpe.framework.video.data.VideoBean;
import com.example.wyyu.gitsamlpe.framework.video.player.ITVideoPlayer;
import com.example.wyyu.gitsamlpe.framework.video.player.TVideoPlayer;
import com.example.wyyu.gitsamlpe.framework.video.widget.PlayerViewProgress;
import com.example.wyyu.gitsamlpe.framework.window.PressListenerView;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2019-12-26.
 **/

public class TFullScreenPlayerView extends FrameLayout implements ITVideoPlayer.MediaPlayListener {

    public TFullScreenPlayerView(@NonNull Context context) {
        super(context);
        initPlayerView();
    }

    public TFullScreenPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPlayerView();
    }

    public TFullScreenPlayerView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPlayerView();
    }

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

    private FrameLayout textureContainer;
    private ImageView buttonStart;
    private ImageView viewLoading;
    private PlayerViewProgress videoProgress;
    private ProgressBar bottomProgress;
    private TFullScreenPlayerControl playerControl;

    private @PlayerViewStatus int playerStatus;
    private WeakHandler handler;
    private AnimationDrawable loadingDrawable;
    private OnFullScreenPlayListener playListener;

    private AudioManager audioManager;
    private int audioVolumeMax;

    private boolean onSlideProgress;
    private boolean onSlideVolume;
    private boolean onSlideBrightness;
    private float eventDownX;
    private float eventDownY;

    private void initPlayerView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_full_screen_player_view, this);

        initValue();
        initView();
    }

    private void initValue() {
        playerStatus = PlayerViewStatus.STATIC;
        handler = new WeakHandler(msg -> {
            if (msg.what == MSG_HIDE_START_SELECTED) {
                if (playerStatus == PlayerViewStatus.PLAYING_SHOW_ICON) {
                    playerStatus = PlayerViewStatus.PLAYING_HIDE_ICON;
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
        textureContainer = findViewById(R.id.full_player_texture_container);
        bottomProgress = findViewById(R.id.full_player_progress_bottom);
        playerControl = findViewById(R.id.full_player_control);

        buttonStart = findViewById(R.id.full_player_start);
        buttonStart.setOnClickListener(v -> {
            if (playerStatus == PlayerViewStatus.PAUSE_SHOW_ICON) {
                TVideoPlayer.getPlayer().start();
            } else if (playerStatus == PlayerViewStatus.PLAYING_SHOW_ICON) {
                TVideoPlayer.getPlayer().pause();
            }
        });

        videoProgress = findViewById(R.id.full_player_progress);
        videoProgress.setBottomBarListener(new PlayerViewProgress.OnBottomBarListener() {
            @Override public void onTouchSeekBar(boolean touched, int progress) {
                if (touched) {
                    if (handler != null) {
                        handler.removeMessages(MSG_HIDE_START_SELECTED);
                    }
                } else {
                    TVideoPlayer.getPlayer().seekTo(progress);
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

        viewLoading = findViewById(R.id.full_player_loading);
        viewLoading.setImageResource(R.drawable.anim_video_loading);
        Drawable drawable = viewLoading.getDrawable();
        if (drawable instanceof AnimationDrawable) {
            loadingDrawable = (AnimationDrawable) drawable;
            loadingDrawable.setOneShot(false);
        }

        PressListenerView listenerView = findViewById(R.id.full_player_press_listener);
        listenerView.setOnPressListener(new PressListenerView.OnPressListener() {
            @Override public boolean onPressDown() {
                return true;
            }

            @Override public boolean onPressUp() {
                if (playerStatus == PlayerViewStatus.PLAYING_HIDE_ICON) {
                    playerStatus = PlayerViewStatus.PLAYING_SHOW_ICON;
                    refreshPlayerShow();
                    if (handler != null) {
                        handler.removeMessages(MSG_HIDE_START_SELECTED);
                        handler.sendEmptyMessageDelayed(MSG_HIDE_START_SELECTED, 3000);
                    }
                    return true;
                }
                if (playerStatus == PlayerViewStatus.PLAYING_SHOW_ICON) {
                    playerStatus = PlayerViewStatus.PLAYING_HIDE_ICON;
                    refreshPlayerShow();
                    if (handler != null) {
                        handler.removeMessages(MSG_HIDE_START_SELECTED);
                    }
                    return true;
                }
                if (playerStatus == PlayerViewStatus.PAUSE_HIDE_ICON) {
                    playerStatus = PlayerViewStatus.PAUSE_SHOW_ICON;
                    refreshPlayerShow();
                    return true;
                }
                if (playerStatus == PlayerViewStatus.PAUSE_SHOW_ICON) {
                    playerStatus = PlayerViewStatus.PAUSE_HIDE_ICON;
                    refreshPlayerShow();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.full_player_back).setOnClickListener(v -> detachPlayerShow());
    }

    private void slideProgress(float deltaX) {
        if (playerControl == null) {
            return;
        }
        long duration = TVideoPlayer.getPlayer().getDuration();
        long position = TVideoPlayer.getPlayer().getCurrentPosition();

        playerControl.showProgress(deltaX <= 0 ? 0 : 1,
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
        if (playerControl != null) {
            playerControl.showVolume((int) (100 * currentPercent));
        }
        return currentVolume != volumeValue;
    }

    private void slideBrightness(float deltaY) {
        Window window = PlayerViewManager.getAppCompActivity(getContext()).getWindow();
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
        if (playerControl != null) {
            playerControl.showBrightness((int) (result * 100));
        }
    }

    @Override public boolean dispatchTouchEvent(MotionEvent event) {
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
                if (playerControl != null) {
                    playerControl.hideDialog();
                }
                return true;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override public void onStateChange(int status) {
        switch (status) {
            case ITVideoPlayer.MediaStatus.COMPLETE:
                detachPlayerShow();
                break;
            case ITVideoPlayer.MediaStatus.PLAYING:
                if (playerStatus == PlayerViewStatus.PAUSE_SHOW_ICON) {
                    playerStatus = PlayerViewStatus.PLAYING_SHOW_ICON;
                    if (handler != null) {
                        handler.sendEmptyMessageDelayed(MSG_HIDE_START_SELECTED, 3000);
                    }
                } else {
                    playerStatus = PlayerViewStatus.PLAYING_HIDE_ICON;
                }
                break;
            case ITVideoPlayer.MediaStatus.LOADING:
                playerStatus = PlayerViewStatus.LOADING;
                break;
            case ITVideoPlayer.MediaStatus.PAUSE:
                playerStatus = PlayerViewStatus.PAUSE_SHOW_ICON;
                break;
            case ITVideoPlayer.MediaStatus.PREPARE:
                playerStatus = PlayerViewStatus.LOADING;
                break;
            case ITVideoPlayer.MediaStatus.ERROR:
                detachPlayerShow();
                break;
        }
        refreshPlayerShow();
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
        Activity activity = PlayerViewManager.getAppCompActivity(getContext());
        if (activity == null) {
            return;
        }
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ViewGroup decorView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        View fullPlayer = decorView.findViewById(R.id.video_player_id_fullscreen);
        if (fullPlayer != null) {
            decorView.removeView(fullPlayer);
        }

        TVideoPlayer.getPlayer().detachPlayListener(this);
        PlayerViewManager.detachPlayer();

        if (playListener != null) {
            playListener.onQuite();
        }
    }

    /**
     * 刷新播放器布局展示
     */
    private void refreshPlayerShow() {
        boolean showSeek = playerStatus == PlayerViewStatus.PLAYING_SHOW_ICON
            || playerStatus == PlayerViewStatus.PAUSE_SHOW_ICON;
        boolean showProgress = playerStatus == PlayerViewStatus.PLAYING_HIDE_ICON
            || playerStatus == PlayerViewStatus.PAUSE_HIDE_ICON;
        boolean showStart = playerStatus == PlayerViewStatus.PLAYING_SHOW_ICON
            || playerStatus == PlayerViewStatus.PAUSE_SHOW_ICON;
        boolean startSelected = playerStatus == PlayerViewStatus.PLAYING_SHOW_ICON
            || playerStatus == PlayerViewStatus.PLAYING_HIDE_ICON
            || playerStatus == PlayerViewStatus.LOADING;

        videoProgress.setVisibility(showSeek ? VISIBLE : GONE);
        bottomProgress.setVisibility(showProgress ? VISIBLE : GONE);
        buttonStart.setVisibility(showStart ? VISIBLE : GONE);
        buttonStart.setSelected(startSelected);

        if (playerStatus == PlayerViewStatus.LOADING) {
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

    /**
     * 传入可供该控件全屏播放的数据
     *
     * @param textureView 公用的 TextureView
     * @param videoBean 视频数据集合
     * @param playListener 全屏播放状态监听
     */
    public void attachPlayer(@NonNull TextureView textureView, @NonNull VideoBean videoBean,
        @PlayerViewStatus int playerStatus, @NonNull OnFullScreenPlayListener playListener) {
        if (textureContainer == null) {
            detachPlayerShow();
            return;
        }
        // 视频宽高比
        float aspectVideo = (float) videoBean.width / videoBean.height;
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
        this.textureContainer.addView(textureView, params);

        TVideoPlayer.getPlayer().attachPlayListener(videoBean.id, this);
        PlayerViewManager.attachPlayer(this);

        if (playerStatus == PlayerViewStatus.PLAYING_SHOW_ICON) {
            this.playerStatus = PlayerViewStatus.PLAYING_HIDE_ICON;
        } else {
            this.playerStatus = playerStatus;
        }
        refreshPlayerShow();

        if (videoProgress != null) {
            videoProgress.setProgressValue(videoBean.duration, 1);
        }

        this.playListener = playListener;
    }
}
