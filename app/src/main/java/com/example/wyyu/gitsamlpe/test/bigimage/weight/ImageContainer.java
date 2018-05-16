package com.example.wyyu.gitsamlpe.test.bigimage.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/5/8.
 **/

public class ImageContainer extends FrameLayout {

    public ImageContainer(@NonNull Context context) {
        super(context);
        initContainer();
    }

    public ImageContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initContainer();
    }

    public ImageContainer(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initContainer();
    }

    private ImageView imageView;

    private void initContainer() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_image_container, this);
        imageView = findViewById(R.id.image_container_main);
    }

    public void setImageValue(int imageId) {
        imageView.setImageResource(imageId);
    }
}
