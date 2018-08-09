package com.example.wyyu.gitsamlpe.test.number;

import android.annotation.SuppressLint;
import com.example.wyyu.gitsamlpe.test.number.value.Symbol;

/**
 * Created by wyyu on 2018/8/8.
 **/

public class OperateUtil {

    // 根据 Symbol 获取操作图标显示样式
    @SuppressLint("SwitchIntDef") public static String getOperateSign(@Symbol int symbol) {
        switch (symbol) {
            case Symbol.加:
                return "+";
            case Symbol.减:
                return "-";
            case Symbol.乘:
                return "*";
            case Symbol.除:
                return "/";
            case Symbol.取余:
                return "%";
            case Symbol.累加:
                return "~";
            case Symbol.阶乘:
                return "!";
            case Symbol.开方:
                return "^";
            default:
                return null;
        }
    }

    // 获取双数字操作符结果
    @SuppressLint("SwitchIntDef") public static int getSimpleResult(@Symbol int symbol, int numA,
        int numB) {
        switch (symbol) {
            case Symbol.加:
                return numA + numB;
            case Symbol.减:
                return numA - numB;
            case Symbol.乘:
                return numA * numB;
            case Symbol.除:
                return numA / numB;
            case Symbol.取余:
                return numA % numB;
            default:
                return numA;
        }
    }

    // 获取单数字操作符结果
    @SuppressLint("SwitchIntDef") public static int getNormalResult(@Symbol int symbol,
        int number) {
        switch (symbol) {
            case Symbol.累加:
                return getAddition(number);
            case Symbol.阶乘:
                return getMultiply(number);
            case Symbol.开方:
                return (int) Math.sqrt(number);
            default:
                return number;
        }
    }

    // 累加
    private static int getAddition(int number) {
        int result = 0;
        for (int index = 0; index <= number; index++) {
            result = result + index;
        }
        return result;
    }

    // 阶乘
    private static int getMultiply(int number) {
        int result = 1;
        for (int index = 1; index <= number; index++) {
            result = result * index;
        }
        return result;
    }
}
