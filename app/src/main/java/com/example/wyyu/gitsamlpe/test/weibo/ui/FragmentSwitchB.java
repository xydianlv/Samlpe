package com.example.wyyu.gitsamlpe.test.weibo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019/1/9.
 **/

public class FragmentSwitchB extends Fragment {

    public static FragmentSwitchB getFragment() {
        return new FragmentSwitchB();
    }

    private Unbinder unbinder;

    @Override @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_switch_b, container, false);

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
    }
}
