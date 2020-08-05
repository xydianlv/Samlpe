package com.example.wyyu.gitsamlpe.test.video.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.WeakHandler;
import com.example.wyyu.gitsamlpe.framework.video.widget.FrameLayoutOffset;
import com.example.wyyu.gitsamlpe.framework.window.PressListenerView;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.example.wyyu.gitsamlpe.test.video.data.VideoItem;
import com.example.wyyu.gitsamlpe.test.video.player.CMVideoPlayer;
import com.example.wyyu.gitsamlpe.test.video.player.ICMVideoPlayer;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by wyyu on 2020-07-27.
 **/

public class CMVideoPlayerView extends FrameLayoutOffset
    implements ICMVideoPlayer.MediaPlayListener {

    // 播放器最大宽高比，超过这个比例用高斯模糊覆盖
    private static final float MAX_ASPECT_WIDTH_HEIGHT = (float) 16 / 9;
    // 判断自动播放时，屏幕上下的扩展判断高度
    private static final int AUTO_PLAY_HEIGHT = UIUtils.dpToPx(55.0f);
    // 展示播放按钮
    private static final int MSG_HIDE_START_SELECTED = 0;

    public CMVideoPlayerView(@NonNull Context context) {
        super(context);
        initPlayerView();
    }

    public CMVideoPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPlayerView();
    }

    public CMVideoPlayerView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPlayerView();
    }

    // 播放器播放状态监听
    public interface OnPlayerStatusListener {

        // 播放状态
        void onStatus(@CMPlayerStatus int playerStatus);
    }

    // 播放器触摸回调监听
    public interface OnPlayerTouchListener {

        // 向外回调的触摸事件类型，0-长按事件
        void onTouch(int type);
    }

    // 播放状态监听
    private OnPlayerStatusListener statusListener;
    // 触屏监听
    private OnPlayerTouchListener touchListener;

    // 视频播放器根布局
    private FrameLayout rootLayout;
    // 底部视频信息 时长、播放次数 信息
    private FrameLayout infoContainer;
    // 视频播放控件布局
    private FrameLayout textureContainer;
    // 视频播放控件，需动态生成，可与全屏和小窗之间做切换
    private TextureView textureView;
    // 底部包含 播放进度、SeekBar、全屏按钮 的控件
    private CMBottomProgress videoProgress;
    // 最底部进度条
    private ProgressBar bottomProgress;
    // 视频播放结束重播按钮
    private ImageView buttonReplay;
    // 开始按钮
    private ImageView buttonStart;
    // 视频加载图片
    private ImageView viewLoading;

    // 封面高斯模糊
    private SimpleDraweeView coverThumb;
    // 视频封面
    private SimpleDraweeView coverImage;
    // 播放次数
    private TextView playCount;
    // 视频时长
    private TextView duration;

    // 视频播放控件当前的展示状态
    private @CMPlayerStatus int playerStatus;
    // 开始、暂停按钮切换动画
    private AnimationDrawable loadingDrawable;
    // 控制开始按钮三秒后隐藏
    private WeakHandler handler;
    // 视频信息
    private VideoItem videoItem;

    // 外部在初始化播放器数据时，传入的播放器在父布局中的宽度
    // 皮皮项目中所有视频播放相关的布局，都是以横向充满屏幕为基准，所以设置好播放器的宽度尺寸即可
    private int fixedSize;
    // 是否展示加载中，隐藏加载中图标，可以让自动播放体验更好
    private boolean showLoading;

    // 记录播放器在界面上的位置
    private int[] location;
    // 用来判断播放器在界面位置的数据，为播放器的高度的一半
    private int heightSize;
    // 距离屏幕顶部的扩展高度
    private int divideHeightTop;
    // 距离屏幕底部的扩展高度
    private int divideHeightBottom;
    // 播放器当前所处位置是否可播放视频，要求至少有一半区域在屏幕内
    private boolean canPlay;

    private void initPlayerView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_cm_video_player_view, this);

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

        divideHeightBottom = 0;
        divideHeightTop = 0;
        showLoading = true;
        location = new int[2];
    }

    private void initView() {
        rootLayout = findViewById(R.id.cm_player_root);
        textureContainer = findViewById(R.id.cm_player_texture_container);
        infoContainer = findViewById(R.id.cm_player_info_container);
        bottomProgress = findViewById(R.id.cm_player_progress_bottom);
        buttonReplay = findViewById(R.id.cm_player_replay);

        coverThumb = findViewById(R.id.cm_player_cover_thumb);
        coverImage = findViewById(R.id.cm_player_cover_image);
        playCount = findViewById(R.id.cm_player_info_count);
        duration = findViewById(R.id.cm_player_info_duration);

        videoProgress = findViewById(R.id.cm_player_progress);
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
                if (type == 0) {
                    startFullScreenShow();
                }
            }
        });

        buttonReplay = findViewById(R.id.cm_player_replay);
        buttonReplay.setOnClickListener(v -> CMVideoPlayer.getPlayer().reStart());

        buttonStart = findViewById(R.id.cm_player_start);
        buttonStart.setOnClickListener(v -> {
            if (playerStatus == CMPlayerStatus.STATIC) {
                startVideoPlay();
            } else if (playerStatus == CMPlayerStatus.PAUSE_SHOW_ICON) {
                CMVideoPlayer.getPlayer().start();
            } else if (playerStatus == CMPlayerStatus.PLAYING_SHOW_ICON) {
                CMVideoPlayer.getPlayer().pause();
            }
        });
        buttonStart.setSelected(false);

        viewLoading = findViewById(R.id.cm_player_loading);
        viewLoading.setImageResource(R.drawable.anim_video_loading);

        Drawable drawable = viewLoading.getDrawable();
        if (drawable instanceof AnimationDrawable) {
            loadingDrawable = (AnimationDrawable) drawable;
            loadingDrawable.setOneShot(false);
        }

        PressListenerView listenerView = findViewById(R.id.cm_player_press_listener);
        listenerView.setOnPressListener(new PressListenerView.OnPressListener() {
            @Override public boolean onPressDown() {
                // 这里返回 false 的话，onPressUp 无法接收回调
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
                if (playerStatus == CMPlayerStatus.STATIC) {
                    startVideoPlay();
                    return true;
                }
                if (playerStatus == CMPlayerStatus.COMPLETE) {
                    CMVideoPlayer.getPlayer().reStart();
                    return true;
                }
                return false;
            }
        });
        listenerView.setOnLongClickListener(v -> {
            if (touchListener != null) {
                touchListener.onTouch(0);
                return true;
            }
            return false;
        });

        rootLayout.setOnLongClickListener(v -> {
            if (touchListener != null) {
                touchListener.onTouch(0);
                return true;
            }
            return false;
        });
    }

    private void startVideoPlay() {
        if (videoItem == null) {
            return;
        }
        CMVideoPlayer.getPlayer()
            .setDataAndPlay(videoItem.sourceType, videoItem.sourceValue, videoItem.id, textureView,
                this);
    }

    private void refreshPlayerShow() {
        boolean showTexture = playerStatus == CMPlayerStatus.PLAYING_SHOW_ICON
            || playerStatus == CMPlayerStatus.LOADING
            || playerStatus == CMPlayerStatus.PLAYING_HIDE_ICON
            || playerStatus == CMPlayerStatus.PAUSE_HIDE_ICON
            || playerStatus == CMPlayerStatus.PAUSE_SHOW_ICON;
        boolean showInfo =
            playerStatus == CMPlayerStatus.STATIC || playerStatus == CMPlayerStatus.PAUSE_HIDE_ICON;
        boolean showSeek = playerStatus == CMPlayerStatus.PLAYING_SHOW_ICON
            || playerStatus == CMPlayerStatus.PAUSE_SHOW_ICON;
        boolean showProgress = playerStatus == CMPlayerStatus.PLAYING_HIDE_ICON
            || playerStatus == CMPlayerStatus.PAUSE_HIDE_ICON;
        boolean showStart = playerStatus == CMPlayerStatus.STATIC
            || playerStatus == CMPlayerStatus.PLAYING_SHOW_ICON
            || playerStatus == CMPlayerStatus.PAUSE_SHOW_ICON;
        boolean startSelected = playerStatus == CMPlayerStatus.PLAYING_SHOW_ICON
            || playerStatus == CMPlayerStatus.PLAYING_HIDE_ICON
            || playerStatus == CMPlayerStatus.LOADING;

        textureContainer.setVisibility(showTexture ? VISIBLE : GONE);
        infoContainer.setVisibility(showInfo ? VISIBLE : GONE);
        buttonReplay.setVisibility(playerStatus == CMPlayerStatus.COMPLETE ? VISIBLE : GONE);
        videoProgress.setVisibility(showSeek ? VISIBLE : GONE);
        bottomProgress.setVisibility(showProgress ? VISIBLE : GONE);
        buttonStart.setVisibility(showStart ? VISIBLE : GONE);
        buttonStart.setSelected(startSelected);
        Drawable drawable = buttonStart.getDrawable();
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }

        if (playerStatus == CMPlayerStatus.LOADING && showLoading) {
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

        if (statusListener != null) {
            statusListener.onStatus(playerStatus);
        }
    }

    @Override public void offsetTopAndBottom(int offset) {
        super.offsetTopAndBottom(offset);
        rootLayout.getLocationOnScreen(location);
        canPlay = location[1] - AUTO_PLAY_HEIGHT - divideHeightTop >= -heightSize
            && location[1] + heightSize + AUTO_PLAY_HEIGHT + divideHeightBottom
            <= UIUtils.getScreenHeight();

        if (videoItem == null || CMVideoPlayer.getPlayer().getSourceId() != videoItem.id) {
            return;
        }

        // 当前播放的视频滑出了屏幕范围，停止播放
        if (!canPlay) {
            CMVideoPlayer.getPlayer().complete();
        }
    }

    @Override public void onStateChange(int status) {
        switch (status) {
            case ICMVideoPlayer.MediaStatus.COMPLETE:
                playerStatus = CMPlayerStatus.COMPLETE;
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
                playerStatus = CMPlayerStatus.STATIC;
                break;
            case ICMVideoPlayer.MediaStatus.ERROR:
            case ICMVideoPlayer.MediaStatus.RESET:
                playerStatus = CMPlayerStatus.STATIC;
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
     * 传入待播放数据，并启动播放，同时带有一个尺寸动画
     * 目前只支持方形图片点击播放 < 神评卡片 >，宽高自适应卡片支持待开发
     *
     * @param videoItem 视频数据
     * @param paramArray 尺寸动画数据，0 - 动画开始时播放器宽度，1 - 动画结束后的播放器宽度，单位为 px
     */
    public void setPlayerValueWidthAnimPlay(VideoItem videoItem, int[] paramArray) {
        fixedSize = paramArray == null || paramArray.length < 2 ? 0 : paramArray[0];
        setPlayerValue(videoItem, fixedSize);
        if (paramArray == null || paramArray.length < 2) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofInt(0, 120);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(240);

        int divideWidth = paramArray[1] - paramArray[0];
        animator.addUpdateListener(animation -> {
            float percent = (int) animation.getAnimatedValue() * 1.0f / 120;
            fixedSize = (int) (percent * divideWidth + paramArray[0]);

            setPlayerValue(videoItem, fixedSize);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startVideoPlay();
            }
        });
        animator.start();
    }

    /**
     * 设置屏幕上下扩展间距，用做自动播放判断逻辑
     *
     * @param divideHeightBottom 底部扩展间距
     * @param divideHeightTop 顶部扩展间距
     */
    public void setDivideValue(int divideHeightBottom, int divideHeightTop) {
        this.divideHeightBottom = divideHeightBottom;
        this.divideHeightTop = divideHeightTop;
    }

    /**
     * 传入视频数据
     *
     * @param videoItem 视频数据
     * @param fixedWidth 播放器在父布局中的宽度，必须大于零
     */
    public void setPlayerValue(VideoItem videoItem, int fixedWidth) {
        if (videoItem == null || videoItem.width <= 0 || videoItem.height <= 0 || fixedWidth <= 0) {
            setVisibility(GONE);
            return;
        }
        this.setVisibility(VISIBLE);
        this.videoItem = videoItem;
        this.fixedSize = fixedWidth;

        loadPlayerShow(videoItem);
    }

    /**
     * 加载各个布局和控件的展示
     *
     * @param videoItem 初始化了 ViewBean 的 videoBean
     */
    private void loadPlayerShow(VideoItem videoItem) {
        int[] sizeArray = loadPlayerSize(videoItem);
        if (sizeArray == null) {
            return;
        }
        loadCoverShow(videoItem, sizeArray);
        loadInfoShow(videoItem);

        videoProgress.setProgressValue((int) videoItem.duration, 0);
        loadTextureShow(videoItem, sizeArray);

        playerStatus = CMPlayerStatus.STATIC;
        refreshPlayerShow();
    }

    private void loadCoverShow(VideoItem videoItem, int[] sizeArray) {

        Uri uri = videoItem.uri();

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
            .setResizeOptions(new ResizeOptions(30, 30))
            .setPostprocessor(new IterativeBoxBlurPostProcessor(1, 30))
            .build();
        GenericDraweeHierarchy hierarchy = coverThumb.getHierarchy();
        if (hierarchy != null) {
            hierarchy.setPlaceholderImage(CMUtils.getImageHolderDrawable());
        }

        //FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) coverImage.getLayoutParams();
        switch (sizeArray[0]) {
            case CMPlayerType.HEIGHT_THAN_WIDTH:
                coverThumb.setVisibility(VISIBLE);
                coverThumb.setImageRequest(imageRequest);
                break;
            case CMPlayerType.WIDTH_THAN_HEIGHT:
                coverThumb.setVisibility(GONE);
                break;
            case CMPlayerType.WIDTH_THAN_MIN_HEIGHT:
                coverThumb.setVisibility(VISIBLE);
                coverThumb.setImageRequest(imageRequest);
                break;
        }

        FrescoLoader.newFrescoLoader()
            .uri(uri)
            .imageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
            .into(coverImage);
    }

    private void loadInfoShow(VideoItem videoItem) {
        if (videoItem == null) {
            return;
        }
        if (duration != null) {
            duration.setText(CMUtils.getVideoFormatDurationBy(videoItem.duration * 1000));
        }
        if (playCount != null) {
            playCount.setText(String.format("%s 次播放", CMUtils.getNumStyle(videoItem.playCount)));
        }
    }

    /**
     * 获取播放器尺寸相关数据
     *
     * @param videoItem 视频原有数据
     * @return 数据列表，0-视频类型，1-视频宽度，2-视频高度，3-播放器宽度，4-播放器高度
     */
    private @Nullable int[] loadPlayerSize(VideoItem videoItem) {
        if (videoItem == null) {
            return null;
        }
        // 播放器的宽高
        int playerWidth = fixedSize;
        int playerHeight = fixedSize;
        // 播放器中视频的宽高
        int videoWidth = fixedSize;
        int videoHeight = fixedSize;

        int videoType = CMPlayerType.HEIGHT_THAN_WIDTH;
        float aspectWidthHeight = (float) videoItem.width / videoItem.height;

        if (aspectWidthHeight > MAX_ASPECT_WIDTH_HEIGHT) {
            videoHeight = (int) ((float) videoWidth / aspectWidthHeight);
            playerHeight = (int) ((float) playerWidth / MAX_ASPECT_WIDTH_HEIGHT);
            videoType = CMPlayerType.WIDTH_THAN_MIN_HEIGHT;
        } else if (aspectWidthHeight > 1.0f) {
            videoHeight = (int) ((float) videoWidth / aspectWidthHeight);
            playerHeight = (int) ((float) playerWidth / aspectWidthHeight);
            videoType = CMPlayerType.WIDTH_THAN_HEIGHT;
        } else {
            videoWidth = (int) ((float) videoHeight * aspectWidthHeight);
        }

        // 这里需手动设置一次，否则控件复用可能导致封面展示出错
        rootLayout.setLayoutParams(new LayoutParams(playerWidth, playerHeight));
        heightSize = playerHeight / 2;

        return new int[] { videoType, videoWidth, videoHeight, playerWidth, playerHeight };
    }

    /**
     * 加载视频播放控件
     *
     * @param videoItem 视频相关数据
     * @param sizeArray 视频尺寸数据
     */
    private void loadTextureShow(VideoItem videoItem, @Nullable int[] sizeArray) {
        if (textureView == null) {
            textureView = new TextureView(getContext());
        }
        if (sizeArray == null) {
            sizeArray = loadPlayerSize(videoItem);
        }
        if (textureContainer != null && sizeArray != null) {
            textureContainer.removeAllViews();
            textureContainer.addView(textureView,
                new LayoutParams(sizeArray[1], sizeArray[2], Gravity.CENTER));
        }
    }

    /**
     * 启动全屏播放
     */
    private void startFullScreenShow() {
        AppCompatActivity activity = CMPlayerViewManager.getAppCompActivity(getContext());
        if (activity == null || textureView == null || videoItem == null) {
            return;
        }
        activity.getWindow()
            .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (videoItem.width > videoItem.height) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        CMFullPlayerView fullScreenPlayer = new CMFullPlayerView(activity);
        fullScreenPlayer.setId(R.id.cm_player_id_fullscreen);
        FrameLayout.LayoutParams params =
            new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        ViewGroup decorView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        View lastPlayer = decorView.findViewById(R.id.cm_player_id_fullscreen);
        if (lastPlayer != null) {
            decorView.removeView(lastPlayer);
        }
        decorView.addView(fullScreenPlayer, params);

        textureContainer.removeAllViews();
        fullScreenPlayer.attachPlayer(textureView, videoItem, playerStatus, () -> {
            loadTextureShow(videoItem, null);
            if (handler != null && (playerStatus == CMPlayerStatus.PLAYING_HIDE_ICON
                || playerStatus == CMPlayerStatus.PLAYING_SHOW_ICON)) {
                handler.removeMessages(MSG_HIDE_START_SELECTED);
                handler.sendEmptyMessageDelayed(MSG_HIDE_START_SELECTED, 3000);
            }
            if (playerStatus == CMPlayerStatus.PAUSE_HIDE_ICON) {
                playerStatus = CMPlayerStatus.PAUSE_SHOW_ICON;
                refreshPlayerShow();
            }
        });
    }

    /**
     * 自动播放
     */
    public boolean tryAutoPlay() {
        if (!canPlay || videoItem == null || getVisibility() != VISIBLE) {
            return false;
        }
        if (CMVideoPlayer.getPlayer().getSourceId() == videoItem.id) {
            if (playerStatus != CMPlayerStatus.PLAYING_SHOW_ICON
                && playerStatus != CMPlayerStatus.PLAYING_HIDE_ICON) {
                CMVideoPlayer.getPlayer().start();
            }
            return true;
        }
        startVideoPlay();
        return true;
    }

    /**
     * 预加载当前视频数据到缓存
     *
     * @return 是否成功缓存
     */
    public boolean tryPreloadPlay() {
        if (videoItem == null) {
            return false;
        }
        CMVideoPlayer.getPlayer()
            .preloadPlay(videoItem.sourceType, videoItem.sourceValue, videoItem.id);
        return true;
    }

    /**
     * 初始化触屏监听
     *
     * @param touchListener 监听回调
     */
    public void setPlayerTouchListener(OnPlayerTouchListener touchListener) {
        this.touchListener = touchListener;
    }

    /**
     * 初始化播放状态监听
     *
     * @param statusListener 监听回调
     */
    public void setPlayerStatusListener(OnPlayerStatusListener statusListener) {
        this.statusListener = statusListener;
    }
}
