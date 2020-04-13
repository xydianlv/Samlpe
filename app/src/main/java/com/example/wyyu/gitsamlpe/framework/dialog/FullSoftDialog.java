package com.example.wyyu.gitsamlpe.framework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2020-04-13.
 **/

public class FullSoftDialog {

    private Dialog dialog;

    private FullSoftDialog(Context context) {
        dialog = new Dialog(context, R.style.SupportSoftDialog);

        Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(0));
    }

    private void show(View contentView) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (contentView == null || contentView.getContext() == null) {
            return;
        }

        dialog.show();

        dialog.setContentView(contentView);

        Window window = dialog.getWindow();
        assert window != null;

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setAttributes(layoutParams);
    }

    private void dismiss() {
        dialog.dismiss();
    }

    public static class Builder {

        private FullSoftDialog dialog;

        public Builder(Context context) {
            dialog = new FullSoftDialog(context);
        }

        public void show(View contentView) {
            dialog.show(contentView);
        }

        public void hide() {
            dialog.dismiss();
        }
    }
}
