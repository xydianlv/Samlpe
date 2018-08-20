package com.example.wyyu.gitsamlpe.test.softkeyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.util.CommonUtil;
import java.util.concurrent.TimeUnit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by wyyu on 2018/8/20.
 **/

public class ActivityShowKeyboard extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityShowKeyboard.class));
    }

    @BindView(R.id.show_keyboard_edit) EditText editText;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_keyboard);

        initActivity();
    }

    private void initActivity() {

        initToolbar("AutoShowSoftKeyboard", 0xffffffff, 0xff84919b);

        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override public void call() {
                if (editText != null) {
                    editText.setFocusable(true);
                    editText.requestFocus();
                    CommonUtil.showSoftInput(ActivityShowKeyboard.this, editText);
                }
            }
        }, 100, TimeUnit.MILLISECONDS);
    }
}
