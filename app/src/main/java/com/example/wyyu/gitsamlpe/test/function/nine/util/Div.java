package com.example.wyyu.gitsamlpe.test.function.nine.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by wyyu on 2019-08-07.
 **/

public final class Div implements Externalizable {

    /**
     * Starting div point
     */
    public int start;

    /**
     * Finish div point. For stretchable areas it will point on next pixel after last Color.BLACK pixel found for this div.
     */
    public int stop;

    public Div() {}

    /**
     * @param start Starting div point
     * @param stop Finish div point. For stretchable areas it will point on next pixel after last Color.BLACK pixel found for this div.
     */
    public Div(int start, int stop) {
        this.start = start;
        this.stop = stop;
    }

    @Override
    public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
        start = input.readByte();
        stop = input.readByte();
    }

    @Override
    public void writeExternal(ObjectOutput output) throws IOException {
        output.writeByte(start);
        output.writeByte(stop);
    }
}
