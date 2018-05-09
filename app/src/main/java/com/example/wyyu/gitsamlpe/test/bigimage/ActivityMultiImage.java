package com.example.wyyu.gitsamlpe.test.bigimage;

import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.bigimage.weight.MultiImageView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/5/9.
 **/

public class ActivityMultiImage extends ToolbarActivity {

    private static final int[] IMAGE_ARRAY = new int[] {
        R.mipmap.multi_image_1, R.mipmap.multi_image_2, R.mipmap.multi_image_3,
        R.mipmap.multi_image_4, R.mipmap.image_test_5, R.mipmap.multi_image_6,
        R.mipmap.multi_image_7, R.mipmap.multi_image_8, R.mipmap.multi_image_9
    };

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_image);

        initMultiImage();
    }

    private void initMultiImage() {
        initToolbar("MultiImage", 0xffffffff, 0xff84919b);

        List<Integer> imageIdList = new ArrayList<>(IMAGE_ARRAY.length);
        for (int image : IMAGE_ARRAY) {
            imageIdList.add(image);
        }
        ((MultiImageView) findViewById(R.id.multi_image_view)).setImageIdList(imageIdList);
    }
}
