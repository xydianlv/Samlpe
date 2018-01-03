package com.example.wyyu.gitsamlpe.util.location.gaode;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.wyyu.gitsamlpe.framework.ULog;

/**
 * Created by wyyu on 2018/1/3.
 * 高德地图定位集成
 **/

public class LocationUtilG {

    public enum LocationType {
        HIGH_ACCURACY,
        BATTERY_SAVING,
        DEVICE_SENSORS
    }

    private AMapLocationClientOption locationClientOption;
    private AMapLocationClient locationClient;

    public LocationUtilG(Context context) {

        locationClientOption = new AMapLocationClientOption();
        locationClient = new AMapLocationClient(context);
    }

    private AMapLocationListener getLocationListener() {

        return new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                ULog.show("LocationG -> ErrorCode ：" + aMapLocation.getErrorCode());
            }
        };
    }

    public void startLocation(LocationType locationType) {

        locationClientOption.setLocationMode(getModeFromType(locationType));

        locationClient.setLocationListener(getLocationListener());

        locationClient.setLocationOption(locationClientOption);

        locationClient.startLocation();

    }

    private AMapLocationClientOption.AMapLocationMode getModeFromType(LocationType locationType) {

        switch (locationType) {
            case HIGH_ACCURACY:
                return AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;
            case BATTERY_SAVING:
                return AMapLocationClientOption.AMapLocationMode.Battery_Saving;
            case DEVICE_SENSORS:
                return AMapLocationClientOption.AMapLocationMode.Device_Sensors;
            default:
                return AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;
        }
    }

    public void stop() {
        locationClient.stopLocation();
    }

    public void destory() {
        locationClient.onDestroy();
    }
}
