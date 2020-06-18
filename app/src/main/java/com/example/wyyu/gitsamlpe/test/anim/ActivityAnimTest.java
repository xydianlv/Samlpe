package com.example.wyyu.gitsamlpe.test.anim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.anim.click.ActivityLongClickTest;
import com.example.wyyu.gitsamlpe.test.anim.frame.ActivityAnimFrame;
import com.example.wyyu.gitsamlpe.test.anim.lottie.ActivityAnimLottie;
import com.example.wyyu.gitsamlpe.test.anim.multi.ActivityAnimMulti;
import com.example.wyyu.gitsamlpe.test.anim.solid.ActivityAnimSolid;
import com.example.wyyu.gitsamlpe.test.anim.spring.ActivityAnimSpring;
import com.example.wyyu.gitsamlpe.test.anim.svga.ActivityAnimSvgA;
import com.example.wyyu.gitsamlpe.test.anim.vector.ActivityAnimVector;
import com.example.wyyu.gitsamlpe.test.anim.wave.ActivityAnimWave;
import com.example.wyyu.gitsamlpe.test.anim.webp.ActivityAnimWebp;
import com.example.wyyu.gitsamlpe.test.anim.wrap.ActivityAnimWrap;

/**
 * Created by wyyu on 2020-04-17.
 **/

public class ActivityAnimTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAnimTest.class));
    }

    @BindView(R.id.anim_test_list) ListViewMain listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("AnimTest", 0xffffffff, 0xff84919b);

        listView.addItem("Wave", v -> ActivityAnimWave.open(ActivityAnimTest.this))
            .addItem("Frame", v -> ActivityAnimFrame.open(ActivityAnimTest.this))
            .addItem("Vector", v -> ActivityAnimVector.open(ActivityAnimTest.this))
            .addItem("Lottie", v -> ActivityAnimLottie.open(ActivityAnimTest.this))
            .addItem("Webp", v -> ActivityAnimWebp.open(ActivityAnimTest.this))
            .addItem("Multi", v -> ActivityAnimMulti.open(ActivityAnimTest.this))
            .addItem("Solid", v -> ActivityAnimSolid.open(ActivityAnimTest.this))
            .addItem("Wrap", v -> ActivityAnimWrap.open(ActivityAnimTest.this))
            .addItem("SvgA", v -> ActivityAnimSvgA.open(ActivityAnimTest.this))
            .addItem("Spring", v -> ActivityAnimSpring.open(ActivityAnimTest.this))
            .addItem("LongClick", v -> ActivityLongClickTest.open(ActivityAnimTest.this))
            .refreshList();
    }
}
