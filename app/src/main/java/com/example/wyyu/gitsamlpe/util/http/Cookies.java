package com.example.wyyu.gitsamlpe.util.http;

import java.util.HashMap;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class Cookies {

    private HashMap<String, String> dataMap;

    private Cookies() {
        dataMap = new HashMap<>();
    }

    private Cookies(HashMap<String, String> dataMap) {
        this.dataMap = dataMap;
    }

    public static Cookies buildCookies() {
        return new Cookies();
    }

    public static Cookies buildCookies(HashMap<String, String> dataMap) {
        return new Cookies(dataMap);
    }

    public Cookies addCookie(String key, String value) {
        dataMap.put(key, value);
        return this;
    }

    public Cookies deleteCookie(String key) {
        dataMap.remove(key);
        return this;
    }

    public Cookies addCookies(Cookies cookies) {
        if (cookies == null) return this;
        dataMap.putAll(cookies.dataMap);
        return this;
    }

    public Cookies deleteCookies(Cookies cookies) {
        for (String keyData : cookies.dataMap.keySet()) {
            dataMap.remove(keyData);
        }
        return this;
    }

    public boolean hasCookie(String keyData) {
        return dataMap.containsKey(keyData);
    }

    public String toCookieString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String keyData : dataMap.keySet()) {
            stringBuilder.append(keyData).append("=").append(dataMap.get(keyData)).append(";");
        }
        return stringBuilder.toString();
    }

}
