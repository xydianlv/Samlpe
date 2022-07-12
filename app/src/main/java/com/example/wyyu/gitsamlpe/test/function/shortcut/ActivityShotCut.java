package com.example.wyyu.gitsamlpe.test.function.shortcut;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityShotCut extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityShotCut.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_cut);

        initActivity();
    }

    private void initActivity() {
        initToolbar("ShotCutTest", 0xffffffff, 0xff84919b);

        findViewById(R.id.shot_cut_click).setOnClickListener(view -> {
            createShotCut();
        });
    }

    private void createShotCut() {
        Intent intent = new Intent(this, ActivityShotCutPreview.class);
        intent.putExtra(Constants.SHOT_CUT_NAME_KEY, Constants.SHOT_CUT_NAME);
        intent.putExtra(Constants.SHOT_CUT_ID_KEY, Constants.SHOT_CUT_ID);
        intent.putExtra("isShortcut", true);
        intent.setAction(Intent.ACTION_VIEW);
        ShortcutInfoCompat shortcutInfoCompat = new ShortcutInfoCompat.Builder(this, Constants.SHOT_CUT_ID)
                .setShortLabel(Constants.SHOT_CUT_NAME)
                .setAlwaysBadged()
                .setIcon(IconCompat.createWithResource(this, R.mipmap.icon_test))
                .setIntent(intent)
                .build();

        // UID 不同，Label 相同的，重复添加创建新的快捷方式
        // UID 相同，Label 不同的，重复添加会更新
        if (isExit(Constants.SHOT_CUT_ID)) {
            updatePinShortcut(shortcutInfoCompat);
        } else {
            createShortcut(shortcutInfoCompat);
        }
    }

    private boolean isExit(String id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager manager = getSystemService(ShortcutManager.class);
            if (manager == null) {
                return false;
            }
            List<ShortcutInfo> pinnedShortcuts = manager.getPinnedShortcuts();
            for (ShortcutInfo info : pinnedShortcuts) {
                if (info.getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updatePinShortcut(ShortcutInfoCompat shortcutInfoCompat) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return;
        }
        ShortcutManager manager = getSystemService(ShortcutManager.class);
        if (manager == null) {
            return;
        }
        ShortcutInfo info = shortcutInfoCompat.toShortcutInfo();

        List<ShortcutInfo> infoList = new ArrayList<>();
        infoList.add(info);

        boolean result = manager.updateShortcuts(infoList);
        ULog.show("updatePinShortcut result : " + result);
    }

    private void createShortcut(ShortcutInfoCompat shortcutInfoCompat) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SHOT_CUT_EXTRA_ID_KEY, shortcutInfoCompat.getId());
        bundle.putString(Constants.SHOT_CUT_EXTRA_LABEL_KEY, shortcutInfoCompat.getShortLabel().toString());

        Intent sendIntent = new Intent(Constants.SHOT_CUT_ACTION);
        sendIntent.setComponent(new ComponentName(this, ShotCutReceiver.class));
        sendIntent.putExtras(bundle);

        IntentSender intentSender = PendingIntent.getBroadcast(this, 0, sendIntent, PendingIntent.FLAG_ONE_SHOT).getIntentSender();
        boolean requestPinShortcut = ShortcutManagerCompat.requestPinShortcut(this, shortcutInfoCompat, intentSender);
        ULog.show("createShortcut result : " + requestPinShortcut);
    }
}
