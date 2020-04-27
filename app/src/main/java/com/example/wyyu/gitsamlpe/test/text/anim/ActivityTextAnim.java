package com.example.wyyu.gitsamlpe.test.text.anim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.WeakHandler;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2019-12-31.
 **/

public class ActivityTextAnim extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityTextAnim.class));
    }

    private static final String TEST_TEXT_L = "枯藤老树昏鸦，小桥流水人家，古道西风瘦马，夕阳西下，断肠人在天涯。";
    private static final String TEST_TEXT_S = "书籍是人类进步的阶梯";

    private WeakHandler handler;
    private boolean isFirst;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_anim);

        initActivity();
    }

    private void initActivity() {

        initToolbar("AnimText", 0xffffffff, 0xff84919b);

        showScrollText();
        showAnimText();
        showMarqueeText();
        showMarqueeListText();
    }

    private void showScrollText() {

        ScrollableTextView textView = findViewById(R.id.scroll_text_view);
        textView.setText(TEST_TEXT_L);

        handler = new WeakHandler(msg -> {
            if (isFirst) {
                textView.setText(TEST_TEXT_S);
                isFirst = false;
            } else {
                textView.setText(TEST_TEXT_L);
                isFirst = true;
            }

            if (handler != null) {
                handler.sendEmptyMessageDelayed(0, 10000);
            }
        });
        handler.sendEmptyMessageDelayed(0, 10000);
        isFirst = true;
    }

    private void showAnimText() {
        AnimTextView textView = findViewById(R.id.anim_text_view);
        getLifecycle().addObserver(textView);
        textView.setContentText("少时不识愁滋味\n爱上层楼\n爱上层楼\n为赋新词强说愁\n而今识尽愁滋味\n欲说还休\n欲说还休\n却道天凉好个秋");
    }

    private void showMarqueeText() {
        MarqueeTextView textViewB = findViewById(R.id.marquee_text_view);
        textViewB.setTextValue(TEST_TEXT_L);

        getLifecycle().addObserver(textViewB);
    }

    private void showMarqueeListText() {

        String[] nameArray = new String[] { "测试一号", "测试二号", "测试三号", "测试四号" };
        String[] textArray = new String[] {
            "新年快乐！新年快乐！！", "无边落木萧萧下，不尽长江滚滚来。", "天生我材必有用，千金散尽还复来。", "人生自古谁无死，留取丹心照汗青。"
        };

        MarqueeListView listView = findViewById(R.id.marquee_list_view);

        for (int index = 0; index < nameArray.length; index++) {
            listView.addNextValue(nameArray[index], textArray[index]);
        }
    }
}
