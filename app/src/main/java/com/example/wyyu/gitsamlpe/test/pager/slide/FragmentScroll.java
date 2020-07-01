package com.example.wyyu.gitsamlpe.test.pager.slide;

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
 * Created by wyyu on 2018/6/19.
 **/

public class FragmentScroll extends Fragment {

    private static final String KEY_FRAGMENT_INDEX = "key_fragment_index";

    public static FragmentScroll getFragment(int index) {
        FragmentScroll fragmentScroll = new FragmentScroll();
        Bundle args = new Bundle();
        args.putInt(KEY_FRAGMENT_INDEX, index);
        fragmentScroll.setArguments(args);
        return fragmentScroll;
    }

    @BindView(R.id.scroll_text) TextView scrollText;

    private Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_scroll, container, false);
        unbinder = ButterKnife.bind(this, contentView);

        initFragment();
        return contentView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initFragment() {
        if (getArguments() != null) {
            scrollText.setText(String.valueOf(getArguments().getInt(KEY_FRAGMENT_INDEX)));
        }
    }
}
