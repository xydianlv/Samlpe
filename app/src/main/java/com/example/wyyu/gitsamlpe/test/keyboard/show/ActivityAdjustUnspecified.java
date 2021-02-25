package com.example.wyyu.gitsamlpe.test.keyboard.show;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2021/1/8.
 **/

public class ActivityAdjustUnspecified extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAdjustUnspecified.class));
    }

    private static final String TEXT = "软键盘弹出的时候，系统会通过布局的移动，保证输入框在用户视野范围内\n"
        + "若界面有滚动控件，系统会缩小可滚动界面，保证即使软键盘弹出，也能看到所有控件\n"
        + "<点击此区域唤起软键盘>";
    private static final int HALF = UIUtils.getScreenWidth() / 2;

    @BindView(R.id.keyboard_mode_recycler) RecyclerView recyclerView;
    @BindView(R.id.keyboard_mode_text) TextView textView;
    @BindView(R.id.keyboard_mode_edit) EditText editText;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_mode);

        initActivity();
    }

    private void initActivity() {
        initToolbar("AdjustUnspecified", 0xffffffff, 0xff84919b);
        initText();
        initList();
    }

    private void initText() {
        textView.setMaxWidth(HALF);
        textView.setText(TEXT);

        textView.setOnClickListener(v -> {
            if (editText != null) {
                KeyboardUtil.showKeyboard(editText);
            }
        });
    }

    private void initList() {
        RecyclerListAdapter listAdapter = new RecyclerListAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}
