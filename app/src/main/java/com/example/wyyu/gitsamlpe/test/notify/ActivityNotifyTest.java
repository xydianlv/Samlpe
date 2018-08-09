package com.example.wyyu.gitsamlpe.test.notify;

import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.util.notify.NotifyManager;

/**
 * Created by wyyu on 2018/8/9.
 **/

public class ActivityNotifyTest extends ToolbarActivity {

    @BindView(R.id.list_view_notify) ListViewMain listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("NotifyTest");

        listView.addNewItem("NoSoundNotify", new View.OnClickListener() {
            @Override public void onClick(View v) {
                NotifyManager.getManager()
                    .sendNotify(NotifyManager.ChannelValue.通知, "测试", "什么动静都没有",
                        R.mipmap.navbar_select, 0, null);
            }
        });

        listView.addNewItem("NoLightNotify", new View.OnClickListener() {
            @Override public void onClick(View v) {
                NotifyManager.getManager()
                    .sendNotify(NotifyManager.ChannelValue.通知, "通知", "什么动静都有",
                        R.mipmap.navbar_select, R.mipmap.test_logo_grey, null);
            }
        });
    }
}
