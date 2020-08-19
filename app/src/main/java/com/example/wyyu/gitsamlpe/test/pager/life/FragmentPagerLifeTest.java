package com.example.wyyu.gitsamlpe.test.pager.life;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2020-08-19.
 **/

public class FragmentPagerLifeTest extends Fragment {

    private static final String KEY_INDEX = "key_index";

    public static Fragment getFragment(int index) {
        Fragment fragment = new FragmentPagerLifeTest();
        Bundle arguments = new Bundle();
        arguments.putInt(KEY_INDEX, index);
        fragment.setArguments(arguments);
        return fragment;
    }

    private int index;

    private void showLog(String funName) {

        String log = "Fragment" + index + " -> " + funName + " \n";

        Log.e("PagerLifeTestLog", log);
        ActivityPagerLifeTest.appendLog(log);

        if (!(getActivity() instanceof ActivityPagerLifeTest)) {
            return;
        }
        ((ActivityPagerLifeTest) getActivity()).showLog();
    }

    @Override public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        index = getArguments() == null ? -1 : getArguments().getInt(KEY_INDEX);

        showLog("onAttach");
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLog("onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        showLog("onCreateView");

        View contentView = inflater.inflate(R.layout.fragment_pager_life_test, container, false);

        TextView textView = contentView.findViewById(R.id.pager_life_test_index);
        textView.setText(String.valueOf(index));

        return contentView;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLog("onActivityCreated");
    }

    @Override public void onStart() {
        super.onStart();
        showLog("onStart");
    }

    @Override public void onResume() {
        super.onResume();
        showLog("onResume");
    }

    @Override public void onPause() {
        super.onPause();
        showLog("onPause");
    }

    @Override public void onStop() {
        super.onStop();
        showLog("onStop");
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        showLog("onDestroyView");
    }

    @Override public void onDestroy() {
        super.onDestroy();
        showLog("onDestroy");
    }

    @Override public void onDetach() {
        super.onDetach();
        showLog("onDetach");
    }
}
