package com.example.wyyu.gitsamlpe.test.bigimage.data;

/**
 * Created by wyyu on 2018/5/9.
 **/

public class LocationObservable {

    private static class LocationObservableHolder {
        private static LocationObservable locationObservable = new LocationObservable();
    }

    private OnConfirmPositionCallBack confirmPositionCallBack;

    private LocationObservable() {

    }

    public static LocationObservable getInstance() {
        return LocationObservableHolder.locationObservable;
    }

    public void attach(OnConfirmPositionCallBack confirmPositionCallBack) {
        this.confirmPositionCallBack = confirmPositionCallBack;
    }

    public void detach(OnConfirmPositionCallBack confirmPositionCallBack) {
        this.confirmPositionCallBack = null;
    }

    public void positionUpdate(LocationData locationData) {
        if (confirmPositionCallBack != null) {
            confirmPositionCallBack.callBack(locationData);
        }
    }
}
