package com.example.wyyu.gitsamlpe.test.tangram.layout;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.tangram.bean.CardMidBean;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class CardCellMidLayout extends FrameLayout {

    public CardCellMidLayout(@NonNull Context context) {
        this(context, null);
    }

    public CardCellMidLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardCellMidLayout(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private TextView text;

    private void initLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_card_mid_layout, this);

        text = findViewById(R.id.cell_mid_text);
    }

    public void cacheValue(CardMidBean midBean) {
        text.setText(midBean.value);
    }
}
