package com.example.wyyu.gitsamlpe.test.video.local;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.example.matisse.Matisse;
import com.example.matisse.MimeType;
import com.example.matisse.filter.Filter;
import com.example.matisse.filter.VideoSizeFilter;
import com.example.matisse.internal.entity.Album;
import com.example.matisse.internal.entity.Item;
import com.example.matisse.internal.model.AlbumCollection;
import com.example.matisse.internal.model.AlbumMediaCollection;
import com.example.matisse.internal.utils.LocalMediaUtil;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.test.video.player.LiteVideoPlayer;
import java.util.LinkedList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2019/4/2.
 **/

public class ActivityLocalVideoList extends FullScreenActivity
    implements AlbumMediaCollection.AlbumMediaCallbacks, AlbumCollection.AlbumCallbacks {

    public static void open(Activity activity) {
        Matisse.from(activity)
            .choose(MimeType.ofVideo(), false)
            .nightMode(false)
            .countable(true)
            .capture(true)
            .maxSelectable(1)
            .addFilter(new VideoSizeFilter(100 * Filter.K * Filter.K, 15 * 60 * 1000))
            .gridExpectedSize(
                activity.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .thumbnailScale(0.6f)
            .showSingleMediaType(true);
        activity.startActivity(new Intent(activity, ActivityLocalVideoList.class));
    }

    @BindView(R.id.local_video_album) RecyclerView albumList;
    @BindView(R.id.local_video_list) RecyclerView videoList;
    @BindView(R.id.local_video_title) TextView title;
    @BindView(R.id.local_video_empty) View empty;

    private AlbumMediaCollection mediaCollection;
    private AlbumCollection albumCollection;

    private LocalAlbumAdapter albumAdapter;
    private LocalVideoAdapter videoAdapter;

    // 收起相册列表的动画
    private AnimatorSet animatorOut;
    // 打开相册列表的动画
    private AnimatorSet animatorIn;
    // 相册列表是否在展示中
    private boolean showAlbumList;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_video_list);

        initActivity(savedInstanceState);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        albumCollection.onSaveInstanceState(outState);
    }

    @Override protected void onResume() {
        super.onResume();
    }

    @Override protected void onPause() {
        super.onPause();
        LiteVideoPlayer.getPlayer().stop();
    }

    @Override protected void onDestroy() {
        super.onDestroy();

        if (albumCollection != null) {
            albumCollection.onDestroy();
        }

        LiteVideoPlayer.getPlayer().release();
    }

    @OnClick({ R.id.local_video_title }) public void onIconClick(View view) {
        switch (view.getId()) {
            case R.id.local_video_title:
                if (showAlbumList) {
                    hideAlbumList();
                } else {
                    showAlbumList();
                }
                break;
        }
    }

    @Override public void onAlbumLoad(final Cursor cursor) {
        if (cursor == null || cursor.getCount() <= 0) {
            empty.setVisibility(View.VISIBLE);
        } else {
            Observable.unsafeCreate(new Observable.OnSubscribe<List<Album>>() {
                @Override public void call(Subscriber<? super List<Album>> subscriber) {
                    List<Album> albumList = new LinkedList<>();
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        albumList.add(Album.valueOf(cursor));
                    }
                    subscriber.onNext(albumList);
                    subscriber.onCompleted();
                }
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Album>>() {
                    @Override public void call(List<Album> albumList) {
                        boolean hasData = albumList != null && !albumList.isEmpty();
                        if (!hasData) {
                            if (empty != null) {
                                empty.setVisibility(View.VISIBLE);
                            }
                            return;
                        }
                        if (albumAdapter != null) {
                            albumAdapter.setAlbumList(albumList);
                        }
                        if (empty != null) {
                            empty.setVisibility(View.GONE);
                        }
                        onAlbumSelect(albumList.get(0));
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        if (empty != null) {
                            empty.setVisibility(View.GONE);
                        }
                        ULog.show(throwable.getMessage());
                    }
                });
        }
    }

    @Override public void onAlbumReset() {

    }

    @Override public void onAlbumMediaLoad(final Cursor cursor) {
        if (cursor == null || cursor.getCount() <= 0) {
            empty.setVisibility(View.VISIBLE);
        } else {
            Observable.unsafeCreate(new Observable.OnSubscribe<List<Item>>() {
                @Override public void call(Subscriber<? super List<Item>> subscriber) {
                    List<Item> itemList = new LinkedList<>();
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        Item item = Item.valueOf(cursor);
                        if (item.height == 0 || item.width == 0) {
                            LocalMediaUtil.getMediaUtil().judgeVideoShow(item);
                        }
                        itemList.add(item);
                    }
                    subscriber.onNext(itemList);
                    subscriber.onCompleted();
                }
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Item>>() {
                    @Override public void call(List<Item> itemList) {
                        boolean hasData = itemList != null && !itemList.isEmpty();
                        if (!hasData) {
                            if (empty != null) {
                                empty.setVisibility(View.VISIBLE);
                            }
                            return;
                        }
                        if (videoAdapter != null) {
                            videoAdapter.setVideoItemList(itemList);
                        }
                        if (empty != null) {
                            empty.setVisibility(View.GONE);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        if (empty != null) {
                            empty.setVisibility(View.GONE);
                        }
                        ULog.show(throwable.getMessage());
                    }
                });
        }
    }

    @Override public void onAlbumMediaReset() {

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

        mediaCollection = new AlbumMediaCollection();
        mediaCollection.onCreate(this, this);

        albumCollection.loadAlbums();

        showAlbumList = false;
    }

    private void initRecyclerView() {
        albumAdapter = new LocalAlbumAdapter();
        albumAdapter.setOnAlbumClickListener(new LocalAlbumAdapter.OnAlbumClickListener() {
            @Override public void onClick(Album album) {
                onAlbumSelect(album);
                hideAlbumList();
            }
        });

        albumList.setLayoutManager(new LinearLayoutManager(this));
        albumList.setAdapter(albumAdapter);
        albumList.setItemAnimator(null);

        videoAdapter = new LocalVideoAdapter();

        videoList.setLayoutManager(new LinearLayoutManager(this));
        videoList.setAdapter(videoAdapter);
        videoList.setItemAnimator(null);

        videoList.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        videoList.setItemViewCacheSize(0);
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
                if (albumList != null) {
                    albumList.setVisibility(View.GONE);
                }
                showAlbumList = false;
            }
        });

        showAlbumList = false;
    }

    private void onAlbumSelect(Album album) {
        if (album == null) {
            return;
        }
        if (mediaCollection == null) {
            mediaCollection = new AlbumMediaCollection();
        }

        mediaCollection.onDestroy();
        mediaCollection.onCreate(this, this);

        mediaCollection.load(album, false);

        title.setText(album.getDisplayName(this));
    }

    private void showAlbumList() {
        albumList.setVisibility(View.VISIBLE);

        animatorIn.start();

        showAlbumList = true;
    }

    private void hideAlbumList() {
        animatorOut.start();
    }
}
