package com.example.wyyu.gitsamlpe.test.pager.vertical.recycler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2021/3/18.
 **/

public class FragmentVerticalPagerA extends Fragment {

    private static final String KEY_PAGE_INDEX = "key_page_index";

    public static FragmentVerticalPagerA getFragment(int index) {
        Bundle args = new Bundle();
        args.putInt(KEY_PAGE_INDEX, index);
        FragmentVerticalPagerA fragment = new FragmentVerticalPagerA();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView pageIndex;
    private View rootView;
    private int index;

    private RecyclerView recyclerView;

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
        View contentView = inflater.inflate(R.layout.fragment_vertical_pager_a, container, false);

        pageIndex = contentView.findViewById(R.id.vertical_page_index);
        rootView = contentView.findViewById(R.id.vertical_page_root);
        recyclerView = contentView.findViewById(R.id.vertical_page_recycler);

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
        pageIndex.setText(String.format("页面%s", index));

        ChildAdapter adapter = new ChildAdapter();

        recyclerView.setLayoutManager(
            new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
    }
}
