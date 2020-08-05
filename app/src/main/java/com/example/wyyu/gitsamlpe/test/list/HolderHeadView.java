package com.example.wyyu.gitsamlpe.test.list;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019/3/1.
 **/

public class HolderHeadView extends FrameLayout {

    public HolderHeadView(@NonNull Context context) {
        this(context, null);
    }

    public HolderHeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HolderHeadView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeadView();
    }

    private TextView textMiddle;
    private TextView textRight;
    private TextView textLeft;

    private void initHeadView() {

        LayoutInflater.from(getContext()).inflate(R.layout.layout_holder_head_view, this);

        textMiddle = findViewById(R.id.head_text_middle);
        textRight = findViewById(R.id.head_text_right);
        textLeft = findViewById(R.id.head_text_left);
    }

    public void setHeadValue(MultiData multiData) {
        if (textMiddle == null || textRight == null || textLeft == null) {
            setVisibility(GONE);
            return;
        }
        if (multiData == null) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);

            textMiddle.setVisibility(GONE);
            textRight.setVisibility(GONE);
            textLeft.setVisibility(GONE);

            switch (multiData.type) {
                case 0:
                    textLeft.setVisibility(VISIBLE);
                    textLeft.setText(String.valueOf(multiData.index));
                    break;
                case 1:
                    textMiddle.setVisibility(VISIBLE);
                    textMiddle.setText(String.valueOf(multiData.index));
                    break;
                case 2:
                    textRight.setVisibility(VISIBLE);
                    textRight.setText(String.valueOf(multiData.index));
                    break;
            }
        }
    }
}
