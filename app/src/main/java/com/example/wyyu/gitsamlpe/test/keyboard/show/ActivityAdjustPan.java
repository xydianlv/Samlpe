package com.example.wyyu.gitsamlpe.test.keyboard.show;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import java.util.concurrent.TimeUnit;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by wyyu on 2021/1/8.
 **/

public class ActivityAdjustPan extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAdjustPan.class));
    }

    private static final String TEXT = "自动弹起软键盘，在 Activity 的 onResume 生命周期回调中，延迟 200ms 执行拉起软键盘操作";

    @BindView(R.id.keyboard_mode_text) TextView textView;
    @BindView(R.id.keyboard_mode_edit) EditText editText;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_mode);

        initActivity();
    }

    @Override protected void onResume() {
        super.onResume();

        AndroidSchedulers.mainThread().createWorker().schedule(() -> {
            if (editText != null) {
                KeyboardUtil.showKeyboard(editText);
            }
        }, 300, TimeUnit.MILLISECONDS);
    }

    private void initActivity() {
        initToolbar("AdjustPan", 0xffffffff, 0xff84919b);

        textView.setText(TEXT);
    }
}
