package com.example.wyyu.gitsamlpe.test.image.local;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.example.matisse.internal.entity.Album;
import com.example.matisse.internal.entity.Item;
import com.example.matisse.internal.model.AlbumCollection;
import com.example.matisse.internal.model.AlbumMediaCollection;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2019-07-01.
 **/
public class ActivityLocalImageList extends FullScreenActivity implements AlbumCollection.AlbumCallbacks,
    AlbumMediaCollection.AlbumMediaCallbacks {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityLocalImageList.class));
    }

    @BindView(R.id.select_img_album_list_layout) View albumLayout;
    @BindView(R.id.select_img_album_list) RecyclerView albumList;
    @BindView(R.id.select_img_image_list) RecyclerView imageList;

    @BindView(R.id.select_img_album_info) TextView albumInfo;
    @BindView(R.id.select_img_album_icon) View albumIcon;

    @BindView(R.id.select_img_complete) TextView complete;
    @BindView(R.id.select_img_preview) TextView preview;

    @BindView(R.id.select_img_size_info) TextView sizeInfo;
    @BindView(R.id.select_img_size_icon) View sizeIcon;

    // 相册集合
    private AlbumCollection albumCollection;
    // 图片集合
    private AlbumMediaCollection photoCollection;

    // 相册列表 Adapter
    private AlbumListAdapter albumListAdapter;
    // 图片列表 Adapter
    private ImageListAdapter imageListAdapter;

    // 收起相册列表的动画
    private AnimatorSet animatorOut;
    // 打开相册列表的动画
    private AnimatorSet animatorIn;
    // 相册列表是否在展示中
    private boolean showAlbumList;

    // 当前选中的 Album
    private Album selectAlbum;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_image_list);

        initActivity(savedInstanceState);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        albumCollection.onSaveInstanceState(outState);
    }

    @Override protected void onDestroy() {
        super.onDestroy();

        if (albumCollection != null) {
            albumCollection.onDestroy();
        }
    }

    @Override public void onAlbumLoad(Cursor cursor) {
        if (cursor == null || cursor.getCount() <= 0) {
            onAlbumSelect(null);
            albumListAdapter.swapCursor(null);
            return;
        }
        cursor.moveToFirst();
        albumListAdapter.swapCursor(cursor);
        onAlbumSelect(Album.valueOf(cursor));
    }

    @Override public void onAlbumReset() {

    }

    @Override public void onAlbumMediaLoad(Cursor cursor) {
        if (cursor == null || cursor.getCount() <= 0) {
            imageListAdapter.swapCursor(null);
            return;
        }
        cursor.moveToFirst();
        imageListAdapter.swapCursor(cursor);
    }

    @Override public void onAlbumMediaReset() {

    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && showAlbumList) {
            hideAlbumList();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({
        R.id.select_img_back, R.id.select_img_album_layout, R.id.select_img_size_layout,
        R.id.select_img_album_list_layout
    }) public void onIconClick(View view) {
        switch (view.getId()) {
            case R.id.select_img_back:
                finish();
                break;
            case R.id.select_img_album_layout:
                if (showAlbumList) {
                    hideAlbumList();
                } else {
                    showAlbumList();
                }
                break;
            case R.id.select_img_size_layout:
                break;
            case R.id.select_img_album_list_layout:
                hideAlbumList();
                break;
        }
    }

    private void initActivity(Bundle savedInstanceState) {
        initBasicValue(savedInstanceState);
        initRecyclerView();
        initAnimatorValue();
    }

    private void initBasicValue(Bundle savedInstanceState) {
        albumCollection = new AlbumCollection();
        albumCollection.onCreate(this, this);
        albumCollection.onRestoreInstanceState(savedInstanceState);

        photoCollection = new AlbumMediaCollection();
        photoCollection.onCreate(this, this);

        albumCollection.loadAlbums();

        showAlbumList = false;
    }

    private void initRecyclerView() {
        albumListAdapter = new AlbumListAdapter();
        albumListAdapter.setOnAlbumClickListener(new AlbumListAdapter.OnAlbumClickListener() {
            @Override public void onClick(Album album) {
                onAlbumSelect(album);
                hideAlbumList();
            }
        });

        albumList.setLayoutManager(new LinearLayoutManager(this));
        albumList.setAdapter(albumListAdapter);
        albumList.setItemAnimator(null);

        imageListAdapter = new ImageListAdapter();
        imageListAdapter.setOnImageClickListener(new ImageListAdapter.OnImageClickListener() {
            @Override public void onClick(Item item, int position) {
            }
        });

        imageList.setLayoutManager(new GridLayoutManager(this, 3));
        imageList.setAdapter(imageListAdapter);
        imageList.setItemAnimator(null);
    }

    private void initAnimatorValue() {
        ObjectAnimator animatorOutA = ObjectAnimator.ofFloat(albumList, "alpha", 1.0f, 0, 0f);
        ObjectAnimator animatorInA = ObjectAnimator.ofFloat(albumList, "alpha", 0.0f, 1.0f);
        ObjectAnimator animatorOutT =
            ObjectAnimator.ofFloat(albumList, "translationY", 0.0f, -380.0f);
        ObjectAnimator animatorInT =
            ObjectAnimator.ofFloat(albumList, "translationY", -380.0f, 0.0f);

        animatorOut = new AnimatorSet();
        animatorIn = new AnimatorSet();

        animatorOut.playTogether(animatorOutA, animatorOutT);
        animatorOut.setInterpolator(new LinearInterpolator());
        animatorOut.setDuration(180);
        animatorIn.playTogether(animatorInA, animatorInT);
        animatorIn.setInterpolator(new LinearInterpolator());
        animatorIn.setDuration(180);

        animatorOut.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (albumLayout != null) {
                    albumLayout.setVisibility(View.GONE);
                }
                showAlbumList = false;
            }
        });

        showAlbumList = false;
    }

    private void onAlbumSelect(Album album) {
        if (album == null) {
            selectAlbum = null;
            return;
        }
        if (photoCollection == null) {
            photoCollection = new AlbumMediaCollection();
        }

        photoCollection.onDestroy();
        photoCollection.onCreate(this, this);

        photoCollection.load(album, false);

        albumInfo.setText(album.getDisplayName(this));
        selectAlbum = album;
    }

    private void showAlbumList() {
        albumLayout.setVisibility(View.VISIBLE);

        albumIcon.setRotation(180.0f);
        animatorIn.start();

        showAlbumList = true;
    }

    private void hideAlbumList() {
        albumIcon.setRotation(0.0f);
        animatorOut.start();
    }
}
