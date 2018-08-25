package com.example.wyyu.gitsamlpe.util.permission;

import java.util.HashSet;

/**
 * Created by wyyu on 2018/8/25.
 **/

public class PermissionObservable implements IPermissionObservable {

    private static class ObservableHolder {
        private static PermissionObservable observable = new PermissionObservable();
    }

    public static PermissionObservable getObservable() {
        return ObservableHolder.observable;
    }

    private HashSet<IPermissionObserver> observerHashSet;

    private PermissionObservable() {
        observerHashSet = new HashSet<>();
    }

    @Override public void permissionGranted() {
        if (observerHashSet == null || observerHashSet.isEmpty()) {
            return;
        }
        for (IPermissionObserver observer : observerHashSet) {
            observer.permissionGranted();
        }
    }

    @Override public void permissionDenied() {
        if (observerHashSet == null || observerHashSet.isEmpty()) {
            return;
        }
        for (IPermissionObserver observer : observerHashSet) {
            observer.permissionDenied();
        }
    }

    @Override public void attach(IPermissionObserver observer) {
        if (observerHashSet == null) {
            observerHashSet = new HashSet<>();
        }
        observerHashSet.add(observer);
    }

    @Override public void detach(IPermissionObserver observer) {
        if (observerHashSet == null || observerHashSet.isEmpty()) {
            return;
        }
        observerHashSet.remove(observer);
    }

    @Override public void release() {
        if (observerHashSet == null) {
            return;
        }
        observerHashSet.clear();
        observerHashSet = null;
    }
}
