package com.example.wyyu.gitsamlpe.test.multi.delegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.multi.delegate.data.ItemTest;
import com.example.wyyu.gitsamlpe.test.multi.delegate.data.ItemTypeA;
import com.example.wyyu.gitsamlpe.test.multi.delegate.data.ItemTypeB;
import com.example.wyyu.gitsamlpe.test.multi.delegate.data.ItemTypeC;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2019-09-23.
 **/

public class ActivityDelegateList extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityDelegateList.class));
    }

    @BindView(R.id.delegate_recycler) RecyclerView recyclerView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegate_list);

        initActivity();
    }

    private void initActivity() {

        initToolbar("DelegateListTest", 0xffffffff, 0xff84919b);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DelegateListAdapter(loadItemList()));
        recyclerView.setItemAnimator(null);
    }

    private List<ItemTest> loadItemList() {

        List<ItemTest> itemTestList = new ArrayList<>();

        itemTestList.add(new ItemTypeA(0));
        itemTestList.add(new ItemTypeA(1));
        itemTestList.add(new ItemTypeB(2));
        itemTestList.add(new ItemTypeC(3));
        itemTestList.add(new ItemTypeB(4));
        itemTestList.add(new ItemTypeB(5));
        itemTestList.add(new ItemTypeB(6));
        itemTestList.add(new ItemTypeA(7));
        itemTestList.add(new ItemTypeC(8));
        itemTestList.add(new ItemTypeC(9));
        itemTestList.add(new ItemTypeA(10));
        itemTestList.add(new ItemTypeA(11));
        itemTestList.add(new ItemTypeA(12));
        itemTestList.add(new ItemTypeB(13));
        itemTestList.add(new ItemTypeB(14));
        itemTestList.add(new ItemTypeB(15));
        itemTestList.add(new ItemTypeC(16));

        return itemTestList;
    }
}
