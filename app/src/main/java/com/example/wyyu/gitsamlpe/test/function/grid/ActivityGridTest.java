package com.example.wyyu.gitsamlpe.test.function.grid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2020-05-28.
 **/

public class ActivityGridTest extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityGridTest.class));
    }

    @BindView(R.id.grid_item_container) GridLayout gridLayout;

    private static final int[] IMAGE_ARRAY = new int[] {
        R.mipmap.multi_image_1, R.mipmap.multi_image_2, R.mipmap.multi_image_3,
        R.mipmap.multi_image_4, R.mipmap.multi_image_5, R.mipmap.multi_image_6,
    };

    private int index;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_test);

        initActivity();
    }

    private void initActivity() {
        index = 0;

        findViewById(R.id.grid_item_bt_1).setOnClickListener(v -> {
            if (gridLayout != null) {
                ImageView imageView = new ImageView(ActivityGridTest.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(IMAGE_ARRAY[index % IMAGE_ARRAY.length]);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = gridLayout.getWidth() / 3;
                params.height = gridLayout.getWidth() / 3;
                imageView.setLayoutParams(params);
                gridLayout.addView(imageView);
                index++;
            }
        });
        findViewById(R.id.grid_item_bt_2).setOnClickListener(v -> {
            if (gridLayout != null && gridLayout.getChildCount() > 0) {
                gridLayout.removeViewAt(gridLayout.getChildCount() - 1);
            }
        });
    }
}
