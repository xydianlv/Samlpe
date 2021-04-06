package com.example.wyyu.gitsamlpe.test.pager.vertical.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2021/3/18.
 **/

public class FragmentPagerChild extends Fragment {

    private static final String KEY_PAGE_INDEX = "key_page_index";

    public static FragmentPagerChild getFragment(int index) {
        Bundle args = new Bundle();
        args.putInt(KEY_PAGE_INDEX, index);
        FragmentPagerChild fragment = new FragmentPagerChild();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView pageIndex;
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
        View contentView = inflater.inflate(R.layout.fragment_pager_child, container, false);

        pageIndex = contentView.findViewById(R.id.child_page_index);

        return contentView;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pageIndex.setText(String.format("页面%s", index + 1));
    }
}