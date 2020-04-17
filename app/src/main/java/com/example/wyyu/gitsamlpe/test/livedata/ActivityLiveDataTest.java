package com.example.wyyu.gitsamlpe.test.livedata;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.test.function.shot.ActivityShotScreen;

/**
 * Created by wyyu on 2019/3/20.
 **/

public class ActivityLiveDataTest extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityLiveDataTest.class));
    }

    @BindView(R.id.live_data_pager) ViewPager viewPager;
    @BindView(R.id.live_data_edit) EditText editText;
    @BindView(R.id.live_data_save) View save;
    @BindView(R.id.live_data_open) View open;

    private LiveDataTimerViewModel liveDataTimerViewModel;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data_test);

        initActivity();
    }

    private void initActivity() {
        liveDataTimerViewModel = ViewModelProviders.of(this).get(LiveDataTimerViewModel.class);

        initView();
        initPager();
    }

    private void initView() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (editText != null) {
                    liveDataTimerViewModel.setElapsedText(editText.getText().toString());
                }
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ActivityShotScreen.open(ActivityLiveDataTest.this);
            }
        });
    }

    private void initPager() {
        viewPager.setAdapter(new TestAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(0);
    }

    private static class TestAdapter extends FragmentPagerAdapter {

        TestAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int i) {
            return FragmentLiveDataTest.getFragment(String.valueOf(i));
        }

        @Override public int getCount() {
            return 3;
        }

        @Override public Parcelable saveState() {
            return null;
        }

        @Override public void restoreState(Parcelable state, ClassLoader loader) {
            try {
                super.restoreState(state, getClass().getClassLoader());
            } catch (Exception e) {
                // null caught
            }
        }
    }
}
