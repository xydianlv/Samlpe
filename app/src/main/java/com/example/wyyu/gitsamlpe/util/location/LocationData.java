package com.example.wyyu.gitsamlpe.util.location;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class LocationData {

    private double longitude;  // 经度
    private double altitude;  // 海拔
    private double latitude;  // 纬度
    private long time;  // 时间

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
