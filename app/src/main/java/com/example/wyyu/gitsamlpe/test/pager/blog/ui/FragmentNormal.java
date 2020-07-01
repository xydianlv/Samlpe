package com.example.wyyu.gitsamlpe.test.pager.blog.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019/1/9.
 **/

public class FragmentNormal extends Fragment {

    private static final String KEY_FRAGMENT_INDEX = "key_fragment_index";

    public static FragmentNormal getFragment(int index) {
        FragmentNormal fragmentNormal = new FragmentNormal();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_FRAGMENT_INDEX, index);
        fragmentNormal.setArguments(bundle);
        return fragmentNormal;
    }

    @BindView(R.id.fragment_normal_index) TextView normalIndex;

    private Unbinder unbinder;

    @Override @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_normal, container, false);

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
        Bundle bundle = getArguments();

        normalIndex.setText(
            String.valueOf(bundle == null ? -1 : bundle.getInt(KEY_FRAGMENT_INDEX)));
    }
}
