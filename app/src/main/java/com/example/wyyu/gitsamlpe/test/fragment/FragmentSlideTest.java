package com.example.wyyu.gitsamlpe.test.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
 * Created by wyyu on 2018/8/28.
 **/

public class FragmentSlideTest extends Fragment {

    private static final String KEY_NUMBER = "key_number";

    public static FragmentSlideTest getFragment(int number) {
        FragmentSlideTest fragmentBottom = new FragmentSlideTest();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_NUMBER, number);
        fragmentBottom.setArguments(bundle);
        return fragmentBottom;
    }

    @BindView(R.id.fragment_slide_title) TextView title;
    @BindView(R.id.fragment_slide_show) View show;

    private FragmentBottom fragmentBottom;
    private Unbinder unbinder;

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_slide_test, container, false);
        unbinder = ButterKnife.bind(this, contentView);

        initFragment();
        return contentView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initFragment() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }

        int number = bundle.getInt(KEY_NUMBER);
        title.setText(String.valueOf(number));

        show.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (getFragmentManager() != null) {
                    showFragmentBottom();
                }
            }
        });
    }

    void showFragmentBottom() {
        fragmentBottom = FragmentBottom.getFragment();

        fragmentBottom.setOnDismissListener(new FragmentBottom.OnDismissListener() {
            @Override public void onDismiss() {
                fragmentBottom = null;
            }
        });

        if (getFragmentManager() != null) {
            fragmentBottom.show(getFragmentManager(), "");
        }
    }

    void onKeyBackClick() {
        if (fragmentBottom != null) {
            fragmentBottom.hide();
        }
    }
}
