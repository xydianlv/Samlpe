package com.example.wyyu.gitsamlpe.util;

import java.util.Random;

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

    // 获取 [ preData , nextData } 间的随机数，包含下限不包含上限
    public static int getRandomValue(int preData, int nextData) {
        return new Random().nextInt(nextData - preData) + preData;
    }
}
