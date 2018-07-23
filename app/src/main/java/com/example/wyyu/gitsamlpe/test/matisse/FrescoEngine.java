package com.example.wyyu.gitsamlpe.test.matisse;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.zhihu.matisse.engine.ImageEngine;

/**
 * Created by wyyu on 2018/7/23.
 **/

public class FrescoEngine implements ImageEngine {

    @Override public void loadThumbnail(Context context, int resize, Drawable placeholder,
        ImageView imageView, Uri uri) {
        FrescoLoader.newFrescoLoader()
            .resize(resize, resize)
            .placeHolder(placeholder)
            .uri(uri)
            .into(imageView);
    }

    @Override
    public void loadAnimatedGifThumbnail(Context context, int resize, Drawable placeholder,
        ImageView imageView, Uri uri) {

    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {

    }

    @Override
    public void loadAnimatedGifImage(Context context, int resizeX, int resizeY, ImageView imageView,
        Uri uri) {

    }

    @Override public boolean supportAnimatedGif() {
        return true;
    }
}
