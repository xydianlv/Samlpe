package com.example.wyyu.gitsamlpe.test.number.value;

import android.annotation.SuppressLint;
import androidx.annotation.IntDef;
import com.example.wyyu.gitsamlpe.test.number.OperateUtil;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Random;

/**
 * Created by wyyu on 2018/8/7.
 * 公式创建器，创建一条算式
 **/

public class Creator {

    @Retention(RetentionPolicy.SOURCE) @IntDef public @interface EquationType {
        int 一级 = 1; // 只会包含加减乘除三个一位数运算
        int 二级 = 2; // 包含高级算术的三个一位数运算
        int 三级 = 3; // 包含高级算术且包含二位数的三个数运算
    }

    private static final int[] SYMBOL_ARRAY = new int[] {
        Symbol.加, Symbol.减, Symbol.乘, Symbol.除, Symbol.取余, Symbol.累加, Symbol.阶乘, Symbol.开方
    };

    private static final int SIMPLE_OPERATE = 5;
    private static final int NORMAL_OPERATE = 8;
    private static final int DOUBLE_NUM = 19;
    private static final int SINGLE_NUM = 9;

    private static class CreatorHolder {
        private static Creator creator = new Creator();
    }

    public static Creator getCreator() {
        return CreatorHolder.creator;
    }

    private Random random;

    private Creator() {
        random = new Random();
    }

    @SuppressLint("SwitchIntDef") public Value getOperateValue(@EquationType int equationType) {
        switch (equationType) {
            case EquationType.一级:
                return createAppointedValue(SIMPLE_OPERATE, SINGLE_NUM);
            case EquationType.二级:
                return createAppointedValue(NORMAL_OPERATE, SINGLE_NUM);
            case EquationType.三级:
                return createAppointedValue(NORMAL_OPERATE, DOUBLE_NUM);
            default:
                return createAppointedValue(SIMPLE_OPERATE, SINGLE_NUM);
        }
    }

    private Value createAppointedValue(int symbolSection, int numSection) {
        Value value = new Value();

        int[] indexArray = new int[symbolSection];
        for (int index = 0; index < symbolSection; index++) {
            indexArray[index] = index;
        }
        randomArray(indexArray, indexArray.length - 1);

        value.symbolA = SYMBOL_ARRAY[indexArray[0]];
        value.symbolB = SYMBOL_ARRAY[indexArray[1]];
        value.symbolC = SYMBOL_ARRAY[indexArray[2]];
        value.symbolD = SYMBOL_ARRAY[indexArray[3]];

        value.numA = random.nextInt(numSection) + 1;
        value.numB = random.nextInt(numSection) + 1;
        value.numC = random.nextInt(numSection) + 1;
        value.numD = random.nextInt(numSection) + 1;

        value.result = getResult(value);

        return value;
    }

    private int getResult(Value value) {
        int result = value.numA;
        if (value.symbolA > Symbol.取余) {
            result = OperateUtil.getNormalResult(value.symbolA, result);
            if (value.symbolB > Symbol.取余) {
                result = OperateUtil.getNormalResult(value.symbolB, result);
            } else {
                result = OperateUtil.getSimpleResult(value.symbolB, result, value.numB);
            }
        } else {
            result = OperateUtil.getSimpleResult(value.symbolA, result, value.numB);
            if (value.symbolB > Symbol.取余) {
                result = OperateUtil.getNormalResult(value.symbolB, result);
            } else {
                result = OperateUtil.getSimpleResult(value.symbolB, result, value.numC);
            }
        }
        return result;
    }

    /**
     * 将一个数组中从开始到 endIndex 位置处的内容随机排序
     *
     * @param numArray 待排序数组
     * @param endIndex 结束 Index
     */
    private void randomArray(int[] numArray, int endIndex) {

        for (int index = endIndex; index > 1; index--) {

            int position = random.nextInt(index);

            numArray[position] = numArray[position] + numArray[index];
            numArray[index] = numArray[position] - numArray[index];
            numArray[position] = numArray[position] - numArray[index];
        }
    }
}
