package com.example.wyyu.gitsamlpe.test.image.matisse;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.zhihu.matisse.internal.entity.Album;
import com.zhihu.matisse.internal.entity.SelectionSpec;
import com.zhihu.matisse.internal.model.AlbumPhotoCollection;
import com.zhihu.matisse.internal.model.DevicePhotoAlbumCollection;
import com.zhihu.matisse.internal.model.SelectedItemCollection;
import com.zhihu.matisse.internal.ui.widget.AlbumsSpinner;

/**
 * Created by wyyu on 2018/8/1.
 **/

public class ActivityLocalImage extends ToolbarActivity
    implements DevicePhotoAlbumCollection.DevicePhotoAlbumCallbacks,
    AlbumPhotoCollection.AlbumPhotoCallbacks {

    @BindView(R.id.local_image_recycler) RecyclerView recyclerView;
    @BindView(R.id.local_image_text) TextView textView;

    private DevicePhotoAlbumCollection albumCollection;
    private SelectedItemCollection itemCollection;

    private AlbumPhotoCollection photoCollection;
    private LocalImageAdapter imageAdapter;

    private AlbumsSpinner albumsSpinner;
    private AlbumAdapter albumAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_local_image);

        initBasicValue(savedInstanceState);
        initActivity();
    }

    private void initActivity() {
        initToolbar("LocalImage", 0xffffffff, 0xff84919b);
        initRecyclerView();
        initAlbumSpinner();
    }

    private void initRecyclerView() {
        imageAdapter = new LocalImageAdapter(null);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(imageAdapter);

        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 32);
    }

    private void initAlbumSpinner() {

        albumAdapter = new AlbumAdapter(this, null, false);
        albumsSpinner = new AlbumsSpinner(this);
        albumsSpinner.setSelectedTextView(textView);
        albumsSpinner.setPopupAnchorView(textView);
        albumsSpinner.setAdapter(albumAdapter);
        albumsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                albumCollection.setStateCurrentSelection(position);
                albumAdapter.getCursor().moveToPosition(position);
                Album album = Album.valueOf(albumAdapter.getCursor());
                if (album.isAll() && SelectionSpec.getInstance().capture) {
                    album.addCaptureCount();
                }

                onAlbumSelect(album);
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        albumAdapter.swapCursor(cursor);
        albumsSpinner.setSelection(this, albumCollection.getCurrentSelection());

        cursor.moveToPosition(albumCollection.getCurrentSelection());
        Album album = Album.valueOf(cursor);
        if (album.isAll() && SelectionSpec.getInstance().capture) {
            album.addCaptureCount();
        }

        onAlbumSelect(album);
    }

    @Override public void onAlbumReset() {
        albumAdapter.swapCursor(null);
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

    private void onAlbumSelect(Album album) {
        if (album == null) {
            return;
        }

        if (photoCollection == null) {
            photoCollection = new AlbumPhotoCollection();
        }

        photoCollection.onDestroy();
        photoCollection.onCreate(this, this);

        photoCollection.load(album, true);
    }
}
