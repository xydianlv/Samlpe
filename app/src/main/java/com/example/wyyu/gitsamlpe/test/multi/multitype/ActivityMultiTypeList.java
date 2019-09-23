package com.example.wyyu.gitsamlpe.test.multi.multitype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.multi.multitype.base.MultiTypeAdapter;
import com.example.wyyu.gitsamlpe.test.multi.multitype.binder.ItemTypeABinder;
import com.example.wyyu.gitsamlpe.test.multi.multitype.binder.ItemTypeBBinder;
import com.example.wyyu.gitsamlpe.test.multi.multitype.binder.ItemTypeCBinder;
import com.example.wyyu.gitsamlpe.test.multi.multitype.data.ItemTypeA;
import com.example.wyyu.gitsamlpe.test.multi.multitype.data.ItemTypeB;
import com.example.wyyu.gitsamlpe.test.multi.multitype.data.ItemTypeC;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2019-09-23.
 **/

public class ActivityMultiTypeList extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityMultiTypeList.class));
    }

    @BindView(R.id.multi_type_list) RecyclerView recyclerView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type_list);

        initToolbar("MultiTypeTest", 0xffffffff, 0xff84919b);
        initActivity();
    }

    private void initActivity() {

        MultiTypeAdapter multiTypeAdapter = new MultiTypeAdapter();

        multiTypeAdapter.register(ItemTypeA.class, new ItemTypeABinder());
        multiTypeAdapter.register(ItemTypeB.class, new ItemTypeBBinder());
        multiTypeAdapter.register(ItemTypeC.class, new ItemTypeCBinder());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(multiTypeAdapter);
        recyclerView.setItemAnimator(null);

        multiTypeAdapter.setItems(loadList());
        multiTypeAdapter.notifyDataSetChanged();
    }

    private List<Object> loadList() {

        List<Object> objectList = new ArrayList<>();

        objectList.add(new ItemTypeA(0));
        objectList.add(new ItemTypeA(1));
        objectList.add(new ItemTypeB(2));
        objectList.add(new ItemTypeC(3));
        objectList.add(new ItemTypeC(4));
        objectList.add(new ItemTypeC(5));
        objectList.add(new ItemTypeB(6));
        objectList.add(new ItemTypeB(7));
        objectList.add(new ItemTypeC(8));
        objectList.add(new ItemTypeA(9));
        objectList.add(new ItemTypeA(10));
        objectList.add(new ItemTypeA(11));
        objectList.add(new ItemTypeC(12));
        objectList.add(new ItemTypeC(13));
        objectList.add(new ItemTypeB(14));
        objectList.add(new ItemTypeB(15));
        objectList.add(new ItemTypeB(16));

        return objectList;
    }
}
