package com.example.wyyu.gitsamlpe.test.text;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2018/5/4.
 **/

public class ActivityAdapterText extends ToolbarActivity {

    private static final String TEST_TEXT =
        "因为公司项目需要全文收起的功能，一共有2种UI所以需要写2个全文收起的控件，之前也是用过一个全文收起的控件，但是因为设计原因，在ListView刷新的时候会闪烁，我估计原因是因为控件本身的设计是需要先让TextView绘制完成，然后获取TextView一共有多少行，再判断是否需要全文收起按钮，如果需要，则吧TextView压缩回最大行数，添加全文按钮，这样就会造成ListView的Item先高后低，所以会发生闪烁，后面我也在网上找了几个，发现和之前的设计都差不多，虽然肯定是有解决了这个问题的控件，但是还是决定自己写了，毕竟找到控件后还需要测试，而现在的项目时间不充分啊（另外欢迎指教如何快速的找到自己需要的控件，有时候在Github上面搜索，都不知道具体该用什么关键字），而且自己写，也是一种锻炼。";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_text);

        initActivity();
    }

    private void initActivity() {
        showAdapterText();
        showExpandText();
        showSpanText();
        showAnimText();
    }

    private void showAdapterText() {
        AdapterTextView textView = findViewById(R.id.adapter_text_view);
        textView.setShowText(TEST_TEXT);
    }

    private void showExpandText() {
        ExpandTextView expandTextView = findViewById(R.id.expand_text_view);
        expandTextView.setMaxLines(4);
        expandTextView.setText(TEST_TEXT);
    }

    private void showSpanText() {
        TextView text = findViewById(R.id.img_span_text_view);
        Drawable drawable = getResources().getDrawable(R.drawable.img_volume);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        SpannableString spannableString = new SpannableString(TEST_TEXT);
        spannableString.setSpan(text, 0, 1, ImageSpan.ALIGN_BASELINE);

        text.setText(spannableString);
    }

    private void showAnimText() {
        AnimTextView textView = findViewById(R.id.anim_text_view);

        getLifecycle().addObserver(textView);

        textView.setContentText("少时不识愁滋味\n爱上层楼\n爱上层楼\n为赋新词强说愁\n而今识尽愁滋味\n欲说还休\n欲说还休\n却道天凉好个秋");
    }
}
