package com.example.wyyu.gitsamlpe.framework.message;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by wyyu on 2017/12/29.
 **/

public class Message<T, V> implements Serializable {

    private HashMap<T, V> dataMap;

    public Message() {
        dataMap = new HashMap<>();
    }

    public void putData(T key, V value) {
        dataMap.put(key, value);
    }

    public V getData(T key) {
        return dataMap.get(key);
    }

}
