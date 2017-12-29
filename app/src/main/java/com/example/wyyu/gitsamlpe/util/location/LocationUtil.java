package com.example.wyyu.gitsamlpe.util.location;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class LocationUtil {

    public static LocationData getLocationData() {

        return new LocationData();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static LocationData getLocationData(Activity activity) {

        activity.requestPermissions(new String[]{}, 47);

        return new LocationData();

    }
}
