package com.example.wyyu.gitsamlpe.test.litho;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.litho.spec.Datum;
import com.example.wyyu.gitsamlpe.test.litho.spec.Decade;
import com.example.wyyu.gitsamlpe.test.litho.spec.MainList;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2019-09-26.
 **/

public class ActivityLithoTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityLithoTest.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ComponentContext componentContext = new ComponentContext(this);

        //Component component = MainItem.create(componentContext).title("Hello").info("info").build();

        //Component component = RecyclerCollectionComponent.create(componentContext)
        //    .disablePTR(true)
        //    .section(MainList.create(new SectionContext(componentContext)).build())
        //    .build();

        Component component = MainList.create(componentContext).dataModels(loadList()).build();

        setContentView(LithoView.create(componentContext, component));
    }

    private List<Datum> loadList() {
        List<Datum> datumList = new ArrayList<>();

        for (int index = 0; index < 32; index++) {
            datumList.add(new Decade("Title", String.valueOf(index)));
        }

        return datumList;
    }
}
