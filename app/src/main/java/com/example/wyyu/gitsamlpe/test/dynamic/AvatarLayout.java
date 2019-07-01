package com.example.wyyu.gitsamlpe.test.dynamic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2019-07-01.
 **/
public class AvatarLayout extends FrameLayout {

    public AvatarLayout(@NonNull Context context) {
        this(context, null);
    }

    public AvatarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAvatarLayout();
    }

    private static final float IMG_DIVIDE_FRAME = (float) 3 / 4;
    private static final int SMALL_PADDING = UIUtils.dpToPx(5.0f);
    private static final int MARGIN_VALUE = UIUtils.dpToPx(2.0f);
    private static final int BASIC_SIZE = UIUtils.dpToPx(52.0f);

    private View iconLayout;
    private View avatarImg;
    private View iconPlay;

    private void initAvatarLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_avatar_layout, this);

        iconLayout = findViewById(R.id.avatar_layout_icon_container);
        avatarImg = findViewById(R.id.avatar_layout_img);
        iconPlay = findViewById(R.id.avatar_layout_icon_play);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        adaptShow(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    private void adaptShow(int width, int height) {

        int heightSpace = (int) (height - height * IMG_DIVIDE_FRAME) / 2;
        int widthSpace = (int) (width - width * IMG_DIVIDE_FRAME) / 2;

        avatarImg.setPadding(widthSpace, heightSpace, widthSpace, heightSpace);
        iconLayout.setPadding(widthSpace - MARGIN_VALUE, heightSpace - MARGIN_VALUE,
            widthSpace - MARGIN_VALUE, heightSpace - MARGIN_VALUE);

        // 小的头像用小的图标，大的头像用大一号的图标
        if (width < BASIC_SIZE) {
            iconPlay.setPadding(SMALL_PADDING, SMALL_PADDING, 0, 0);
        } else {
            iconPlay.setPadding(0, 0, 0, 0);
        }
    }
}
