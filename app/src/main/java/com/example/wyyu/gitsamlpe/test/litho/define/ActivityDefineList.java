package com.example.wyyu.gitsamlpe.test.litho.define;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        adapter = new LithoDefineAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);
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
}
