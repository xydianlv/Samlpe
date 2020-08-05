package com.example.wyyu.gitsamlpe.test.multi.custom.binder;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.multi.custom.data.CustomDataA;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import com.wyyu.multi.binder.HolderBinder;

/**
 * Created by wyyu on 2019-09-24.
 **/

public class CustomBinderA extends HolderBinder {

    @NonNull @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new HolderCustomA(onCreateView(parent, HolderCustomA.LAYOUT));
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull Object item) {
        ((HolderCustomA) holder).cacheValue((CustomDataA) item);

        Log.e("CustomListTest", "index -> " + ((CustomDataA) item).index);
    }

    @Override
    protected void updateItem(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull Object item,
        int updateType) {

    }

    private static class HolderCustomA extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_holder_custom_a;

        private ImageView imageView;
        private TextView textView;

        HolderCustomA(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.item_custom_text);
            imageView = itemView.findViewById(R.id.item_custom_image);

            LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(UIUtils.dpToPx(120.0f), UIUtils.dpToPx(120.0f));

            imageView.setOnClickListener(v -> imageView.setLayoutParams(layoutParams));
        }

        @SuppressLint("Assert") void cacheValue(CustomDataA dataA) {
            textView.setText(String.valueOf(dataA.index));
            imageView.setLayoutParams(
                new LinearLayout.LayoutParams(UIUtils.dpToPx(48.0f), UIUtils.dpToPx(48.0f)));
        }
    }
}
