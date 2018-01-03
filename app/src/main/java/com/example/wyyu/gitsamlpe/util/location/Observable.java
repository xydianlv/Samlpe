package com.example.wyyu.gitsamlpe.util.location;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2017/12/29.
 **/

public class Observable {

    private List<Observer> observerList;

    private Observable() {
        observerList = new LinkedList<>();
    }

    private static class ObservableHolder {
        private static Observable observable = new Observable();
    }

    static Observable getObservable() {
        return ObservableHolder.observable;
    }

    void attach(Observer observer) {
        observerList.add(observer);
    }

    void detach(Observer observer) {
        observerList.remove(observer);
    }

    void notifyLocationData(LocationData locationData) {
        for (Observer observer : observerList) {
            observer.locationUpdate(locationData);
        }
    }

    public void release() {
        observerList.clear();
        observerList = null;
    }

}
