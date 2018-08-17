package com.example.wyyu.gitsamlpe.test.number.value;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2018/8/10.
 **/

public class Judge implements IJudge {

    private static class JudgeHolder {
        private static IJudge judge = new Judge();
    }

    public static IJudge getJudge() {
        return JudgeHolder.judge;
    }

    private List<Integer> keyList;
    private int result;

    private Judge() {
        keyList = new LinkedList<>();
    }

    @Override public boolean judgeRight(int key) {
        return false;
    }

    @Override public void setResult(int result) {
        this.result = result;
    }
}
