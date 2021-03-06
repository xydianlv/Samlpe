package com.example.wyyu.gitsamlpe.test.function.expandable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;

/**
 * Created by wyyu on 2018/10/10.
 **/

public class ActivityExpandableListView extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityExpandableListView.class));
    }

    @BindView(R.id.expandable_list_view) ExpandableListView expandableListView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);

        initActivity();
    }

    private void initActivity() {

        initToolbar("ExpandListView");

        expandableListView.setAdapter(new ExpandableListAdapter(this));

        expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            UToast.showShort(ActivityExpandableListView.this,
                ExpandableListAdapter.GROUP_NAME[groupPosition]);
            // 必须返回 false，否则分组不会展开
            return false;
        });
        expandableListView.setOnChildClickListener(
            (parent, v, groupPosition, childPosition, id) -> {
                UToast.showShort(ActivityExpandableListView.this,
                    ExpandableListAdapter.CHILD_NAME[groupPosition][childPosition]);
                return true;
            });
    }
}
