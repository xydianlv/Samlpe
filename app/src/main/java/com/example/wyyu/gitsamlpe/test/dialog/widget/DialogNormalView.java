package com.example.wyyu.gitsamlpe.test.dialog.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2020-04-13.
 **/

public class DialogNormalView extends FrameLayout {

    public DialogNormalView(@NonNull Context context) {
        super(context);
        initDialogView();
    }

    public DialogNormalView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDialogView();
    }

    public DialogNormalView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDialogView();
    }

    public interface OnViewClickListener {

        void onClick(int type);
    }

    private void initDialogView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_normal_view, this);
    }

    public void show(@NonNull OnViewClickListener clickListener) {
        findViewById(R.id.dialog_normal_root).setOnClickListener(v -> clickListener.onClick(0));
        findViewById(R.id.dialog_normal_text).setOnClickListener(v -> clickListener.onClick(1));
    }
}
