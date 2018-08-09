package com.example.wyyu.gitsamlpe.test.number.database;

import com.example.wyyu.gitsamlpe.test.number.value.Symbol;

/**
 * Created by wyyu on 2018/8/8.
 **/

public enum SimpleSection {

    AAAAA(3, 5, 1, 2, Symbol.加, Symbol.除, Symbol.乘, Symbol.减, 11),
    AAAAB(2, 3, 2, 7, Symbol.乘, Symbol.减, Symbol.除, Symbol.加, 8),
    AAAAC(5, 2, 4, 7, Symbol.乘, Symbol.减, Symbol.除, Symbol.加, 8),
    AAAAD(2, 2, 4, 7, Symbol.乘, Symbol.减, Symbol.除, Symbol.加, 12),
    AAAAE(9, 2, 5, 4, Symbol.乘, Symbol.减, Symbol.除, Symbol.加, 12),
    AAAAF(1, 2, 5, 3, Symbol.乘, Symbol.减, Symbol.除, Symbol.加, 12),
    AAAAG(3, 6, 3, 7, Symbol.乘, Symbol.减, Symbol.除, Symbol.加, 12),
    AAAAH(1, 3, 1, 7, Symbol.乘, Symbol.减, Symbol.除, Symbol.加, 12),
    AAAAI(1, 3, 2, 7, Symbol.乘, Symbol.减, Symbol.除, Symbol.加, 12),
    AAAAJ(3, 6, 3, 7, Symbol.乘, Symbol.减, Symbol.除, Symbol.加, 12),
    AAAAZ(1, 8, 3, 7, Symbol.乘, Symbol.减, Symbol.除, Symbol.加, 12);

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
