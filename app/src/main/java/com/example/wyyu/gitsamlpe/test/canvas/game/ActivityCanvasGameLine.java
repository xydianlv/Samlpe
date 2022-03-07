package com.example.wyyu.gitsamlpe.test.canvas.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

public class ActivityCanvasGameLine extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCanvasGameLine.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_game_line);

        initActivity();
    }

    private void initActivity() {
        initToolbar("CanvasGameLine", 0xffffffff, 0xff84919b);
    }
}
