package com.example.wyyu.gitsamlpe.test.function.finger;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.util.Log;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class FingerCheckUtil {

    private FingerprintManagerCompat fingerprintManager;
    private CancellationSignal cancellationSignal;

    FingerCheckUtil(Context context) {

        cancellationSignal = new CancellationSignal();

        initFingerCheckManager(context);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SuppressLint({"ServiceCast", "MissingPermission"})
    private void initFingerCheckManager(Context context) {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                Log.d("~~FingerCheckUtil  ", "权限不足");
                return;
            }
            if (((FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE)).isHardwareDetected()) {
                fingerprintManager = FingerprintManagerCompat.from(context);
                Log.d("~~FingerCheckUtil  ", "初始化成功");
            } else {
                Log.d("~~FingerCheckUtil  ", "无法获取到指纹识别组件");
            }
        } catch (Throwable throwable) {
            Log.d("~~FingerCheckUtil  ", "异常  " + throwable.toString());
        }
    }

    void startFingerCheck() {

        if (fingerprintManager == null || fingerCheckListener == null) return;

        fingerprintManager.authenticate(null, 0, cancellationSignal, getCallBack(), null);

    }

    private FingerprintManagerCompat.AuthenticationCallback getCallBack() {
        return new FingerprintManagerCompat.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                super.onAuthenticationError(errMsgId, errString);

                fingerCheckListener.onCheckError("" + errString);
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                super.onAuthenticationHelp(helpMsgId, helpString);

                switch (helpMsgId) {
                    case 1001:      // 等待按下手指
                        fingerCheckListener.onCheckInfo("请按下手指");
                        break;
                    case 1002:      // 手指按下
                        fingerCheckListener.onCheckInfo("正在识别...");
                        break;
                    case 1003:      // 手指抬起
                        fingerCheckListener.onCheckInfo("手指抬起，请重新按下");
                        break;
                }
                fingerCheckListener.onCheckInfo("");
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                fingerCheckListener.onCheckSuccess();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

                fingerCheckListener.onCheckFailed();
            }
        };
    }

    void stopFingerCheck() {
        cancellationSignal.cancel();
    }

    private OnFingerCheckListener fingerCheckListener;

    void setFingerCheckListener(OnFingerCheckListener fingerCheckListener) {
        this.fingerCheckListener = fingerCheckListener;
    }

    interface OnFingerCheckListener {

        // 因失败次数过多，导致被禁用的时长
        void onCheckError(String forbiddenTime);

        // 提供 指纹识别 过程中的指导
        void onCheckInfo(String checkInfo);

        // 识别成功
        void onCheckSuccess();

        // 识别失败
        void onCheckFailed();
    }
}
