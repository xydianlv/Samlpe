package com.example.wyyu.gitsamlpe.test.pager.complex;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2020-01-15.
 **/

public class FragmentComplexPage extends Fragment {

    private static final String KEY_PAGE_INDEX = "key_page_index";

    public static Fragment getFragment(int index) {
        Bundle args = new Bundle();
        args.putInt(KEY_PAGE_INDEX, index);
        FragmentComplexPage fragment = new FragmentComplexPage();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView pageIndex;
    private View rootView;
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
        View contentView = inflater.inflate(R.layout.fragment_complex_page, container, false);

        pageIndex = contentView.findViewById(R.id.complex_page_index);
        rootView = contentView.findViewById(R.id.complex_page_root);

        return contentView;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        switch (index) {
            case 0:
                rootView.setBackgroundColor(0xffcb8e85);
                break;
            case 1:
                rootView.setBackgroundColor(0xffae716e);
                break;
            case 2:
                rootView.setBackgroundColor(0xffe27386);
                break;
        }
        pageIndex.setText(String.format("页面%s", String.valueOf(index)));
    }
}
