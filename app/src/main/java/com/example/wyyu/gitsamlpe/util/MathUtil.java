package com.example.wyyu.gitsamlpe.util;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class MathUtil {

    public static int getMinInteger(int numberA, int numberB) {
        return numberA > numberB ? numberB : numberA;
    }

    public static int getMaxInteger(int numberA, int numberB) {
        return numberA < numberB ? numberB : numberA;
    }
}
