package com.example.wyyu.gitsamlpe.test.anim.frame;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2020-04-17.
 **/

public class ActivityAnimFrame extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAnimFrame.class));
    }

    private AnimationDrawable animDrawable;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_frame);

        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (animDrawable != null) {
            animDrawable.stop();
            animDrawable = null;
        }
    }

    private void initActivity() {
        ImageView imageView = findViewById(R.id.anim_frame_refresh);
        imageView.setImageResource(R.drawable.anim_video_refresh);
        Drawable animationDrawable = imageView.getDrawable();
        if (animationDrawable instanceof AnimationDrawable) {
            animDrawable = (AnimationDrawable) animationDrawable;
            animDrawable.setOneShot(false);
            animDrawable.start();
        }
    }
}
