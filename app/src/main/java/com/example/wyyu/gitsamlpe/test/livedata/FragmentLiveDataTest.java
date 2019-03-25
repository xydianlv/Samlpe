package com.example.wyyu.gitsamlpe.test.livedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019/3/20.
 **/

public class FragmentLiveDataTest extends Fragment {

    private static final String KEY_FRAGMENT_INDEX = "key_fragment_index";

    public static Fragment getFragment(String index) {
        FragmentLiveDataTest fragment = new FragmentLiveDataTest();
        Bundle args = new Bundle();
        args.putString(KEY_FRAGMENT_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.frag_live_data_index) TextView dataIndex;
    @BindView(R.id.frag_live_data_text) TextView dataText;

    private Unbinder unbinder;
    private String index;

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup,
        Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_live_data_test, viewGroup, false);

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

        if (getActivity() != null) {
            LiveDataTimerViewModel viewModel =
                ViewModelProviders.of(getActivity()).get(LiveDataTimerViewModel.class);

            Observer<String> observer = new Observer<String>() {
                @Override public void onChanged(@Nullable String text) {
                    if (dataText != null) {
                        dataText.setText(text);
                    }
                    Log.e("LiveDataTest", "index : " + index + "    text : " + text);
                }
            };

            viewModel.getElapsedTime().observe(this, observer);
        }

        if (getArguments() != null) {
            index = getArguments().getString(KEY_FRAGMENT_INDEX);
            if (!TextUtils.isEmpty(index)) {
                dataIndex.setText(index);
            }
        }
    }
}
