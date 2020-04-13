package com.example.wyyu.gitsamlpe.test.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.dialog.FullScreenDialogTest;
import com.example.wyyu.gitsamlpe.framework.dialog.FullSoftDialog;
import com.example.wyyu.gitsamlpe.framework.dialog.SDAlertDialog;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.dialog.widget.DialogNormalView;
import com.example.wyyu.gitsamlpe.test.dialog.widget.DialogSoftView;

/**
 * Created by wyyu on 2019/1/2.
 **/

public class ActivityDialogShowTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityDialogShowTest.class));
    }

    @BindView(R.id.dialog_show_list) ListViewMain listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_show_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("DialogShowTest", 0xffffffff, 0xff84919b);

        listView.addItem("NormalDialog", v -> {
            SDAlertDialog.Builder builder = new SDAlertDialog.Builder(ActivityDialogShowTest.this);
            DialogNormalView normalView = new DialogNormalView(ActivityDialogShowTest.this);
            normalView.show(type -> {
                switch (type) {
                    case 0:
                        builder.hide();
                        break;
                    case 1:
                        UToast.showShort(ActivityDialogShowTest.this, "NormalDialog");
                        break;
                }
            });
            builder.setContentView(normalView).show();
        }).addItem("FullDialog", v -> {
            FullScreenDialogTest.Builder builder =
                new FullScreenDialogTest.Builder(ActivityDialogShowTest.this);
            DialogNormalView normalView = new DialogNormalView(ActivityDialogShowTest.this);
            normalView.show(type -> {
                switch (type) {
                    case 0:
                        builder.hide();
                        break;
                    case 1:
                        UToast.showShort(ActivityDialogShowTest.this, "FullDialog");
                        break;
                }
            });
            builder.show(normalView);
        }).addItem("SoftDialog", v -> {
            FullSoftDialog.Builder builder =
                new FullSoftDialog.Builder(ActivityDialogShowTest.this);
            DialogSoftView softView = new DialogSoftView(ActivityDialogShowTest.this);
            softView.show(type -> {
                switch (type) {
                    case 0:
                        builder.hide();
                        break;
                    case 1:
                        break;
                }
            });
            builder.show(softView);
        }).refreshList();
    }
}
