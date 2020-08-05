package com.example.wyyu.gitsamlpe.test.image.preview;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import java.util.List;

/**
 * Created by wyyu on 2020-01-14.
 **/

public class PreviewPagerAdapter extends FragmentPagerAdapter {

    private List<ItemBean> itemBeanList;

    PreviewPagerAdapter(FragmentManager fm, List<ItemBean> itemBeanList) {
        super(fm);

        this.itemBeanList = itemBeanList;
    }

    @Override public Fragment getItem(int i) {
        return FragmentImage.getFragment(itemBeanList.get(i));
    }

    @Override public int getCount() {
        return itemBeanList == null ? 0 : itemBeanList.size();
    }
}
