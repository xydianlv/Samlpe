package com.example.wyyu.gitsamlpe.test.anim.click;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.framework.window.PressListenerView;

/**
 * Created by wyyu on 2020-06-16.
 **/

public class ActivityLongClickTest extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityLongClickTest.class));
    }

    @BindView(R.id.long_click_anim_a) PressListenerView listenerViewA;
    @BindView(R.id.long_click_anim_b) PressListenerView listenerViewB;
    @BindView(R.id.long_click_anim_c) PressListenerView listenerViewC;
    @BindView(R.id.long_click_anim) LongClickView animView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_click_test);

        initActivity();
    }

    private void initActivity() {
        listenerViewA.setOnPressListener(new PressListenerView.OnPressListener() {
            @Override public boolean onPressDown() {
                if (animView != null) {
                    animView.showAnim(listenerViewA);
                }
                return true;
            }

            @Override public boolean onPressUp() {
                if (animView != null) {
                    animView.endAnim();
                }
                return true;
            }
        });

        listenerViewB.setOnPressListener(new PressListenerView.OnPressListener() {
            @Override public boolean onPressDown() {
                if (animView != null) {
                    animView.showAnim(listenerViewB);
                }
                return true;
            }

            @Override public boolean onPressUp() {
                if (animView != null) {
                    animView.endAnim();
                }
                return true;
            }
        });

        listenerViewC.setOnPressListener(new PressListenerView.OnPressListener() {
            @Override public boolean onPressDown() {
                if (animView != null) {
                    animView.showAnim(listenerViewC);
                }
                return true;
            }

            @Override public boolean onPressUp() {
                if (animView != null) {
                    animView.endAnim();
                }
                return true;
            }
        });
    }
}
