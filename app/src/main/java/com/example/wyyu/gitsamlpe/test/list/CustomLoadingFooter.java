package com.example.wyyu.gitsamlpe.test.list;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;

/**
 * Created by wyyu on 2020-07-31.
 **/

public class CustomLoadingFooter extends InternalAbstract implements RefreshFooter {

    public static final String REFRESH_FOOTER_NOTHING = "到底了，没有更多了～";

    private SimpleDraweeView loadAnim;
    private TextView loadInfo;

    private Animatable webpAnim;

    public CustomLoadingFooter(Context context) {
        this(context, null);
        initView();
    }

    public CustomLoadingFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView();
    }

    public CustomLoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_loading_footer, this);

        loadAnim = findViewById(R.id.refresh_footer_anim);
        loadInfo = findViewById(R.id.refresh_footer_info);

        DraweeController controller = Fresco.newDraweeControllerBuilder()
            .setUri(Uri.parse("asset:///anim_loading_blue.webp"))
            .setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo,
                    @Nullable Animatable animatable) {
                    if (animatable != null) {
                        webpAnim = animatable;
                    }
                }
            })
            .build();
        loadAnim.setController(controller);

        loadInfo.setText(REFRESH_FOOTER_NOTHING);
    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        super.onInitialized(kernel, height, maxDragHeight);
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState,
        @NonNull RefreshState newState) {
        if (RefreshState.None.equals(newState)) {
            if (webpAnim != null && webpAnim.isRunning()) {
                webpAnim.stop();
            }
        } else {
            if (webpAnim != null && !webpAnim.isRunning()) {
                webpAnim.start();
            }
        }
    }

    @Override public boolean setNoMoreData(boolean noMoreData) {
        if (loadAnim != null) {
            loadAnim.setVisibility(noMoreData ? GONE : VISIBLE);
        }
        if (loadInfo != null) {
            loadInfo.setVisibility(noMoreData ? VISIBLE : GONE);
        }
        return true;
    }
}
