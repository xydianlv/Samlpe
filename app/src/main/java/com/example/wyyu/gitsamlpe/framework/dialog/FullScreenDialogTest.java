package com.example.wyyu.gitsamlpe.framework.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/8/25.
 **/

public class FullScreenDialogTest {

    private AlertDialog funScreenDialog;

    private FullScreenDialogTest(Context context) {
        funScreenDialog = new AlertDialog.Builder(context, R.style.SupportSoftDialog).create();

        Window window = funScreenDialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(0));
    }

    private void show(View contentView) {
        if (funScreenDialog.isShowing()) {
            funScreenDialog.dismiss();
        }
        if (contentView == null || contentView.getContext() == null) {
            return;
        }

        funScreenDialog.show();

        funScreenDialog.setContentView(contentView);

        Window window = funScreenDialog.getWindow();
        assert window != null;

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setAttributes(layoutParams);
    }

    private void dismiss() {
        funScreenDialog.dismiss();
    }

    public static class Builder {

        private FullScreenDialogTest funScreenDialog;

        public Builder(Context context) {
            funScreenDialog = new FullScreenDialogTest(context);
        }

        public void show(View contentView) {
            funScreenDialog.show(contentView);
        }

        public void hide() {
            funScreenDialog.dismiss();
        }
    }
}
