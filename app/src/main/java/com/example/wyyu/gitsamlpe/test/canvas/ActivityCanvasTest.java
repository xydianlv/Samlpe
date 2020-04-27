package com.example.wyyu.gitsamlpe.test.canvas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.canvas.figure.ActivityCanvasFigure;
import com.example.wyyu.gitsamlpe.test.canvas.image.ActivityCanvasImage;
import com.example.wyyu.gitsamlpe.test.canvas.path.ActivityCanvasPath;
import com.example.wyyu.gitsamlpe.test.canvas.text.ActivityCanvasText;

/**
 * Created by wyyu on 2020-01-09.
 **/

public class ActivityCanvasTest extends ToolbarActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_test);

        initActivity();
    }

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCanvasTest.class));
    }

    private void initActivity() {
        initToolbar("CanvasTest", 0xffffffff, 0xff84919b);

        ListViewMain canvasShowList = findViewById(R.id.canvas_show_list);

        canvasShowList.addItem("Figure", v -> ActivityCanvasFigure.open(ActivityCanvasTest.this))
            .addItem("Text", v -> ActivityCanvasText.open(ActivityCanvasTest.this))
            .addItem("Path", v -> ActivityCanvasPath.open(ActivityCanvasTest.this))
            .addItem("Image", v -> ActivityCanvasImage.open(ActivityCanvasTest.this))
            .refreshList();
    }
}
