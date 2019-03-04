package com.example.wyyu.gitsamlpe.test.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by wyyu on 2019/3/1.
 **/

class HolderCreator {

    static BaseViewHolder createMultiHolder(ViewGroup parent, int type) {
        switch (type) {
            case 0:
                return createHolderZero(parent);
            case 1:
                return createHolderOne(parent);
            case 2:
                return createHolderTwo(parent);
            default:
                return createHolderZero(parent);
        }
    }

    private static ViewHolderZero createHolderZero(ViewGroup parent) {
        return new ViewHolderZero(LayoutInflater.from(parent.getContext())
            .inflate(BaseViewHolder.BASE_VIEW_LAYOUT, parent, false));
    }

    private static ViewHolderOne createHolderOne(ViewGroup parent) {
        return new ViewHolderOne(LayoutInflater.from(parent.getContext())
            .inflate(BaseViewHolder.BASE_VIEW_LAYOUT, parent, false));
    }

    private static ViewHolderTwo createHolderTwo(ViewGroup parent) {
        return new ViewHolderTwo(LayoutInflater.from(parent.getContext())
            .inflate(BaseViewHolder.BASE_VIEW_LAYOUT, parent, false));
    }
}
