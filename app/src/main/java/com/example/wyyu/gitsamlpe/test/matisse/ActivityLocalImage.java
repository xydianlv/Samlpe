package com.example.wyyu.gitsamlpe.test.matisse;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.zhihu.matisse.internal.entity.Album;
import com.zhihu.matisse.internal.entity.SelectionSpec;
import com.zhihu.matisse.internal.model.AlbumPhotoCollection;
import com.zhihu.matisse.internal.model.DevicePhotoAlbumCollection;
import com.zhihu.matisse.internal.model.SelectedItemCollection;

/**
 * Created by wyyu on 2018/8/1.
 **/

public class ActivityLocalImage extends ToolbarActivity
    implements DevicePhotoAlbumCollection.DevicePhotoAlbumCallbacks,
    AlbumPhotoCollection.AlbumPhotoCallbacks {

    @BindView(R.id.local_image_recycler) RecyclerView recyclerView;

    private DevicePhotoAlbumCollection albumCollection;
    private SelectedItemCollection itemCollection;

    private AlbumPhotoCollection photoCollection;

    private LocalImageAdapter imageAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_local_image);

        initActivity();
        initBasicValue(savedInstanceState);
    }

    private void initActivity() {
        initToolbar("LocalImage", 0xffffffff, 0xff84919b);
        initRecyclerView();
    }

    private void initRecyclerView() {
        imageAdapter = new LocalImageAdapter(null);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(imageAdapter);

        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 32);
    }

    private void initBasicValue(Bundle savedInstanceState) {
        albumCollection = new DevicePhotoAlbumCollection();
        itemCollection = new SelectedItemCollection(this);

        albumCollection.onRestoreInstanceState(savedInstanceState);
        albumCollection.onCreate(this, this);
        albumCollection.loadAlbums();

        itemCollection.onCreate(savedInstanceState, SelectionSpec.getInstance());

        photoCollection = new AlbumPhotoCollection();
        photoCollection.onCreate(this, this);
    }

    @Override public void onAlbumLoad(Cursor cursor) {
        cursor.moveToPosition(albumCollection.getCurrentSelection());
        Album album = Album.valueOf(cursor);
        if (album.isAll() && SelectionSpec.getInstance().capture) {
            album.addCaptureCount();
        }
        photoCollection.load(album, SelectionSpec.getInstance().capture);
    }

    @Override public void onAlbumReset() {

    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        itemCollection.onSaveInstanceState(outState);
        albumCollection.onSaveInstanceState(outState);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        albumCollection.onDestroy();
    }

    @Override public void onLoad(Cursor cursor) {
        imageAdapter.swapCursor(cursor);
    }

    @Override public void onReset() {
        imageAdapter.swapCursor(null);
    }
}
