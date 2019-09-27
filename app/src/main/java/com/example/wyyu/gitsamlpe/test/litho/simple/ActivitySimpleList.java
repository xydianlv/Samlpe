package com.example.wyyu.gitsamlpe.test.litho.simple;

import android.os.Bundle;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.litho.Datum;
import com.example.wyyu.gitsamlpe.test.litho.simple.data.DecadeSimple;
import com.example.wyyu.gitsamlpe.test.litho.simple.spec.SimpleList;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2019-09-27.
 **/

public class ActivitySimpleList extends ToolbarActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ComponentContext componentContext = new ComponentContext(this);

        Component component = SimpleList.create(componentContext).dataModels(loadList()).build();

        setContentView(LithoView.create(componentContext, component));
    }

    private List<Datum> loadList() {
        List<Datum> datumList = new ArrayList<>();

        for (int index = 0; index < 32; index++) {
            datumList.add(new DecadeSimple("Title", String.valueOf(index)));
        }

        return datumList;
    }
}
