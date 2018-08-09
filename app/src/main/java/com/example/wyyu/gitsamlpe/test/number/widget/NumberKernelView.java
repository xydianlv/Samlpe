package com.example.wyyu.gitsamlpe.test.number.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.number.OperateUtil;
import com.example.wyyu.gitsamlpe.test.number.value.Value;

/**
 * Created by wyyu on 2018/8/7.
 * 操作界面
 **/

public class NumberKernelView extends LinearLayout {

    public NumberKernelView(Context context) {
        this(context, null);
    }

    public NumberKernelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberKernelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initKernelView();
    }

    private TextView symbolA;
    private TextView symbolB;
    private TextView symbolC;
    private TextView symbolD;

    private TextView numberA;
    private TextView numberB;
    private TextView numberC;
    private TextView numberD;

    private TextView result;

    private void initKernelView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_number_kernel_view, this);

        symbolA = findViewById(R.id.kernel_symbol_a);
        symbolB = findViewById(R.id.kernel_symbol_b);
        symbolC = findViewById(R.id.kernel_symbol_c);
        symbolD = findViewById(R.id.kernel_symbol_d);

        numberA = findViewById(R.id.kernel_number_a);
        numberB = findViewById(R.id.kernel_number_b);
        numberC = findViewById(R.id.kernel_number_c);
        numberD = findViewById(R.id.kernel_number_d);

        result = findViewById(R.id.kernel_result);
    }

    /**
     * 传入操作框的相关数据
     *
     * @param value 相关数据
     */
    public void setKernelValue(Value value) {

        symbolA.setText(OperateUtil.getOperateSign(value.symbolA));
        symbolB.setText(OperateUtil.getOperateSign(value.symbolB));
        symbolC.setText(OperateUtil.getOperateSign(value.symbolC));
        symbolD.setText(OperateUtil.getOperateSign(value.symbolD));

        numberA.setText(String.valueOf(value.numA));
        numberB.setText(String.valueOf(value.numB));
        numberC.setText(String.valueOf(value.numC));
        numberD.setText(String.valueOf(value.numD));

        result.setText(String.valueOf(value.result));
    }
}
