package com.example.wyyu.gitsamlpe.test.image.matisse;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.zhihu.matisse.internal.entity.Album;

/**
 * Created by wyyu on 2018/8/20.
 **/

public class AlbumAdapter extends CursorAdapter {

    private final Drawable mPlaceholder;

    AlbumAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

        TypedArray ta = context.getTheme()
            .obtainStyledAttributes(new int[] { R.attr.album_thumbnail_placeholder });
        mPlaceholder = ta.getDrawable(0);
        ta.recycle();
    }

    @Override public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.layout_album_item, parent, false);
    }

    @Override public void bindView(View view, Context context, Cursor cursor) {
        Album album = Album.valueOf(cursor);

        ((TextView) view.findViewById(R.id.album_item_name)).setText(album.getDisplayName(context));
        String albumInfo = "(" + album.getCount() + ")";
        ((TextView) view.findViewById(R.id.album_item_count)).setText(albumInfo);

        SelectionSpec.getInstance().imageEngine.loadThumbnail(context,
            context.getResources().getDimensionPixelSize(R.dimen.media_grid_size), mPlaceholder,
            (ImageView) view.findViewById(R.id.album_item_cover), album.getContentUri());
    }
}
