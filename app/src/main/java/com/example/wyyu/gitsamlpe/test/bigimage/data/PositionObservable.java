package com.example.wyyu.gitsamlpe.test.bigimage.data;

/**
 * Created by wyyu on 2018/5/8.
 **/

public class PositionObservable {

    private static class PositionObservableHolder {
        private static PositionObservable positionObservable = new PositionObservable();
    }

    private OnPositionChangeListener positionChangeListener;

    private PositionObservable() {

    }

    public static PositionObservable getInstance() {
        return PositionObservableHolder.positionObservable;
    }

    public void attach(OnPositionChangeListener positionChangeListener) {
        this.positionChangeListener = positionChangeListener;
    }

    public void detach(OnPositionChangeListener positionChangeListener) {
        this.positionChangeListener = null;
    }

    public void positionUpdate(int position) {
        if (positionChangeListener != null) {
            positionChangeListener.onChange(position);
        }
    }
}
