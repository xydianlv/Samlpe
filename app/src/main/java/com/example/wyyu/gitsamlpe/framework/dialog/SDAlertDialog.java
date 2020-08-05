package com.example.wyyu.gitsamlpe.framework.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/5/8.
 **/

public class SDAlertDialog {

    private AlertDialog alertDialog;
    private View contentView;

    @SuppressLint("InflateParams") private SDAlertDialog(Context context) {
        contentView =
            LayoutInflater.from(context).inflate(R.layout.layout_dialog_content_view, null);
        alertDialog = new AlertDialog.Builder(context).create();

        Window window = alertDialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(0));

        alertDialog.setView(contentView);
    }

    private void setCancelText(String cancelText) {
        ((TextView) contentView.findViewById(R.id.dialog_cancel)).setText(cancelText);
    }

    private void setConfirmText(String confirmText) {
        ((TextView) contentView.findViewById(R.id.dialog_confirm)).setText(confirmText);
    }

    private void setCancelListener(View.OnClickListener cancelListener) {
        contentView.findViewById(R.id.dialog_cancel).setOnClickListener(cancelListener);
    }

    private void setConfirmListener(View.OnClickListener confirmListener) {
        contentView.findViewById(R.id.dialog_confirm).setOnClickListener(confirmListener);
    }

    private void setDialogTitle(String dialogTitle) {
        ((TextView) contentView.findViewById(R.id.dialog_title)).setText(dialogTitle);
    }

    private void setDialogMessage(String dialogMessage) {
        ((TextView) contentView.findViewById(R.id.dialog_msg)).setText(dialogMessage);
    }

    private void setDialogContentView(View view) {
        alertDialog.setView(view);
    }

    private void show() {
        if (alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        alertDialog.show();
    }

    private void dismiss() {
        alertDialog.dismiss();
    }

    public static class Builder {

        private SDAlertDialog sdAlertDialog;

        public Builder(Context context) {
            sdAlertDialog = new SDAlertDialog(context);
        }

        public Builder(Context context, String title, String message) {
            this(context);
            setTitle(title);
            setMessage(message);
        }

        public Builder setTitle(String title) {
            sdAlertDialog.setDialogTitle(title);
            return this;
        }

        public Builder setMessage(String msg) {
            sdAlertDialog.setDialogMessage(msg);
            return this;
        }

        public Builder setCancel(String cancelText, final View.OnClickListener clickListener) {

            sdAlertDialog.setCancelListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    sdAlertDialog.dismiss();

                    if (clickListener == null) return;

                    clickListener.onClick(v);
                }
            });
            sdAlertDialog.setCancelText(cancelText);
            return this;
        }

        public Builder setConfirm(String confirmText, final View.OnClickListener clickListener) {
            sdAlertDialog.setConfirmListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    sdAlertDialog.dismiss();

                    if (clickListener == null) return;

                    clickListener.onClick(v);
                }
            });
            sdAlertDialog.setConfirmText(confirmText);
            return this;
        }

        public Builder setContentView(View view) {
            sdAlertDialog.setDialogContentView(view);
            return this;
        }

        public void show() {
            sdAlertDialog.show();
        }

        public void hide() {
            sdAlertDialog.dismiss();
        }
    }
}
