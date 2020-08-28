package com.example.wyyu.gitsamlpe.test.live;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.application.AppInstances;
import com.example.wyyu.gitsamlpe.framework.application.Constants;

/**
 * Created by wyyu on 2019/1/7.
 **/

public class ActivityLiveTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityLiveTest.class));
    }

    @BindView(R.id.live_test_listener_check) View listenerCheck;
    @BindView(R.id.live_test_service_check) View serviceCheck;
    @BindView(R.id.live_test_notify_check) View notifyCheck;
    @BindView(R.id.live_test_float_check) View floatCheck;

    private boolean openBackService;
    private boolean showFloat;
    private boolean openListener;
    private boolean showNotify;

    private Drawable checked;
    private Drawable noCheck;

    private Unbinder unbinder;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_test);

        unbinder = ButterKnife.bind(this);
        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @OnClick({
        R.id.live_test_back_service, R.id.live_test_float, R.id.live_test_close_listener,
        R.id.live_test_notify
    }) public void onEvent(View view) {
        switch (view.getId()) {
            case R.id.live_test_back_service:
                onClickService();
                break;
            case R.id.live_test_float:
                onClickFloat();
                break;
            case R.id.live_test_close_listener:
                onClickClose();
                break;
            case R.id.live_test_notify:
                onClickNotify();
                break;
        }
    }

    private void onClickService() {
        openBackService = !openBackService;

        if (openBackService) {
            KeepLiveManager.getManager().startBackService();
            serviceCheck.setBackground(checked);
        } else {
            KeepLiveManager.getManager().closeBackService();
            serviceCheck.setBackground(noCheck);
        }

        AppInstances.getSharedPreference()
            .edit()
            .putBoolean(Constants.KEY_OPEN_BACK_SERVICE, openBackService)
            .apply();
    }

    private void onClickFloat() {
        showFloat = !showFloat;

        if (showFloat) {
            KeepLiveManager.getManager().showFloatView(ActivityLiveTest.this);
            floatCheck.setBackground(checked);
        } else {
            KeepLiveManager.getManager().hideFloatView();
            floatCheck.setBackground(noCheck);
        }

        AppInstances.getSharedPreference()
            .edit()
            .putBoolean(Constants.KEY_SHOW_FLOAT_VIEW, showFloat)
            .apply();
    }

    private void onClickClose() {
        openListener = !openListener;

        if (openListener) {
            KeepLiveManager.getManager().openCloseListener();
            listenerCheck.setBackground(checked);
        } else {
            KeepLiveManager.getManager().closeCloseListener();
            listenerCheck.setBackground(noCheck);
        }

        AppInstances.getSharedPreference()
            .edit()
            .putBoolean(Constants.KEY_OPEN_CLOSE_LISTENER, openListener)
            .apply();
    }

    private void onClickNotify() {
        showNotify = !showNotify;

        if (showNotify) {
            KeepLiveManager.getManager().showNotifyBar();
            notifyCheck.setBackground(checked);
        } else {
            KeepLiveManager.getManager().hideNotifyBar();
            notifyCheck.setBackground(noCheck);
        }

        AppInstances.getSharedPreference()
            .edit()
            .putBoolean(Constants.KEY_SHOW_ALWAYS_NOTIFY, showNotify)
            .apply();
    }

    private void initActivity() {
        openBackService =
            AppInstances.getSharedPreference().getBoolean(Constants.KEY_OPEN_BACK_SERVICE, false);
        showFloat =
            AppInstances.getSharedPreference().getBoolean(Constants.KEY_SHOW_FLOAT_VIEW, false);
        openListener =
            AppInstances.getSharedPreference().getBoolean(Constants.KEY_OPEN_CLOSE_LISTENER, false);
        showNotify =
            AppInstances.getSharedPreference().getBoolean(Constants.KEY_SHOW_ALWAYS_NOTIFY, false);

        checked = getResources().getDrawable(R.drawable.button_circle_back_grey);
        noCheck = getResources().getDrawable(R.drawable.bg_round_grey);
    }
}
