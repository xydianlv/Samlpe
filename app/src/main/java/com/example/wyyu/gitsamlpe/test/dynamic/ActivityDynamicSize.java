package com.example.wyyu.gitsamlpe.test.dynamic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;

/**
 * Created by wyyu on 2018/10/12.
 **/

public class ActivityDynamicSize extends BaseActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityDynamicSize.class));
    }

    @BindView(R.id.dynamic_test_0) DynamicSizeView sizeView0;
    @BindView(R.id.dynamic_test_1) DynamicSizeView sizeView1;
    @BindView(R.id.dynamic_test_2) DynamicSizeView sizeView2;
    @BindView(R.id.dynamic_test_3) DynamicSizeView sizeView3;
    @BindView(R.id.dynamic_test_4) DynamicSizeView sizeView4;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_size);

        initActivity();
    }

    private void initActivity() {
        sizeView0.setRootViewSize(22, 22);
        sizeView1.setRootViewSize(32, 32);
        sizeView2.setRootViewSize(42, 42);
        sizeView3.setRootViewSize(54, 54);
        sizeView4.setRootViewSize(64, 64);
    }
}
