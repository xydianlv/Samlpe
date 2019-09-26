package com.example.wyyu.gitsamlpe.test.annotion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.wyyu.debug_annotate.IDebugAnnotate;
import com.wyyu.debug_annotate.StringAnnotate;

/**
 * Created by wyyu on 2019-09-26.
 *
 * 注解编译期动态注入测试
 **/

@StringAnnotate(value = "Method") public class ActivityAnnotionTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAnnotionTest.class));
    }

    @BindView(R.id.annotion_default) TextView textDefault;
    @BindView(R.id.annotion_text) TextView textView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotion_test);

        initToolbar("AnnotionTest", 0xffffffff, 0xff84919b);
        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

    private void initActivity() {
        try {
            Class annotateClass =
                Class.forName("com.example.wyyu.gitsamlpe.test.annotion.DebugAnnotateImpl");
            IDebugAnnotate debugAnnotate = (IDebugAnnotate) annotateClass.newInstance();

            textView.setText(debugAnnotate.getValue(ActivityAnnotionTest.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
