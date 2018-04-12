package com.example.wyyu.gitsamlpe.test.video;

import android.hardware.camera2.CameraDevice;
import android.os.Bundle;
import android.util.Size;
import android.view.TextureView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2018/4/10.
 **/

public class ActivityCameraTest extends FullScreenActivity {

    private CameraDevice cameraDevice;
    private TextureView textureView;

    private Size previewSize;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);
    }
}
