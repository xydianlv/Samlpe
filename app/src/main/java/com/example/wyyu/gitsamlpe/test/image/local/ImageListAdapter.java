package com.example.wyyu.gitsamlpe.test.image.local;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.collection.LongSparseArray;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.matisse.internal.entity.Item;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.test.image.matisse.AspectRatioFrameLayout;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;

/**
 * Created by wyyu on 2019-07-01.
 **/

class ImageListAdapter extends RecyclerView.Adapter {

    private LongSparseArray<Integer> selectPositionArray;
    private OnImageClickListener clickListener;

    private Drawable placeHolder;
    private Cursor cursor;

    ImageListAdapter() {
        placeHolder = DefaultManager.getDefaultPlaceHolder();
        selectPositionArray = new LongSparseArray<>();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.layout_local_image_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        try {
            cursor.moveToPosition(position);
        } catch (Exception exception) {
            ULog.show(exception.getMessage());
        }

        final ImageViewHolder viewHolder = (ImageViewHolder) holder;
        final Item item = Item.valueOf(cursor);

        viewHolder.cacheValue(item);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(item, viewHolder.getAdapterPosition());
                }
            }
        });
    }

    @Override public int getItemCount() {
        return cursor == null || cursor.isClosed() ? 0 : cursor.getCount();
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        private AspectRatioFrameLayout ratioFrameLayout;
        private ImageView imageView;

        ImageViewHolder(View itemView) {
            super(itemView);

            ratioFrameLayout = itemView.findViewById(R.id.local_img_root);
            imageView = itemView.findViewById(R.id.local_img_image);
            ratioFrameLayout.setAspectRatio(1.0f);
        }

        void cacheValue(Item item) {
            FrescoLoader.newFrescoLoader()
                .placeHolder(placeHolder)
                .resize(320, 320)
                .uri(item.uri)
                .into(imageView);
        }
    }

    void setOnImageClickListener(OnImageClickListener clickListener) {
        this.clickListener = clickListener;
    }

    void swapCursor(Cursor cursor) {
        if (cursor == null) {
            notifyItemRangeRemoved(0, getItemCount());
            return;
        }
        if (cursor == this.cursor) {
            return;
        }
        initSelectArray(cursor);
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    private void initSelectArray(@NonNull Cursor cursor) {
        if (selectPositionArray == null) {
            selectPositionArray = new LongSparseArray<>();
        }
        cursor.moveToFirst();
    }

    public interface OnImageClickListener {

        void onClick(Item item, int position);
    }
}
