package com.example.banner.holder;

import android.view.View;

/**
 * Created by wyyu on 2019-07-01.
 **/

public interface CBViewHolderCreator {

    Holder createHolder(View itemView);

    int getLayoutId();
}
