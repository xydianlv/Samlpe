package com.example.wyyu.gitsamlpe.test.dialog.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.window.PressListenerView;
import com.example.wyyu.gitsamlpe.util.CommonUtil;

/**
 * Created by wyyu on 2020-04-13.
 **/

public class DialogSoftView extends LinearLayout {

    public DialogSoftView(Context context) {
        super(context);
        initDialogView();
    }

    public DialogSoftView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDialogView();
    }

    public DialogSoftView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDialogView();
    }

    public interface OnDialogClickListener {

        void onClick(int type);
    }

    private View fun;

    private void initDialogView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_soft_view, this);

        fun = findViewById(R.id.dialog_soft_fun);

        PressListenerView listenerView = findViewById(R.id.dialog_soft_edit_layout);
        EditText editText = findViewById(R.id.dialog_soft_edit);
        listenerView.setOnPressListener(new PressListenerView.OnPressListener() {
            @Override public boolean onPressDown() {
                CommonUtil.showSoftInput(getContext(), editText);
                return true;
            }

            @Override public boolean onPressUp() {
                return false;
            }
        });
    }

    public void show(@NonNull OnDialogClickListener clickListener) {
        fun.setOnClickListener(v -> clickListener.onClick(0));
    }
}
