package com.example.wyyu.gitsamlpe.test.pager;

import android.view.View;
import android.widget.ImageView;
import com.example.banner.holder.Holder;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019-07-01.
 **/

class BannerHolder extends Holder<Integer> {

    private ImageView bannerImage;

    BannerHolder(View itemView) {
        super(itemView);
    }

    @Override protected void initView(View itemView) {
        if (itemView != null) {
            bannerImage = itemView.findViewById(R.id.banner_image);
        }
    }

    @Override public void updateUI(Integer data) {
        bannerImage.setImageResource(data);
    }
}
