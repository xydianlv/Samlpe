package com.example.wyyu.gitsamlpe.test.litho.define;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineData;
import java.util.List;

/**
 * Created by wyyu on 2019-10-09.
 **/

public class ActivityDefineList extends ToolbarActivity {

    @BindView(R.id.litho_define_list) RecyclerView recyclerView;

    @BindView(R.id.litho_define_parent_height) TextView parentHeight;
    @BindView(R.id.litho_define_start_top) TextView startTop;
    @BindView(R.id.litho_define_end_bottom) TextView endBottom;
    @BindView(R.id.litho_define_first_top) TextView firstTop;
    @BindView(R.id.litho_define_last_bottom) TextView lastBottom;
    @BindView(R.id.litho_define_scroll_y) TextView scrollY;

    private LinearLayoutManager layoutManager;
    private LithoDefineAdapter adapter;
    private LithoDefineModel model;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litho_define_list);

        initActivity();
    }

    private void initActivity() {

        initToolbar("DefineTest", 0xffffffff, 0xff84919b);

        initList();
        initValue();
        loadList();
    }

    private void initList() {
        layoutManager = new LinearLayoutManager(this);
        adapter = new LithoDefineAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    refreshInfoShow();
                }
            }
        });
    }

    private void initValue() {
        model = ViewModelProviders.of(this).get(LithoDefineModel.class);
    }

    private void loadList() {
        model.loadList(new LithoDefineModel.LoadListCallback() {
            @Override public void onSuccess(List<DefineData> dataList) {
                if (adapter != null) {
                    adapter.setDataList(dataList);
                }
            }

            @Override public void onFailure() {

            }
        });
    }

    private void refreshInfoShow() {

        parentHeight.setText(
            String.format("ParentHeight : %s", String.valueOf(recyclerView.getHeight())));

        startTop.setText(
            String.format("StartTop : %s", String.valueOf(recyclerView.getChildAt(0).getTop())));
        endBottom.setText(String.format("EndBottom : %s",
            String.valueOf(recyclerView.getChildAt(recyclerView.getChildCount() - 1).getBottom())));

        RecyclerView.ViewHolder viewHolderFirst = recyclerView.findViewHolderForAdapterPosition(
            layoutManager.findFirstVisibleItemPosition());
        firstTop.setText(String.format("FirstTop : %s",
            viewHolderFirst == null ? "-100" : String.valueOf(viewHolderFirst.itemView.getTop())));

        RecyclerView.ViewHolder viewHolderLast = recyclerView.findViewHolderForAdapterPosition(
            layoutManager.findLastVisibleItemPosition());
        lastBottom.setText(String.format("LastBottom : %s",
            viewHolderLast == null ? "-101" : String.valueOf(viewHolderLast.itemView.getTop())));

        scrollY.setText(String.format("ScrollY : %s", String.valueOf(recyclerView.getScrollY())));
    }
}
