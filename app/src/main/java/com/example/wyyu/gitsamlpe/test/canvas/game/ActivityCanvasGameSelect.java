package com.example.wyyu.gitsamlpe.test.canvas.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

public class ActivityCanvasGameSelect extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCanvasGameSelect.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_game_select);

        initActivity();
    }

    private void initActivity() {
        initToolbar("CanvasGameSelect", 0xffffffff, 0xff84919b);
    }
}
