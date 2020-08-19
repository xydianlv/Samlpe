package com.example.wyyu.gitsamlpe.test.pager.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2020-08-19.
 **/

public class ActivityPagerLifeTest extends ToolbarActivity {

    public static void open(Context context) {
        lifeLog = "";
        context.startActivity(new Intent(context, ActivityPagerLifeTest.class));
    }

    public static void appendLog(String log) {
        lifeLog = lifeLog == null ? "" : lifeLog + log;
    }

    public static String lifeLog = "";

    private TextView logText;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_life_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("PagerLifeTest");

        logText = findViewById(R.id.pager_life_test_log);

        ViewPager viewPager = findViewById(R.id.pager_life_test_pager);

        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new PagerLifeAdapter(getSupportFragmentManager(),
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
    }

    void showLog() {
        if (logText == null) {
            return;
        }
        if (TextUtils.isEmpty(lifeLog)) {
            logText.setText("");
        } else {
            logText.setText(lifeLog);
        }
    }

    private static class PagerLifeAdapter extends FragmentPagerAdapter {

        private PagerLifeAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override public int getCount() {
            return 4;
        }

        @NonNull @Override public Fragment getItem(int position) {
            return FragmentPagerLifeTest.getFragment(position);
        }
    }
}
