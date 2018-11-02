package com.example.wyyu.gitsamlpe.test.softkeyboard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/11/2.
 **/

public class SendMessageBar extends LinearLayout implements View.OnClickListener {

    public SendMessageBar(Context context) {
        this(context, null);
    }

    public SendMessageBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SendMessageBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSendMessageBar();
    }

    private void initSendMessageBar() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_send_message_bar, this);

        findViewById(R.id.send_msg_icon_a).setOnClickListener(this);
        findViewById(R.id.send_msg_icon_b).setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        if (iconClickListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.send_msg_icon_a:
                iconClickListener.onClick(0);
                break;
            case R.id.send_msg_icon_b:
                iconClickListener.onClick(1);
                break;
        }
    }

    private OnSendIconClickListener iconClickListener;

    public void setIconClickListener(OnSendIconClickListener iconClickListener) {
        this.iconClickListener = iconClickListener;
    }

    public interface OnSendIconClickListener {

        void onClick(int type);
    }
}
