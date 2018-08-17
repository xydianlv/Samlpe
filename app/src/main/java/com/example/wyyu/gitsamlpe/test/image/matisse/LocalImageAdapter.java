package com.example.wyyu.gitsamlpe.test.image.matisse;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhihu.matisse.internal.entity.Item;

/**
 * Created by wyyu on 2018/8/1.
 **/

public class LocalImageAdapter extends RecyclerView.Adapter {

    private Cursor cursor;

    LocalImageAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.layout_local_image_view, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        ((ImageViewHolder) holder).bind(Item.valueOf(cursor));
    }

    @Override public int getItemCount() {
        return cursor == null || cursor.isClosed() ? 0 : cursor.getCount();
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView simpleDraweeView;

        ImageViewHolder(View itemView) {
            super(itemView);

            simpleDraweeView = itemView.findViewById(R.id.local_image_item);
        }

        void bind(Item item) {
            FrescoLoader.newFrescoLoader()
                .resize(240, 240)
                .uri(item.uri)
                .cornersRadius(UIUtils.dpToPx(4))
                .into(simpleDraweeView);
        }
    }

    void swapCursor(Cursor newCursor) {
        if (newCursor == null) {
            notifyItemRangeRemoved(0, getItemCount());
            return;
        }
        if (newCursor == cursor) {
            return;
        }
        cursor = newCursor;
        notifyDataSetChanged();
    }
}
