package com.example.wyyu.gitsamlpe.test.slide.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.slide.data.ViewData;

/**
 * Created by wyyu on 2018/1/3.
 **/

public class ContentView extends LinearLayout {

    private TextView contentText;

    public ContentView(Context context) {
        super(context);
        initContentView();
    }

    public ContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initContentView();
    }

    public ContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initContentView();
    }

    private void initContentView() {

        LayoutInflater.from(getContext()).inflate(R.layout.slide_content_view, this);

        contentText = findViewById(R.id.content_text);
    }

    public void setViewData(ViewData viewData) {

        contentText.setTextColor(viewData.getViewColor());
        contentText.setText(viewData.getViewText());
    }
}
