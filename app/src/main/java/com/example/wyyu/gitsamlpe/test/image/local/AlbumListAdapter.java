package com.example.wyyu.gitsamlpe.test.image.local;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.matisse.internal.entity.Album;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import java.io.File;

/**
 * Created by wyyu on 2019-07-01.
 **/

class AlbumListAdapter extends RecyclerView.Adapter {

    private OnAlbumClickListener albumClickListener;

    private Drawable placeHolder;
    private Cursor cursor;

    AlbumListAdapter() {
        placeHolder = DefaultManager.getDefaultPlaceHolder();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlbumViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.layout_local_album_item_view, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            cursor.moveToPosition(position);
        } catch (Exception exception) {
            ULog.show(exception.getMessage());
        }

        AlbumViewHolder viewHolder = (AlbumViewHolder) holder;
        final Album album = Album.valueOf(cursor);

        viewHolder.cacheValue(album);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (albumClickListener != null) {
                    albumClickListener.onClick(album);
                }
            }
        });
    }

    @Override public int getItemCount() {
        return cursor == null || cursor.isClosed() ? 0 : cursor.getCount();
    }

    private class AlbumViewHolder extends RecyclerView.ViewHolder {

        private ImageView albumCover;
        private TextView albumInfo;

        AlbumViewHolder(View itemView) {
            super(itemView);

            albumCover = itemView.findViewById(R.id.local_album_cover);
            albumInfo = itemView.findViewById(R.id.local_album_info);
        }

        void cacheValue(Album album) {
            FrescoLoader.newFrescoLoader()
                .placeHolder(placeHolder)
                .resize(120, 120)
                .uri(Uri.fromFile(new File(album.getCoverPath())))
                .into(albumCover);

            String info =
                album.getDisplayName(itemView.getContext()) + "(" + album.getCount() + ")";

            albumInfo.setText(info);
        }
    }

    void swapCursor(Cursor cursor) {
        if (cursor == null) {
            notifyItemRangeRemoved(0, getItemCount());
            return;
        }
        if (cursor == this.cursor) {
            return;
        }
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    void setOnAlbumClickListener(OnAlbumClickListener albumClickListener) {
        this.albumClickListener = albumClickListener;
    }

    public interface OnAlbumClickListener {

        void onClick(Album album);
    }
}
