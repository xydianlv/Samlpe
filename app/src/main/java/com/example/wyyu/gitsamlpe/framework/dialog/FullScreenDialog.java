package com.example.wyyu.gitsamlpe.framework.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.WindowManager;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/5/8.
 **/

public class FullScreenDialog extends AlertDialog {

    public FullScreenDialog(Context context) {
        super(context, R.style.FullScreenDialog); // 自定义全屏style
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public void show() {
        super.show();

        // 设置宽度全屏，要设置在show的后面
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
