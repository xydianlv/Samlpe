package com.example.wyyu.gitsamlpe.test.pager.blog.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019/1/9.
 **/

public class FragmentSwitchA extends Fragment {

    public static FragmentSwitchA getFragment() {
        return new FragmentSwitchA();
    }

    private Unbinder unbinder;

    @Override @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_switch_a, container, false);

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
