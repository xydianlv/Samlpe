package com.example.wyyu.gitsamlpe.test.video.list;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.list.CustomLoadingView;
import com.example.wyyu.gitsamlpe.test.video.data.VideoItem;
import com.example.wyyu.gitsamlpe.test.video.model.VideoResultModel;
import com.example.wyyu.gitsamlpe.test.video.player.CMVideoPlayer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.List;

/**
 * Created by wyyu on 2020-07-27.
 **/

public class ActivityVideoList extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityVideoList.class));
    }

    @BindView(R.id.video_list_refresh) SmartRefreshLayout refreshLayout;
    @BindView(R.id.video_list_recycler) RecyclerView recyclerView;
    @BindView(R.id.video_list_loading) CustomLoadingView loadingView;
    @BindView(R.id.video_list_empty) View emptyView;

    private VideoListAdapter adapter;
    private VideoResultModel model;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        initActivity();
    }

    @Override protected void onPause() {
        super.onPause();
        CMVideoPlayer.getPlayer().release();
    }

    private void initActivity() {
        initToolbar("VideoList");

        initModel();
        initView();
        initList();
        loadList();
    }

    private void initModel() {
        model = ViewModelProviders.of(this).get(VideoResultModel.class);
    }

    private void initView() {
        refreshLayout.setEnableHeaderTranslationContent(false);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableFooterTranslationContent(false);
        refreshLayout.setEnableLoadMore(false);

        refreshLayout.setOnLoadMoreListener(refreshLayout -> appendList());
    }

    private void initList() {
        adapter = new VideoListAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);
    }

    private void loadList() {
        if (model == null) {
            initModel();
        }
        if (loadingView != null) {
            loadingView.show();
        }
        model.loadVideoList(new VideoResultModel.LoadCallback() {
            @Override public void onSuccess(List<VideoItem> videoList, boolean hasMore) {
                if (loadingView != null) {
                    loadingView.hide();
                }
                if (refreshLayout != null) {
                    refreshLayout.setEnableLoadMore(hasMore);
                    refreshLayout.setEnableFooterTranslationContent(hasMore);
                }
                if (adapter != null) {
                    adapter.setItemList(videoList, true);
                }
            }

            @Override public void onFailure() {
                if (loadingView != null) {
                    loadingView.hide();
                }
                if (emptyView != null) {
                    emptyView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void appendList() {
        if (model == null) {
            return;
        }
        model.appendVideoList(new VideoResultModel.LoadCallback() {
            @Override public void onSuccess(List<VideoItem> videoList, boolean hasMore) {
                if (refreshLayout != null) {
                    if (hasMore) {
                        refreshLayout.finishLoadMore();
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
                if (adapter != null) {
                    adapter.setItemList(videoList, false);
                }
            }

            @Override public void onFailure() {
                if (refreshLayout != null) {
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }
}
