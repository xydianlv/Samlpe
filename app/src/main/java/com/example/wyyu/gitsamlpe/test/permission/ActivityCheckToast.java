package com.example.wyyu.gitsamlpe.test.permission;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.toast.TToast;

/**
 * Created by wyyu on 2018/8/27.
 **/

public class ActivityCheckToast extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCheckToast.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_toast);

        initActivity();
    }

    private void initActivity() {
        initToolbar("CheckToast", 0xffffffff, 0xff84919b);

        findViewById(R.id.check_toast_button).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                TToast.showShort("CheckToast");
            }
        });
    }
}
