package com.example.wyyu.gitsamlpe.test.skin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wyyu on 2019/1/25.
 **/

public class ActivitySkinTest extends AppCompatActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySkinTest.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
