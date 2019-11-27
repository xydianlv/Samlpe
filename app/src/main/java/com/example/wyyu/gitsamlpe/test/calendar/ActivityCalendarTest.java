package com.example.wyyu.gitsamlpe.test.calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2019-11-26.
 **/

public class ActivityCalendarTest extends ToolbarActivity implements View.OnClickListener {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCalendarTest.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_test);

        initActivity();
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calender_add:
                startActivity(new Intent(this, ActivityCalendarAdd.class));
                break;
            case R.id.calender_check:
                break;
            case R.id.calender_update:
                break;
            case R.id.calender_delete:
                break;
        }
    }

    private void initActivity() {

        initToolbar("CalendarTest", 0xffffffff, 0xff84919b);

        findViewById(R.id.calender_add).setOnClickListener(this);
        findViewById(R.id.calender_check).setOnClickListener(this);
        findViewById(R.id.calender_update).setOnClickListener(this);
        findViewById(R.id.calender_delete).setOnClickListener(this);
    }
}
