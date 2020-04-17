package com.example.wyyu.gitsamlpe.test.function.finger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class ActivityFingerLockTest extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityFingerLockTest.class));
    }

    private FingerCheckUtil checkUtil;

    private ImageView imageView;
    private TextView textView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_lock_test);

        initFingerCheck();
    }

    private void initFingerCheck() {

        initTestView();

        initCheckUtil();
    }

    private void initTestView() {

        imageView = findViewById(R.id.finger_test_finger_view);

        textView = findViewById(R.id.finger_test_check_info);
    }

    private void initCheckUtil() {
        checkUtil = new FingerCheckUtil(this);

        checkUtil.setFingerCheckListener(new FingerCheckUtil.OnFingerCheckListener() {
            @SuppressLint("SetTextI18n") @Override public void onCheckError(String forbiddenTime) {
                textView.setText("失败次数过多，请" + forbiddenTime + "秒后重试");
            }

            @Override public void onCheckInfo(String checkInfo) {
                textView.setText(checkInfo);
            }

            @Override public void onCheckSuccess() {
                textView.setText("识别成功");

                imageView.setImageResource(R.mipmap.finger_check_blue);
            }

            @Override public void onCheckFailed() {
                textView.setText("识别失败");
            }
        });

        checkUtil.startFingerCheck();
    }

    @Override protected void onDestroy() {
        super.onDestroy();

        checkUtil.stopFingerCheck();
    }
}
