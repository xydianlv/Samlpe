package com.example.wyyu.gitsamlpe.test.calendar;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.EditText;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.example.wyyu.gitsamlpe.util.permission.IPermissionObserver;
import com.example.wyyu.gitsamlpe.util.permission.PermissionCheck;
import com.example.wyyu.gitsamlpe.util.permission.PermissionItem;
import com.example.wyyu.gitsamlpe.util.permission.PermissionItemKey;
import com.example.wyyu.gitsamlpe.util.permission.PermissionObservable;
import java.util.TimeZone;

/**
 * Created by wyyu on 2019-11-26.
 **/

public class ActivityCalendarAdd extends ToolbarActivity implements IPermissionObserver {

    private static final String CALENDER_URL = "content://com.android.calendar/calendars";
    private static final String CALENDER_EVENT_URL = "content://com.android.calendar/events";
    private static final String CALENDER_REMINDER_URL = "content://com.android.calendar/reminders";

    private static final String CALENDARS_NAME = "wyyu";
    private static final String CALENDARS_ACCOUNT_NAME = "wyyu";
    private static final String CALENDARS_ACCOUNT_TYPE = "com.android.wyyu";
    private static final String CALENDARS_DISPLAY_NAME = "wyyu账户";

    @BindView(R.id.calendar_add_title) EditText title;
    @BindView(R.id.calendar_add_des) EditText des;
    @BindView(R.id.calendar_add_start) EditText start;
    @BindView(R.id.calendar_add_end) EditText end;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_add);

        PermissionObservable.getObservable().attach(this);
        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        PermissionObservable.getObservable().detach(this);
    }

    private void initActivity() {
        initToolbar("CalendarAdd", 0xffffffff, 0xff84919b);
        requestPermission();

        findViewById(R.id.calendar_add_submit).setOnClickListener(
            v -> submitEvent(title.getText().toString(), des.getText().toString(),
                System.currentTimeMillis(), System.currentTimeMillis()));
    }

    private void requestPermission() {
        PermissionCheck.getInstance()
            .check(this, new PermissionItem(PermissionItemKey.日历, Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR).rationalText("开启存储权限才能选图和视频")
                .confirmText("确认")
                .denyText("取消")
                .goSetting(false));
    }

    private void submitEvent(String title, String description, long timeStart, long previousDate) {

        long userId = getCalendarId();
        if (userId < 0) {
            UToast.showShort(this, "用户ID为空");
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("calendar_id", getCalendarId());
        contentValues.put(CalendarContract.Events.DTSTART, timeStart);
        contentValues.put(CalendarContract.Events.DTEND, timeStart + 1000 * 60 * 60 * 12);
        contentValues.put(CalendarContract.Events.HAS_ALARM, 1);
        contentValues.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Beijing");

        Uri uri = getContentResolver().insert(Uri.parse(CALENDER_EVENT_URL), contentValues);
        if (uri == null) {
            UToast.showShort(this, "日历添加失败");
            return;
        }

        ContentValues values = new ContentValues();
        long eventId = ContentUris.parseId(uri);
        values.put(CalendarContract.Reminders.EVENT_ID, eventId);
        values.put(CalendarContract.Reminders.MINUTES, previousDate * 24 * 60);// 提前previousDate天有提醒
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri result = getContentResolver().insert(Uri.parse(CALENDER_REMINDER_URL), values);
        if (result == null) { //添加事件提醒失败直接返回
            UToast.showShort(this, "提醒设定失败");
        }
    }

    private long getCalendarId() {
        try (Cursor cursor = getContentResolver().query(Uri.parse(CALENDER_URL), null, null, null,
            null)) {
            if (cursor == null) {
                return -1;
            }
            int count = cursor.getCount();
            if (count > 0) { //存在现有账户，取第一个账户的id返回
                cursor.moveToFirst();
                return cursor.getInt(cursor.getColumnIndex(CalendarContract.Calendars._ID));
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    private long addCalenderAccount() {
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();

        value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE);
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME);
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
            CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = Uri.parse(CALENDER_URL);
        calendarUri = calendarUri.buildUpon()
            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
            .build();

        Uri result = getContentResolver().insert(calendarUri, value);

        return result == null ? -1 : ContentUris.parseId(result);
    }

    @Override public void permissionGranted(int itemKey) {
        UToast.showShort(this, "权限申请成功");
        long id = addCalenderAccount();
        if (id < 0) {
            UToast.showShort(this, "账号添加失败");
        } else {
            UToast.showShort(this, "账号添加成功");
        }
    }

    @Override public void permissionDenied(int itemKey) {
        UToast.showShort(this, "权限申请失败");
    }
}
