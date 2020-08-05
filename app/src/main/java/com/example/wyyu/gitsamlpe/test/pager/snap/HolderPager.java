package com.example.wyyu.gitsamlpe.test.pager.snap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2020-05-13.
 **/

public class HolderPager extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.layout_holder_pager;

    private static final int[] RES_ARRAY = new int[] {
        0xff9fbfbf, 0xffa39480, 0xffc86f67, 0xffb7d28d
    };

    private TextView pagerIndex;

    HolderPager(@NonNull View itemView) {
        super(itemView);

        pagerIndex = itemView.findViewById(R.id.pager_index);
    }

    void cacheData(int index) {
        pagerIndex.setText(String.valueOf(index));
        pagerIndex.setBackgroundColor(RES_ARRAY[index % RES_ARRAY.length]);
    }
}
