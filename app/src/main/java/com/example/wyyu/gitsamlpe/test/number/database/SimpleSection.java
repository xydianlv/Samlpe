package com.example.wyyu.gitsamlpe.test.number.database;

import com.example.wyyu.gitsamlpe.test.number.value.Symbol;

/**
 * Created by wyyu on 2018/8/8.
 **/

public enum SimpleSection {

    AAAAA(3, 5, 1, 2, Symbol.加, Symbol.除, Symbol.乘, Symbol.减, 11),
    AAAAB(2, 3, 2, 6, Symbol.乘, Symbol.减, Symbol.除, Symbol.加, 8);

    int numberA;
    int numberB;
    int numberC;
    int numberD;

    @Symbol int symbolA;
    @Symbol int symbolB;
    @Symbol int symbolC;
    @Symbol int symbolD;

    int result;

    SimpleSection(int nA, int nB, int nC, int nD, int sA, int sB, int sC, int sD, int result) {
        this.numberA = nA;
        this.numberB = nB;
        this.numberC = nC;
        this.numberD = nD;

        this.symbolA = sA;
        this.symbolB = sB;
        this.symbolC = sC;
        this.symbolD = sD;

        this.result = result;
    }
}
