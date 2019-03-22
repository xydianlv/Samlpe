package com.example.wyyu.gitsamlpe.test.livedata;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019/3/20.
 **/

public class FragmentLiveDataTest extends Fragment {

    public static Fragment getFragment() {
        return new FragmentLiveDataTest();
    }

    private SeekBarViewModel seekBarViewModel;
    private SeekBar seekBar;

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup,
        Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_live_data_test, viewGroup, false);

        return contentView;
    }
}
