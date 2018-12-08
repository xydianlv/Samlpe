package com.example.wyyu.gitsamlpe.test.image.matisse;

import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhihu.matisse.internal.entity.Item;

/**
 * Created by wyyu on 2018/12/8.
 **/

public class LocalMediaAdapter extends RecyclerCursorAdapter {

    private static final int ITEM_CAPTURE = 1;
    private static final int ITEM_MEDIA = 0;

    private FrescoEngine frescoEngine;
    private Drawable mPlaceholder;

    LocalMediaAdapter() {
        super(null);

        frescoEngine = new FrescoEngine();

        TypedArray ta = AppController.getAppContext()
            .getTheme()
            .obtainStyledAttributes(new int[] { com.zhihu.matisse.R.attr.item_placeholder });
        mPlaceholder = ta.getDrawable(0);
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_CAPTURE) {
            return new CaptureHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_capture_view, parent, false));
        } else {
            return new ImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_local_image_view, parent, false));
        }
    }

    @Override protected void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        if (holder instanceof CaptureHolder) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onCapture();
                    }
                }
            });
            return;
        }

        ((ImageViewHolder) holder).bind(Item.valueOf(cursor));
    }

    @Override protected int getItemViewType(int position, Cursor cursor) {
        return Item.valueOf(cursor).isCapture() ? ITEM_CAPTURE : ITEM_MEDIA;
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        private AspectRatioFrameLayout frameLayout;
        private SimpleDraweeView simpleDraweeView;

        ImageViewHolder(View itemView) {
            super(itemView);

            simpleDraweeView = itemView.findViewById(R.id.local_image_item);
            frameLayout = itemView.findViewById(R.id.img_item_root);

            frameLayout.setAspectRatio(1.0f);
        }

        void bind(Item item) {
            frescoEngine.loadThumbnail(itemView.getContext(), 64, mPlaceholder, simpleDraweeView,
                item.uri);
        }
    }

    private class CaptureHolder extends RecyclerView.ViewHolder {

        CaptureHolder(View itemView) {
            super(itemView);
        }
    }

    private OnMediaClickListener clickListener;

    void setOnMediaClickListener(OnMediaClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnMediaClickListener {

        void onClickItem();

        void onCapture();
    }
}