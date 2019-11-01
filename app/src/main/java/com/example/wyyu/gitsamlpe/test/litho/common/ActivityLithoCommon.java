package com.example.wyyu.gitsamlpe.test.litho.common;

import android.os.Bundle;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.litho.common.component.ComponentCard;
import com.example.wyyu.gitsamlpe.test.litho.common.component.ComponentMultiImage;
import com.example.wyyu.gitsamlpe.test.litho.common.component.ComponentPercent;
import com.example.wyyu.gitsamlpe.test.litho.common.component.ComponentRowText;
import com.example.wyyu.gitsamlpe.test.litho.common.component.ComponentText;

/**
 * Created by wyyu on 2019-10-14.
 **/

public class ActivityLithoCommon extends ToolbarActivity {

    @BindView(R.id.percent_container) FrameLayout percentContainer;
    @BindView(R.id.text_container) FrameLayout textContainer;
    @BindView(R.id.multi_img_container) FrameLayout multiImgContainer;
    @BindView(R.id.card_container) FrameLayout cardContainer;
    @BindView(R.id.row_text_container) FrameLayout rowTextContainer;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litho_common);

        initActivity();
    }

    private void initActivity() {

        initToolbar("Common", 0xffffffff, 0xff84919b);

        textContainer.removeAllViews();
        textContainer.addView(new ComponentText().createView(this));

        percentContainer.removeAllViews();
        percentContainer.addView(new ComponentPercent().createView(this));

        multiImgContainer.removeAllViews();
        multiImgContainer.addView(new ComponentMultiImage().createView(this));

        cardContainer.removeAllViews();
        cardContainer.addView(new ComponentCard().createView(this));

        rowTextContainer.removeAllViews();
        rowTextContainer.addView(new ComponentRowText().createView(this));
    }
}
