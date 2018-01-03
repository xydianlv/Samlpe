package com.example.wyyu.gitsamlpe.util.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.wyyu.gitsamlpe.framework.ULog;

/**
 * Created by wyyu on 2017/12/29.
 **/

public class LocationProvider {

    public enum LocationType {
        PASSIVE,
        NETWORK,
        GPS
    }

    // 根据不同方式获取位置信息，仅在测试时使用
    public static void getLocationDataFromType(Context context, LocationType type) {

        if (LocationUtil.lackPermission(context)) return;

        switch (type) {
            case PASSIVE:
                notifyLocationData(getListenerLocation(LocationManager.PASSIVE_PROVIDER, context));
                break;
            case NETWORK:
                notifyLocationData(getListenerLocation(LocationManager.NETWORK_PROVIDER, context));
                break;
            case GPS:
                notifyLocationData(getListenerLocation(LocationManager.GPS_PROVIDER, context));
                break;
            default:
                notifyLocationData(null);
        }
    }

    // 根据当前手机设置选取最优的位置获取方式
    static void getLocationData(Context context) {

        if (gpsLocationIsOpen(context) && getLocation(LocationManager.GPS_PROVIDER, context) != null) {
            notifyLocationData(getListenerLocation(LocationManager.GPS_PROVIDER, context));
        } else if (netLocationIsOpen(context) && getLocation(LocationManager.NETWORK_PROVIDER, context) != null) {
            notifyLocationData(getListenerLocation(LocationManager.NETWORK_PROVIDER, context));
        } else {
            notifyLocationData(getListenerLocation(LocationManager.PASSIVE_PROVIDER, context));
        }
    }

    private static boolean gpsLocationIsOpen(Context context) {
        return getLocationManager(context).isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private static boolean netLocationIsOpen(Context context) {
        return getLocationManager(context).isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private static void notifyLocationData(Location location) {
        Observable.getObservable().notifyLocationData(getLocationDataFromLocation(location));
    }

    private static LocationData getLocationDataFromLocation(Location location) {

        LocationData locationData = new LocationData();

        if (location == null) return locationData;

        locationData.setLongitude(location.getLongitude());
        locationData.setAltitude(location.getAltitude());
        locationData.setLatitude(location.getLatitude());
        locationData.setTime(location.getTime());

        return locationData;
    }

    @SuppressLint("MissingPermission")
    private static LocationManager getLocationManager(String provider, final Context context) {

        LocationListener locationListener = new LocationListener() {

            // 当位置信息发生变化时触发
            @Override
            public void onLocationChanged(Location location) {
                notifyLocationData(location);
            }

            // 当 GPS 状态发生改变时触发
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                ULog.show("LocationProvider -> Listener ：GPS status -> " + s);
            }

            // 当 GPS 开启时触发
            @Override
            public void onProviderEnabled(String s) {
                ULog.show("LocationProvider -> Listener ：GPS is open");
            }

            // 当 GPS 关闭时触发
            @Override
            public void onProviderDisabled(String s) {
                ULog.show("LocationProvider -> Listener ：GPS is closed");
            }
        };

        LocationManager locationManager = getLocationManager(context);

        // minTime 表示位置刷新的最短时间间隔，单位为 "毫秒"
        // minDistance 表示位置刷新的最短距离间隔，单位为 "米"
        locationManager.requestLocationUpdates(provider, 600000, 10000, locationListener);

        return locationManager;
    }

    private static LocationManager getLocationManager(Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    private static Location getListenerLocation(String provider, Context context) {
        return getLocationManager(provider, context).getLastKnownLocation(provider);
    }

    @SuppressLint("MissingPermission")
    private static Location getLocation(String provider, Context context) {
        return getLocationManager(context).getLastKnownLocation(provider);
    }
}
