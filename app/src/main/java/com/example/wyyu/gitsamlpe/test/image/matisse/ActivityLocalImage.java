package com.example.wyyu.gitsamlpe.test.image.matisse;

import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.zhihu.matisse.internal.entity.Album;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.entity.SelectionSpec;
import com.zhihu.matisse.internal.model.AlbumPhotoCollection;
import com.zhihu.matisse.internal.model.DevicePhotoAlbumCollection;
import com.zhihu.matisse.internal.model.SelectedItemCollection;
import com.zhihu.matisse.internal.ui.widget.AlbumsSpinner;
import com.zhihu.matisse.internal.utils.MediaStoreCompat;

/**
 * Created by wyyu on 2018/8/1.
 **/

public class ActivityLocalImage extends ToolbarActivity
    implements DevicePhotoAlbumCollection.DevicePhotoAlbumCallbacks,
    AlbumPhotoCollection.AlbumPhotoCallbacks {

    private static final String URI_LOCAL_IMAGE_OBSERVER = "content://media/external/images/media";
    private static final String PROVIDER =
        AppController.getAppContext().getPackageName() + ".fileprovider";
    private static final int REQUEST_CODE_CAPTURE = 24;

    @BindView(R.id.local_image_recycler) RecyclerView recyclerView;
    @BindView(R.id.local_image_text) TextView textView;

    private DevicePhotoAlbumCollection albumCollection;
    private SelectedItemCollection itemCollection;

    private AlbumPhotoCollection photoCollection;
    private LocalMediaAdapter mediaAdapter;

    private AlbumsSpinner albumsSpinner;
    private AlbumAdapter albumAdapter;

    private ContentObserver contentObserver;
    private MediaStoreCompat mediaStoreCompat;

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

        mediaStoreCompat = new MediaStoreCompat(this);
        mediaStoreCompat.setCaptureStrategy(new CaptureStrategy(true, PROVIDER));
    }

    private void initRecyclerView() {
        mediaAdapter = new LocalMediaAdapter();

        mediaAdapter.setOnMediaClickListener(new LocalMediaAdapter.OnMediaClickListener() {
            @Override public void onClickItem() {
            }

            @Override public void onCapture() {
                mediaStoreCompat.dispatchCaptureIntent(ActivityLocalImage.this,
                    REQUEST_CODE_CAPTURE);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(mediaAdapter);
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

        contentObserver = new ContentObserver(new Handler()) {
            @Override public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                resetCollection();
            }
        };
        getContentResolver().registerContentObserver(Uri.parse(URI_LOCAL_IMAGE_OBSERVER), true,
            contentObserver);
    }

    private void resetCollection() {
        if (albumCollection != null) {
            albumCollection.onDestroy();
        }
        if (photoCollection != null) {
            photoCollection.onDestroy();
        }

        albumCollection = new DevicePhotoAlbumCollection();
        albumCollection.onCreate(this, this);

        photoCollection = new AlbumPhotoCollection();
        photoCollection.onCreate(this, this);

        albumCollection.loadAlbums();
    }

    @Override public void onAlbumLoad(Cursor cursor) {

        albumAdapter.swapCursor(cursor);
        albumsSpinner.setSelection(this, albumCollection.getCurrentSelection());

        cursor.moveToFirst();
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

        getContentResolver().unregisterContentObserver(contentObserver);
    }

    @Override public void onLoad(Cursor cursor) {
        mediaAdapter.swapCursor(cursor);
    }

    @Override public void onReset() {
        mediaAdapter.swapCursor(null);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CAPTURE) {
            Uri uri = mediaStoreCompat.getCurrentPhotoPath();
            if (uri == null) {
                return;
            }
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
                intent.setData(uri);
                sendBroadcast(intent);
            } else {
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(uri);
                sendBroadcast(intent);
            }
        }
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
