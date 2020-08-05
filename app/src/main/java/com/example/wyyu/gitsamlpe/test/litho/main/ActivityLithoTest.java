package com.example.wyyu.gitsamlpe.test.litho.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.litho.Datum;
import com.example.wyyu.gitsamlpe.test.litho.common.ActivityLithoCommon;
import com.example.wyyu.gitsamlpe.test.litho.custom.ActivityCustom;
import com.example.wyyu.gitsamlpe.test.litho.define.ActivityDefineList;
import com.example.wyyu.gitsamlpe.test.litho.multi.ActivityLithoMulti;
import com.example.wyyu.gitsamlpe.test.litho.simple.ActivitySimpleList;
import com.example.wyyu.gitsamlpe.test.litho.text.ActivityLithoText;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import java.util.Arrays;

/**
 * Created by wyyu on 2019-09-26.
 **/

public class ActivityLithoTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityLithoTest.class));
    }

    private static final Datum[] DATUM_ARRAY = new Datum[] {
        new DecadeMain("SimpleList", ActivitySimpleList.class, Color.WHITE),
        new DecadeMain("Custom", ActivityCustom.class, Color.WHITE),
        new DecadeMain("MulyiList", ActivityLithoMulti.class, Color.WHITE),
        new DecadeMain("Define", ActivityDefineList.class, Color.WHITE),
        new DecadeMain("Common", ActivityLithoCommon.class, Color.WHITE),
        new DecadeMain("Text", ActivityLithoText.class, Color.WHITE)
    };

    @BindView(R.id.litho_container) FrameLayout container;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litho_test);

        initToolbar("LithoTest", 0xffffffff, 0xff84919b);

        //ComponentContext componentContext = new ComponentContext(this);
        //Component component =
        //    MainList.create(componentContext).dataModels(Arrays.asList(DATUM_ARRAY)).build();
        //container.addView(LithoView.create(componentContext, component));
    }
}
