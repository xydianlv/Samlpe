package com.example.wyyu.gitsamlpe.test.pager.slide.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/1/5.
 **/

public class DataSource {

    private static class SourceHolder {
        private static DataSource dataSource = new DataSource();
    }

    private static final int[] COLOR_ARRAY = new int[]{0x55ff0000, 0x55ffff00, 0x5500ff00,
            0x5500ffff, 0x55000000, 0x55ae716e, 0x55b7d28d, 0x55ffe543, 0x55f1b8e4};

    private List<ViewData> viewDataList;

    private DataSource() {

        viewDataList = new ArrayList<>();

        for (int index = 0; index < COLOR_ARRAY.length; index++) {
            viewDataList.add(new ViewData("" + index, COLOR_ARRAY[index]));
        }
    }

    public ViewData getViewDataFromIndex(int index) {
        return index > viewDataList.size() || index < 0 ? null : viewDataList.get(index);
    }

    public static DataSource getDataSource() {
        return SourceHolder.dataSource;
    }
}
