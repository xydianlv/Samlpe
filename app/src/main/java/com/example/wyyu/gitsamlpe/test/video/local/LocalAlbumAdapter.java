package com.example.wyyu.gitsamlpe.test.video.local;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.matisse.internal.entity.Album;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.facebook.drawee.view.SimpleDraweeView;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2019/4/2.
 **/

public class LocalAlbumAdapter extends RecyclerView.Adapter {

    private OnAlbumClickListener albumClickListener;

    private List<Album> albumList;
    private Drawable placeHolder;

    LocalAlbumAdapter() {
        TypedArray ta = AppController.getAppContext()
            .getTheme()
            .obtainStyledAttributes(new int[] { com.zhihu.matisse.R.attr.item_placeholder });
        placeHolder = ta.getDrawable(0);

        albumList = new LinkedList<>();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AlbumViewHolder(LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.layout_album_list_item, viewGroup, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final Album album = albumList.get(i);
        ((AlbumViewHolder) viewHolder).cache(album);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (albumClickListener != null) {
                    albumClickListener.onClick(album);
                }
            }
        });
    }

    @Override public int getItemCount() {
        return albumList == null ? 0 : albumList.size();
    }

    void setAlbumList(List<Album> albumList) {
        if (albumList == null || albumList.isEmpty()) {
            return;
        }
        if (this.albumList == null) {
            this.albumList = new LinkedList<>();
        }
        this.albumList.clear();
        this.albumList.addAll(albumList);
        notifyDataSetChanged();
    }

    void setOnAlbumClickListener(OnAlbumClickListener albumClickListener) {
        this.albumClickListener = albumClickListener;
    }

    public interface OnAlbumClickListener {

        void onClick(Album album);
    }

    private class AlbumViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView albumCover;
        private TextView albumInfo;

        AlbumViewHolder(@NonNull View itemView) {
            super(itemView);

            albumCover = itemView.findViewById(R.id.album_item_cover);
            albumInfo = itemView.findViewById(R.id.album_item_info);
        }

        void cache(Album album) {

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
}
