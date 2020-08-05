package com.example.wyyu.gitsamlpe.test.tangram.support;

import androidx.annotation.NonNull;
import android.util.Log;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.support.ExposureSupport;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class CustomExposureSupport extends ExposureSupport {

    public CustomExposureSupport() {
        setOptimizedMode(true);
    }

    @Override public void onExposure(@NonNull Card card, int offset, int position) {
        Log.e("CustomExposureSupport", "onExposure -> card : "
            + card.getClass().getName()
            + "  position : "
            + position
            + "  page : "
            + card.page);

        if (position / 3 == 10) {
            LiveEventBus.get().with(EventLoadMore.EVENT).setValue(new EventLoadMore());
        }
    }
}
