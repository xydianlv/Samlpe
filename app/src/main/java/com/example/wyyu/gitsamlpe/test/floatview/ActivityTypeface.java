package com.example.wyyu.gitsamlpe.test.floatview;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;

/**
 * Created by wyyu on 2018/7/25.
 **/

public class ActivityTypeface extends BaseActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityTypeface.class));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeface_test);
        initTypeface();
    }

    private void initTypeface() {
        //((TextView) findViewById(R.id.typeface_1)).setTypeface(
        //    getTypeface("NotoSansHans-Black.otf"));
        //((TextView) findViewById(R.id.typeface_2)).setTypeface(
        //    getTypeface("NotoSansHans-Bold.otf"));
        //((TextView) findViewById(R.id.typeface_3)).setTypeface(
        //    getTypeface("NotoSansHans-DemiLight.otf"));
        //((TextView) findViewById(R.id.typeface_4)).setTypeface(
        //    getTypeface("NotoSansHans-Light.otf"));
        //((TextView) findViewById(R.id.typeface_5)).setTypeface(
        //    getTypeface("NotoSansHans-Medium.otf"));
        //((TextView) findViewById(R.id.typeface_6)).setTypeface(
        //    getTypeface("NotoSansHans-Regular.otf"));
        //((TextView) findViewById(R.id.typeface_7)).setTypeface(
        //    getTypeface("NotoSansHans-Thin-Windows.otf"));
    }

    private Typeface getTypeface(String typeName) {
        AssetManager assetManager = getAssets();
        return Typeface.createFromAsset(assetManager, "fonts/" + typeName);
    }
}
