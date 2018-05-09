package com.example.wyyu.gitsamlpe.test.bigimage.manager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import com.example.wyyu.gitsamlpe.framework.FullScreenDialog;
import com.example.wyyu.gitsamlpe.test.bigimage.weight.ImageContainer;

/**
 * Created by wyyu on 2018/5/8.
 **/

public class BigImageManager {

    public static void showFinishAnim(Activity activity, int imageId) {

        ImageContainer imageContainer = new ImageContainer(activity);
        imageContainer.setImageValue(imageId);

        final FullScreenDialog dialog = new FullScreenDialog(activity);
        dialog.show();
        dialog.setCancelable(false);
        dialog.setContentView(imageContainer);

        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(imageContainer, "alpha", 1.0f, 0.0f);

        animatorAlpha.setDuration(2000);
        animatorAlpha.start();

        animatorAlpha.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                dialog.cancel();
            }
        });
    }

    public static void setARGBStatusBar(Activity activity, View topView, int r, int g, int b,
        int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow()
                .getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.argb(alpha, r, g, b));
        }
        //else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //    setARGBStatusViewToAct(activity, r, g, b, alpha);
        //}
        //if (topView != null) {
        //    boolean isSetPadding = topView.getTag(IS_SET_PADDING_KEY) != null;
        //    if (!isSetPadding) {
        //        topView.setPadding(topView.getPaddingLeft(), topView.getPaddingTop() + getStatusBarHeight(activity), topView.getPaddingRight(), topView.getPaddingBottom());
        //        topView.setTag(IS_SET_PADDING_KEY, true);
        //    }
        //}
    }

    public static int getStatusHeight(Activity activity, View rootView) {
        int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        int rootHeight = rootView.getHeight();
        return screenHeight - rootHeight;
    }
}
