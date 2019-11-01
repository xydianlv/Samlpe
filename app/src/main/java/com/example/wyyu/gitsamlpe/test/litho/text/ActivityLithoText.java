package com.example.wyyu.gitsamlpe.test.litho.text;

import android.os.Bundle;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2019-11-01.
 **/

public class ActivityLithoText extends ToolbarActivity {

    private static final String TEST_TEXT =
        "因为公司项目需要全文收起的功能，一共有2种UI所以需要写2个全文收起\n的控件，之前也是用过一个全文收起的控件，但是因为设计原因，在ListView刷新的时候会闪烁，我估计原因是因为控件本身的设计是需要先让TextView绘制完成，然后获取TextView一共有多少行，再判断是否需要全文收起按钮，如果需要，则吧TextView压缩回最大行数，添加全文按钮，这样就会造成ListView的Item先高后低，所以会发生闪烁，后面我也在网上找了几个，发现和之前的设计都差不多，虽然肯定是有解决了这个问题的控件，但是还是决定自己写了，毕竟找到控件后还需要测试，而现在的项目时间不充分啊（另外欢迎指教如何快速的找到自己需要的控件，有时候在Github上面搜索，都不知道具体该用什么关键字），而且自己写，也是一种锻炼。";

    private static final String TEST_TEXT_M = "白日依山尽，\n黄河入海流。\n欲穷千里目，\n更上一层楼。\n";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litho_text);

        initActivity();
    }

    private void initActivity() {
        initToolbar("Text", 0xffffffff, 0xff84919b);

        loadLithoShow();
        loadMarkTextShow();
        loadPoemShow();
    }

    private void loadLithoShow() {
        FrameLayout container = findViewById(R.id.text_container);

        ComponentSpanText spanText = new ComponentSpanText();
        spanText.setViewData(TEST_TEXT, 15.0f, true, 3);

        container.removeAllViews();
        container.addView(spanText.createView(this));
    }

    private void loadMarkTextShow() {
        MarkTextView textView = findViewById(R.id.mark_text);
        textView.setTextValue(TEST_TEXT,
            new HeightLightText.Builder(HeightLightText.LightType.LIGHT).insertIndex(0)
                .textColor(0xff00ff00)
                .lightLength(4)
                .build(),
            new HeightLightText.Builder(HeightLightText.LightType.DRAWABLE).insertIndex(42)
                .insertDrawable(getResources().getDrawable(R.mipmap.icon_test))
                .build(),
            new HeightLightText.Builder(HeightLightText.LightType.INSERT).insertIndex(32)
                .insertText("哈哈哈哈哈哈哈")
                .textColor(0xffff0000)
                .build());
    }

    private void loadPoemShow() {
        MarkTextView textView = findViewById(R.id.poem_text);
        textView.setTextValue(TEST_TEXT_M);
    }
}
