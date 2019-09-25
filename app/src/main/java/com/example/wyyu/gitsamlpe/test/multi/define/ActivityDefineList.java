package com.example.wyyu.gitsamlpe.test.multi.define;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.multi.define.base.MultiItemAdapter;
import com.example.wyyu.gitsamlpe.test.multi.define.binder.DefineBinderA;
import com.example.wyyu.gitsamlpe.test.multi.define.binder.DefineBinderB;
import com.example.wyyu.gitsamlpe.test.multi.define.binder.DefineBinderC;
import com.example.wyyu.gitsamlpe.test.multi.define.data.DefineDataA;
import com.example.wyyu.gitsamlpe.test.multi.define.data.DefineDataB;
import com.example.wyyu.gitsamlpe.test.multi.define.data.DefineDataC;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2019-09-24.
 **/

public class ActivityDefineList extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityDefineList.class));
    }

    @BindView(R.id.define_list_recycler) RecyclerView recyclerView;

    private DefineDataC defineDataC;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_list);

        initToolbar("DefineListTest", 0xffffffff, 0xff84919b);
        initActivity();
    }

    private void initActivity() {

        MultiItemAdapter adapter = new MultiItemAdapter();

        adapter.register(DefineDataA.class, new DefineBinderA());
        adapter.register(DefineDataB.class, new DefineBinderB());
        adapter.register(DefineDataC.class, new DefineBinderC());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        adapter.bindRecyclerView(recyclerView);
        adapter.setItemList(loadList());

        findViewById(R.id.define_click_test).setOnClickListener(
            v -> adapter.updateItem(defineDataC, DefineBinderC.UPDATE_TYPE_B));

        findViewById(R.id.define_click_test_r).setOnClickListener(v -> {
            defineDataC.index = defineDataC.index + 2;
            adapter.updateItem(defineDataC, DefineBinderC.UPDATE_TYPE_A);
        });
    }

    private List<?> loadList() {
        List<Object> objectList = new ArrayList<>();

        objectList.add(new DefineDataA(0));
        objectList.add(new DefineDataB(1));
        objectList.add(new DefineDataC(2));
        defineDataC = new DefineDataC(3);
        objectList.add(defineDataC);
        objectList.add(new DefineDataC(4));
        objectList.add(new DefineDataA(5));
        objectList.add(new DefineDataA(6));
        objectList.add(new DefineDataB(7));
        objectList.add(new DefineDataC(8));
        objectList.add(new DefineDataB(9));
        objectList.add(new DefineDataB(10));
        objectList.add(new DefineDataB(11));
        objectList.add(new DefineDataB(12));
        objectList.add(new DefineDataC(13));
        objectList.add(new DefineDataA(14));
        objectList.add(new DefineDataA(15));
        objectList.add(new DefineDataC(16));

        return objectList;
    }
}
