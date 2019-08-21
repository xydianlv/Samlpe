package com.example.wyyu.gitsamlpe.test.tangram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.OnClick;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2019-08-21.
 **/

public class ActivityTangram extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityTangram.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tangram);

        initToolbar("Tangram", 0xffffffff, 0xff84919b);
    }

    @OnClick({ R.id.tangram_default, R.id.tangram_card, R.id.tangram_define })
    public void onEvent(View view) {
        switch (view.getId()) {
            case R.id.tangram_default:
                ActivityDefault.open(ActivityTangram.this);
                break;
            case R.id.tangram_define:
                ActivityDefine.open(ActivityTangram.this);
                break;
            case R.id.tangram_card:
                break;
        }
    }
}
