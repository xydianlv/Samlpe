package com.example.wyyu.gitsamlpe.test.text;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2019-12-31.
 **/

public class ActivityAnimText extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAnimText.class));
    }

    private static final String TEST_TEXT ="枯藤老树昏鸦，小桥流水人家，古道西风瘦马，夕阳西下，断肠人在天涯。";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_text);

        initActivity();
    }

    private void initActivity() {

        initToolbar("AnimText", 0xffffffff, 0xff84919b);

        ScrollableTextView textViewA = findViewById(R.id.scroll_text_view);
        textViewA.setText(TEST_TEXT);

        AnimTextView textView = findViewById(R.id.anim_text_view);
        getLifecycle().addObserver(textView);
        textView.setContentText("少时不识愁滋味\n爱上层楼\n爱上层楼\n为赋新词强说愁\n而今识尽愁滋味\n欲说还休\n欲说还休\n却道天凉好个秋");
    }
}
