package com.example.wyyu.gitsamlpe.test.multi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.OnClick;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.multi.custom.ActivityCustomList;
import com.example.wyyu.gitsamlpe.test.multi.define.ActivityDefineList;
import com.example.wyyu.gitsamlpe.test.multi.delegate.ActivityDelegateList;
import com.example.wyyu.gitsamlpe.test.multi.multitype.ActivityMultiTypeList;

/**
 * Created by wyyu on 2019-09-23.
 **/

public class ActivityMultiListTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityMultiListTest.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_list_test);

        initToolbar("MultiListTest", 0xffffffff, 0xff84919b);
    }

    @OnClick({
        R.id.adapter_delegate, R.id.multi_type_adapter, R.id.define_list_adapter,
        R.id.custom_list_adapter
    }) public void onEvent(View view) {
        switch (view.getId()) {
            case R.id.adapter_delegate:
                ActivityDelegateList.open(ActivityMultiListTest.this);
                break;
            case R.id.multi_type_adapter:
                ActivityMultiTypeList.open(ActivityMultiListTest.this);
                break;
            case R.id.define_list_adapter:
                ActivityDefineList.open(ActivityMultiListTest.this);
                break;
            case R.id.custom_list_adapter:
                ActivityCustomList.open(ActivityMultiListTest.this);
                break;
        }
    }
}
