package com.example.wyyu.gitsamlpe.test.litho.custom;

import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.litho.custom.component.ComponentFoot;
import com.example.wyyu.gitsamlpe.test.litho.custom.widget.LayoutContent;
import com.example.wyyu.gitsamlpe.test.litho.custom.widget.LayoutFoot;
import com.example.wyyu.gitsamlpe.test.litho.custom.widget.LayoutHead;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import java.util.concurrent.TimeUnit;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by wyyu on 2019-09-30.
 **/

public class ActivityCustom extends ToolbarActivity {

    private static final String CONTENT =
        "这是一段长的测试文字，最多展示四行，用一些乱七八糟的语言让它长度尽可能地长，比如复制一段Litho的描述：Litho 是 FaceBook2017年上半年开源的声明式UI渲染框架，采用 Yoga 这个布局引擎 把 measure 和 layout 放到后台线程";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litho_custom);

        initActivity();
    }

    private void initActivity() {
        initToolbar("Custom", 0xffffffff, 0xff84919b);

        initHead();
        initContent();
        initFoot();
        initLitho();
    }

    private void initHead() {
        ((LayoutHead) findViewById(R.id.custom_head_a)).setHeadValue("HeadA", "info");
    }

    private void initContent() {
        ((LayoutContent) findViewById(R.id.custom_content_a)).setContentValue(CONTENT,
            R.mipmap.image_test_1);
    }

    private void initFoot() {
        AndroidSchedulers.mainThread()
            .createWorker()
            .schedule(() -> ((LayoutFoot) findViewById(R.id.custom_foot_a)).setValue(47, 32), 5000,
                TimeUnit.MILLISECONDS);
    }

    private void initLitho() {
        ComponentContext componentContext = new ComponentContext(this);

        LithoView.create(componentContext, ComponentFoot.create(componentContext).build());
    }
}
