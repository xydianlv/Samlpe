package com.example.wyyu.gitsamlpe.test.multi.custom.binder;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.aop.click.SingleClick;
import com.example.wyyu.gitsamlpe.test.multi.custom.data.CustomDataB;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import com.wyyu.multi.binder.HolderBinder;

/**
 * Created by wyyu on 2019-09-24.
 **/

public class CustomBinderB extends HolderBinder {

    @NonNull @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new CustomBinderB.HolderCustomB(
            onCreateView(parent, CustomBinderB.HolderCustomB.LAYOUT));
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull Object item) {
        ((CustomBinderB.HolderCustomB) holder).cacheValue((CustomDataB) item);

        Log.e("CustomListTest", "index -> " + ((CustomDataB) item).index);
    }

    @Override
    protected void updateItem(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull Object item,
        int updateType) {

    }

    private static class HolderCustomB extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_holder_custom_b;

        private TextView textView;
        private ImageView imageView;

        HolderCustomB(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.item_custom_text);
            imageView = itemView.findViewById(R.id.item_custom_image);

            imageView.setOnClickListener(v -> startAnim());
        }

        private void startAnim() {

            float divideWidth = 160.0f - 48.0f;
            float divideHeight = 120.0f - 48.0f;

            ValueAnimator animator = ValueAnimator.ofInt(0, 100);
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(320);
            animator.addUpdateListener(animation -> {
                float percent = (int) animation.getAnimatedValue() * 1.0f / 100;
                int currentWidth = UIUtils.dpToPx(percent * divideWidth + 48.0f);
                int currentHeight = UIUtils.dpToPx(percent * divideHeight + 48.0f);

                imageView.setLayoutParams(
                    new LinearLayout.LayoutParams(currentWidth, currentHeight));
            });

            animator.start();
        }

        void cacheValue(CustomDataB dataB) {
            textView.setText(String.valueOf(dataB.index));
            imageView.setLayoutParams(
                new LinearLayout.LayoutParams(UIUtils.dpToPx(48.0f), UIUtils.dpToPx(48.0f)));

            imageView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(time = 800L)
                @Override public void onClick(View v) {
                    Log.e("SingleClickAspectTest", "onClick");
                }
            });
        }
    }
}