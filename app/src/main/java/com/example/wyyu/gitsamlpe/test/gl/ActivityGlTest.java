package com.example.wyyu.gitsamlpe.test.gl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.gl.draw.ActivityGLDraw;
import com.example.wyyu.gitsamlpe.test.gl.image.ActivityGLImage;
import com.example.wyyu.gitsamlpe.test.gl.video.ActivityGLVideo;

/**
 * Created by wyyu on 2021/3/9.
 **/

public class ActivityGlTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityGlTest.class));
    }

    @BindView(R.id.gl_test_list) ListViewMain listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("GLTest", 0xffffffff, 0xff84919b);

        listView.addItem("DrawTest", v -> ActivityGLDraw.open(ActivityGlTest.this))
            .addItem("ImageTest", v -> ActivityGLImage.open(ActivityGlTest.this))
            .addItem("VideoTest", v -> ActivityGLVideo.open(ActivityGlTest.this))
            .refreshList();
    }
}
