package com.example.wyyu.gitsamlpe.test.multi.waterfall;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import java.util.List;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class ActivityWaterfallList extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityWaterfallList.class));
    }

    @BindView(R.id.waterfall_list) RecyclerView recyclerView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfall_list);

        initActivity();
    }

    private void initActivity() {
        initToolbar("WaterfallList", 0xffffffff, 0xff84919b);

        WaterfallAdapter adapter = new WaterfallAdapter();

        recyclerView.setLayoutManager(
            new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        WaterFallModel model = ViewModelProviders.of(this).get(WaterFallModel.class);
        model.loadList(new WaterFallModel.LoadCallback() {
            @Override public void onSuccess(List<ItemBean> itemList) {
                adapter.setItemBeanList(itemList);
            }

            @Override public void onFailure() {
                UToast.showShort(ActivityWaterfallList.this, "数据获取失败");
            }
        });
    }
}
