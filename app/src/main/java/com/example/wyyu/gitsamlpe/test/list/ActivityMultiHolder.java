package com.example.wyyu.gitsamlpe.test.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2019/3/1.
 **/

public class ActivityMultiHolder extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityMultiHolder.class));
    }

    @BindView(R.id.multi_recycler_view) RecyclerView recyclerView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_holder);

        initActivity();
    }

    private void initActivity() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        MultiHolderAdapter adapter = new MultiHolderAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        List<MultiData> multiDataList = new ArrayList<>();

        for (int index = 0; index < 50; index++) {
            multiDataList.add(new MultiData(index, index % 3));
        }

        adapter.setMultiDataList(multiDataList);
    }
}
