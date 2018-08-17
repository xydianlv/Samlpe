package com.example.wyyu.gitsamlpe.test.number.value;

/**
 * Created by wyyu on 2018/8/13.
 **/

public interface IJudge {

    // 判断当前算式是否可成功计算出结果
    boolean judgeRight(int key);

    // 设置当前题目的结果值
    void setResult(int result);
}
