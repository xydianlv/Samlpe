package com.example.wyyu.gitsamlpe.test.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;
import com.example.wyyu.gitsamlpe.framework.dialog.FullScreenDialogTest;
import com.example.wyyu.gitsamlpe.framework.dialog.SDAlertDialog;

/**
 * Created by wyyu on 2019/1/2.
 **/

public class ActivityFullDialog extends BaseActivity implements View.OnClickListener {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityFullDialog.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_dialog);

        initActivity();
    }

    private void initActivity() {
        findViewById(R.id.show_dialog_normal).setOnClickListener(this);
        findViewById(R.id.show_dialog_full).setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_dialog_normal:
                showNormalDialog();
                break;
            case R.id.show_dialog_full:
                showFullDialog();
                break;
        }
    }

    private void showNormalDialog() {
        new SDAlertDialog.Builder(this).setContentView(getContentView()).show();
    }

    private void showFullDialog() {
        new FullScreenDialogTest.Builder(this).show(getContentView());
    }

    private View getContentView() {
        return LayoutInflater.from(this).inflate(R.layout.layout_test_dialog_view, null);
    }
}
