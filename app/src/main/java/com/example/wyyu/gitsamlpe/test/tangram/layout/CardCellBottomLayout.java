package com.example.wyyu.gitsamlpe.test.tangram.layout;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.tangram.bean.CardBottomBean;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class CardCellBottomLayout extends FrameLayout {

    public CardCellBottomLayout(@NonNull Context context) {
        this(context, null);
    }

    public CardCellBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardCellBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private TextView text;

    private void initLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_card_cell_bottom, this);

        text = findViewById(R.id.cell_bottom_text);
    }

    public void cacheValue(CardBottomBean bottomBean) {
        text.setText(bottomBean.value);
    }
}
