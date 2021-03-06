package com.example.wyyu.gitsamlpe.test.multi.custom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.multi.custom.binder.CustomBinderA;
import com.example.wyyu.gitsamlpe.test.multi.custom.binder.CustomBinderB;
import com.example.wyyu.gitsamlpe.test.multi.custom.binder.CustomBinderC;
import com.example.wyyu.gitsamlpe.test.multi.custom.data.CustomDataA;
import com.example.wyyu.gitsamlpe.test.multi.custom.data.CustomDataB;
import com.example.wyyu.gitsamlpe.test.multi.custom.data.CustomDataC;
import com.wyyu.multi.adapter.MultiAdapter;
import com.wyyu.multi.binder.DefaultBinderManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2019-09-24.
 **/

public class ActivityCustomList extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCustomList.class));
    }

    @BindView(R.id.custom_list_recycler) RecyclerView recyclerView;

    private MultiAdapter<Class<?>, Object> multiAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);

        initActivity();
    }

    private void initActivity() {
        initToolbar("CustomListTest", 0xffffffff, 0xff84919b);

        initList();
        loadList();
    }

    private void initList() {
        multiAdapter = new MultiAdapter<>(new DefaultBinderManager());

        multiAdapter.register(CustomDataA.class, new CustomBinderA());
        multiAdapter.register(CustomDataB.class, new CustomBinderB());
        multiAdapter.register(CustomDataC.class, new CustomBinderC());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(multiAdapter);
        recyclerView.setItemAnimator(null);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                Log.e("onScrollStateChangedLog", "newState : " + newState);
            }

            @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.e("onScrollStateChangedLog", "dx : " + dx + "  dy : " + dy);
            }
        });
    }

    private void loadList() {
        List<Object> objectList = new ArrayList<>();

        for (int index = 0; index < 48; index++) {
            switch (index % 3) {
                case 0:
                    objectList.add(new CustomDataA(index));
                    break;
                case 1:
                    objectList.add(new CustomDataB(index));
                    break;
                case 2:
                    objectList.add(new CustomDataC(index));
                    break;
            }
        }

        multiAdapter.initItemList(objectList, true);
    }
}
