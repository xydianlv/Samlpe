package com.example.wyyu.gitsamlpe.test.pager.complex;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2020-01-15.
 **/

public class FragmentComplexTest extends Fragment {

    private static final String KEY_PAGE_INDEX = "key_page_index";

    public static Fragment getFragment(int index) {
        Bundle args = new Bundle();
        args.putInt(KEY_PAGE_INDEX, index);
        FragmentComplexTest fragment = new FragmentComplexTest();
        fragment.setArguments(args);
        return fragment;
    }

    private ViewPager viewPager;
    private TextView viewIndex;

    private int index;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            index = bundle.getInt(KEY_PAGE_INDEX);
        } else {
            index = -1;
        }
    }

    @Override @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_complex_test, container, false);

        viewPager = contentView.findViewById(R.id.complex_frag_view_pager);
        viewIndex = contentView.findViewById(R.id.complex_frag_index);

        return contentView;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager.setAdapter(new ComplexPageAdapter(getChildFragmentManager()));
        viewIndex.setText(String.format("界面%s", String.valueOf(index)));
        viewPager.setOffscreenPageLimit(2);
    }
}
