package com.example.wyyu.gitsamlpe.test.canvas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;

/**
 * Created by wyyu on 2020-01-09.
 **/

public class ActivityCanvasDrawShow extends ToolbarActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_draw_show);

        initActivity();
    }

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCanvasDrawShow.class));
    }

    private void initActivity() {
        initToolbar("CanvasShow", 0xffffffff, 0xff84919b);

        ListViewMain canvasShowList = findViewById(R.id.canvas_show_list);

        canvasShowList.addItem("FigureShow",
            v -> ActivityCanvasFigure.open(ActivityCanvasDrawShow.this))
            .addItem("TextShow", v -> ActivityCanvasText.open(ActivityCanvasDrawShow.this))
            .addItem("PathShow", v -> ActivityCanvasPath.open(ActivityCanvasDrawShow.this))
            .addItem("ImageShow", new View.OnClickListener() {
                @Override public void onClick(View v) {

                }
            })
            .refreshList();
    }
}
