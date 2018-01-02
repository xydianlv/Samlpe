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
                notifyLocationData(getLocationFromPassive(context));
                break;
            case NETWORK:
                notifyLocationData(getLocationFromNet(context));
                break;
            case GPS:
                notifyLocationData(getLocationFromGPS(context));
                break;
            default:
                notifyLocationData(null);
        }
    }

    // 根据当前手机设置选取最优的位置获取方式
    static void getLocationData(Context context) {

        if (gpsLocationIsOpen(context) && getLocationFromGPS(context) != null)
            notifyLocationData(getLocationFromGPS(context));

        if (netLocationIsOpen(context) && getLocationFromNet(context) != null)
            notifyLocationData(getLocationFromNet(context));

        notifyLocationData(getLocationFromPassive(context));
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
                Observable.getObservable().notifyLocationData(getLocationDataFromLocation(location));
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

        locationManager.requestLocationUpdates(provider, 0, 0, locationListener);

        return locationManager;
    }

    private static LocationManager getLocationManager(Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    private static Location getLocationFromGPS(Context context) {
        return getLocationManager(LocationManager.GPS_PROVIDER, context).getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private static Location getLocationFromNet(Context context) {
        return getLocationManager(LocationManager.NETWORK_PROVIDER, context).getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private static Location getLocationFromPassive(Context context) {
        return getLocationManager(LocationManager.PASSIVE_PROVIDER, context).getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
    }

}
