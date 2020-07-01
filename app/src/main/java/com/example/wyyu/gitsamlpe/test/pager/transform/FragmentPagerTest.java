package com.example.wyyu.gitsamlpe.test.pager.transform;

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
 * Created by wyyu on 2020-01-13.
 **/

public class FragmentPagerTest extends Fragment {

    private static final String KEY_PAGER_INDEX = "key_pager_index";

    public static Fragment getFragment(int index) {
        Fragment fragment = new FragmentPagerTest();
        Bundle args = new Bundle();
        args.putInt(KEY_PAGER_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    private TextView pagerIndex;
    private View rootView;

    private int index;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            index = getArguments().getInt(KEY_PAGER_INDEX);
        } else {
            index = -1;
        }
    }

    @Override @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_pager_test, container, false);

        pagerIndex = contentView.findViewById(R.id.pager_transformer_index);
        rootView = contentView.findViewById(R.id.pager_transformer_root);

        return contentView;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        switch (index) {
            case 0:
                rootView.setBackgroundColor(0x88a39480);
                break;
            case 1:
                rootView.setBackgroundColor(0x8809a4ff);
                break;
            case 2:
                rootView.setBackgroundColor(0x88f55066);
                break;
        }
        pagerIndex.setText(String.valueOf(index));
    }
}
