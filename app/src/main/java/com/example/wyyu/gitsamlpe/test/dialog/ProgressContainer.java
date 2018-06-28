package com.example.wyyu.gitsamlpe.test.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/5/7.
 **/

public class ProgressContainer extends RelativeLayout {

    public ProgressContainer(Context context) {
        super(context);
        initProgressContainer();
    }

    public ProgressContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initProgressContainer();
    }

    public ProgressContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initProgressContainer();
    }

    private ProgressBar progressBar;
    private TextView percent;
    private TextView info;

    private void initProgressContainer() {

        LayoutInflater.from(getContext()).inflate(R.layout.layout_progress_view, this);

        progressBar = findViewById(R.id.progress_bar);
        percent = findViewById(R.id.progress_percent);
        info = findViewById(R.id.progress_info);

        progressBar.setMax(100);
    }

    public void refreshProgressPercent(int progress) {

        String progressInfo = String.valueOf(progress) + "%";
        percent.setText(progressInfo);

        progressBar.setProgress(progress);
    }

    void refreshProgressInfo(int index, int total) {
        String progressInfo = "正在上传中...(" + index + "/" + total + ")";
        info.setText(progressInfo);
    }
}
