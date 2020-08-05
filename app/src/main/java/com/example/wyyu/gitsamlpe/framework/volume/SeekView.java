package com.example.wyyu.gitsamlpe.framework.volume;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/8/11.
 **/

public class SeekView extends LinearLayout {

    public SeekView(Context context) {
        this(context, null);
    }

    public SeekView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSeekView();
    }

    private SeekBar seekBar;

    private void initSeekView() {

        LayoutInflater.from(getContext()).inflate(R.layout.layout_volume_seek_view, this);

        seekBar = findViewById(R.id.volume_seek_view);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    VolumeObservable.getObservable().refreshProgress(progress);
                }
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    void setProgress(int progress) {
        if (seekBar == null) {
            return;
        }
        seekBar.setProgress(progress);
    }

    void setMax(int max) {
        if (seekBar == null) {
            return;
        }
        seekBar.setMax(max);
    }
}
