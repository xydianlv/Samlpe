package com.example.wyyu.gitsamlpe.test.image.preview;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-01-14.
 **/

public class FragmentImage extends Fragment {

    private static final int CENTER_Y = UIUtils.getScreenHeight() / 2;
    private static final int CENTER_X = UIUtils.getScreenWidth() / 2;

    private static final int ANIM_VALUE = 900;
    private static final int ANIM_TIME = 300;

    private static final String KEY_ITEM_BEAN = "key_item_bean";

    public static Fragment getFragment(ItemBean itemBean) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_ITEM_BEAN, itemBean);
        FragmentImage fragment = new FragmentImage();
        fragment.setArguments(args);
        return fragment;
    }

    private ImageView imageView;
    private TextView imageIndex;
    private ItemBean itemBean;

    private ValueAnimator animator;
    private float divideHeight;
    private float divideWidth;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initValue();
    }

    @Override @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_preview_image, container, false);

        imageIndex = contentView.findViewById(R.id.preview_index);
        imageView = contentView.findViewById(R.id.preview_image);

        return contentView;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (itemBean != null && itemBean.imageBean != null) {
            imageView.setImageResource(itemBean.imageBean.resId);
        }
        if (itemBean != null) {
            imageIndex.setText(String.valueOf(itemBean.index));
        }
        if (itemBean != null && itemBean.imageLocation != null) {
            imageView.setX(itemBean.imageLocation.left);
            imageView.setY(itemBean.imageLocation.top);

            FrameLayout.LayoutParams params =
                (FrameLayout.LayoutParams) imageView.getLayoutParams();
            params.width = (int) itemBean.imageLocation.width;
            params.height = (int) itemBean.imageLocation.height;
        }

        //if (animator != null) {
        //    animator.start();
        //}
    }

    private void initValue() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            itemBean = (ItemBean) getArguments().getSerializable(KEY_ITEM_BEAN);
        } else {
            itemBean = null;
        }

        if (itemBean != null && itemBean.imageLocation != null) {
            divideHeight =
                CENTER_Y - (itemBean.imageLocation.height / 2 + itemBean.imageLocation.top);
            divideWidth =
                CENTER_X - (itemBean.imageLocation.width / 2 + itemBean.imageLocation.left);
        }

        animator = ValueAnimator.ofInt(ANIM_VALUE);
        animator.setDuration(ANIM_TIME);

        animator.addUpdateListener(animation -> {
            if (imageView == null || itemBean == null || itemBean.imageLocation == null) {
                return;
            }

            int value = (int) animation.getAnimatedValue();
            float y = divideHeight * (value * 1.0f / ANIM_VALUE) + itemBean.imageLocation.top;
            float x = divideWidth * (value * 1.0f / ANIM_VALUE) + itemBean.imageLocation.left;

            imageView.setY(y);
            imageView.setX(x);
        });
    }
}
