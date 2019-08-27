package com.example.wyyu.gitsamlpe.test.tangram.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.tangram.bean.CardTopBean;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class CardCellTopLayout extends FrameLayout {

    public CardCellTopLayout(@NonNull Context context) {
        this(context, null);
    }

    public CardCellTopLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardCellTopLayout(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private TextView text;

    private void initLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_card_cell_top, this);

        text = findViewById(R.id.cell_top_text);
    }

    public void cacheValue(CardTopBean topBean) {
        text.setText(topBean.value);
    }
}
