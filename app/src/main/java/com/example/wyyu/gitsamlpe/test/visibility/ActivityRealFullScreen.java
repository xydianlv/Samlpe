package com.example.wyyu.gitsamlpe.test.visibility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;

import com.example.wyyu.gitsamlpe.R;

import butterknife.BindView;

/**
 * Created by wyyu on 2020-01-14.
 **/

public class ActivityRealFullScreen extends AppCompatActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityRealFullScreen.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.ToolbarActivityTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_full_screen);

        setFullScreenShow();
        initClick();
    }

    private void setFullScreenShow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().getDecorView()
            //    .setSystemUiVisibility(
            //        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initClick() {
        View.OnClickListener clickListener = view -> {
            switch (view.getId()) {
                case R.id.full_screen_click_a:
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    break;
                case R.id.full_screen_click_b:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }
                    break;
            }
        };
        findViewById(R.id.full_screen_click_a).setOnClickListener(clickListener);
        findViewById(R.id.full_screen_click_b).setOnClickListener(clickListener);
    }
}
