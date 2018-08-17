package com.example.wyyu.gitsamlpe.framework.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/8/17.
 **/

public class SDTediumDialog extends LinearLayout implements View.OnClickListener {

    private AlertDialog alertDialog;

    private LinearLayout container;
    private TextView title;

    public SDTediumDialog(Context context) {
        this(context, null);
    }

    public SDTediumDialog(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SDTediumDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTediumDialog();
    }

    private void initTediumDialog() {

        LayoutInflater.from(getContext()).inflate(R.layout.layout_tedium_dialog, this);

        initBasicValue();
        initDialog();
    }

    private void initBasicValue() {
        container = findViewById(R.id.tedium_dialog_container);
        title = findViewById(R.id.tedium_dialog_title);
    }

    private void initDialog() {
        alertDialog = new AlertDialog.Builder(getContext()).create();

        Window window = alertDialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(0));

        alertDialog.setView(this);
    }

    public void addNewItem(String info, int tag, boolean isLast) {
        TediumItemView itemView = new TediumItemView(getContext());

        itemView.setItemValue(info, isLast);
        itemView.setTag(tag);

        itemView.setOnClickListener(this);

        container.addView(itemView);
    }

    public void setTitle(String text) {
        title.setText(text);
    }

    public void show() {
        alertDialog.show();
    }

    @Override public void onClick(View v) {
        if (tediumClickListener == null) {
            return;
        }
        tediumClickListener.onClick((int) v.getTag());
        alertDialog.dismiss();
    }

    private OnTediumClickListener tediumClickListener;

    public void setOnTediumClickListener(OnTediumClickListener tediumClickListener) {
        this.tediumClickListener = tediumClickListener;
    }

    public interface OnTediumClickListener {

        void onClick(int tag);
    }
}
