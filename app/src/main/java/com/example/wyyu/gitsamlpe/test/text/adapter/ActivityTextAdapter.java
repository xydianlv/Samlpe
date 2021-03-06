package com.example.wyyu.gitsamlpe.test.text.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2018/5/4.
 **/

public class ActivityTextAdapter extends ToolbarActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_adapter);

        initActivity();
    }

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityTextAdapter.class));
    }

    private static final String DIVIDE_TEXT =
        "因为公司项\n目需要\n全文收起的功能，一共有23242343\n2种UI所以需要写2个全文收起的控\n件，之前也是\n用过一个全文收起的控件，但是因为设计原因";

    private static final String TEST_TEXT =
        "因为公司项目需要全文收起的功能，一共有232423432种UI所以需要写2个全文收起的控件，之前也是用过一个全文收起的控件，但是因为设计原因，在ListView刷新的时候会闪烁，我估计原因是因为控件本身的设计是需要先让TextView绘制完成，然后获取TextView一共有多少行，再判断是否需要全文收起按钮，如果需要，则吧TextView压缩回最大行数，添加全文按钮，这样就会造成ListView的Item先高后低，所以会发生闪烁，后面我也在网上找了几个，发现和之前的设计都差不多，虽然肯定是有解决了这个问题的控件，但是还是决定自己写了，毕竟找到控件后还需要测试，而现在的项目时间不充分啊（另外欢迎指教如何快速的找到自己需要的控件，有时候在Github上面搜索，都不知道具体该用什么关键字），而且自己写，也是一种锻炼。";

    private void initActivity() {
        initToolbar("AdapterText", 0xffffffff, 0xff84919b);

        showAdapterText();
        showExpandText();
        showEllipseText();
        showMiddleEllipse();
        showAdjustText();
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

    private void showEllipseText() {
        TextView textView = findViewById(R.id.img_ellipse_text_view);
        String string = TextUtils.ellipsize(TEST_TEXT, textView.getPaint(),
            UIUtils.getScreenWidth() * textView.getMaxLines() - UIUtils.dpToPx(45.0f),
            TextUtils.TruncateAt.END).toString();
        string = string + "…全文";
        textView.setText(string);
    }

    private void showMiddleEllipse() {
        TextView textView = findViewById(R.id.img_ellipse_middle_text);
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        textView.setText(TEST_TEXT);
    }

    private void showAdjustText() {
        AdjustTextView textView = findViewById(R.id.adjust_text_test);
        textView.setMaxLines(4);
        textView.setText(TEST_TEXT);
    }
}
