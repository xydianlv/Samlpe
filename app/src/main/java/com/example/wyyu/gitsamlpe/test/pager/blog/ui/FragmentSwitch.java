package com.example.wyyu.gitsamlpe.test.pager.blog.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.NoScrollViewPager;

/**
 * Created by wyyu on 2019/1/9.
 **/

public class FragmentSwitch extends Fragment {

    public static FragmentSwitch getFragment() {
        return new FragmentSwitch();
    }

    @BindView(R.id.switch_container) NoScrollViewPager viewPager;
    @BindView(R.id.switch_index) TextView switchIndex;
    @BindView(R.id.switch_cut) View cut;

    private Unbinder unbinder;
    private boolean isMain;

    @Override @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_switch, container, false);

        unbinder = ButterKnife.bind(this, contentView);
        initFragment();

        return contentView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void initFragment() {
        initBasicView();
        initViewPager();

        isMain = true;
    }

    private void initBasicView() {
        switchIndex.setText(String.valueOf(1));
        switchIndex.setVisibility(View.VISIBLE);

        cut.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                isMain = !isMain;
                viewPager.setCurrentItem(isMain ? 0 : 1, false);
            }
        });
        cut.setVisibility(View.VISIBLE);
    }

    private void initViewPager() {

        viewPager.setAdapter(new SwitchPagerAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(0);
    }

    private class SwitchPagerAdapter extends FragmentPagerAdapter {

        SwitchPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int i) {
            if (i == 0) {
                return FragmentSwitchA.getFragment();
            } else {
                return FragmentSwitchB.getFragment();
            }
        }

        @Override public int getCount() {
            return 2;
        }
    }
}
