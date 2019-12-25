package com.example.wyyu.gitsamlpe.test.svga;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2019-12-23.
 **/

public class ActivitySvgAnim extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySvgAnim.class));
    }

    @BindView(R.id.svg_anim_surface_container) FrameLayout surfaceContainer;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_anim);

        initActivity();
    }

    private void initActivity() {
        surfaceContainer.removeAllViews();
        surfaceContainer.addView(new SvgASurface(this));
    }
}
