package com.example.wyyu.gitsamlpe.test.pager.banner;

import android.view.View;
import android.widget.ImageView;
import com.example.banner.holder.Holder;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2020-06-24.
 **/

class BannerHolder extends Holder<Integer> {

    public static final int LAYOUT = R.layout.layout_banner_holder;

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