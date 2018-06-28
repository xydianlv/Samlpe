package com.example.wyyu.gitsamlpe.test.lock.gesture;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class ActivityGestureLockTest extends FullScreenActivity {

    private View bottomFunView;
    private View topFunView;

    private boolean isShowMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_lock_test);

        initGestureLockTest();
    }

    private void initGestureLockTest() {

        initBasicView();

        initGestureLockView();

    }

    private void initBasicView() {

        bottomFunView = findViewById(R.id.gesture_test_bottom_view);
        topFunView = findViewById(R.id.gesture_test_top_view);

        isShowMain = false;

        findViewById(R.id.gesture_lock_forget_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UToast.showShort(ActivityGestureLockTest.this, "那你好棒棒哦");
            }
        });
    }

    private void initGestureLockView() {
        final GestureLockView gestureLockView = findViewById(R.id.gesture_test_gesture_view);

        gestureLockView.setOnDrawLockComplete(new GestureLockView.OnDrawLockComplete() {
            @Override
            public boolean onComplete(String password) {
                return checkPassWord(password);
            }
        });

    }

    private boolean checkPassWord(String passWord) {

        if (passWord.equals("01258")) {

            funViewAnimation(true);

            return true;
        } else {
            return false;
        }
    }

    private void funViewAnimation(boolean isOpen) {
        AnimatorSet animatorSet = new AnimatorSet();
        int funNum = isOpen ? 1 : 0;

        isShowMain = isOpen;

        ObjectAnimator animatorBottom = ObjectAnimator.ofFloat(bottomFunView,
                "translationY", bottomFunView.getTranslationY(), funNum * bottomFunView.getHeight());
        ObjectAnimator animatorTop = ObjectAnimator.ofFloat(topFunView,
                "translationY", topFunView.getTranslationY(), funNum * -topFunView.getHeight());

        animatorSet.play(animatorTop).with(animatorBottom);
        animatorSet.setDuration(1000);

        animatorSet.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && isShowMain) {
            funViewAnimation(false);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
