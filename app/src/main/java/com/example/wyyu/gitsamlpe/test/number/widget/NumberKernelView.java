package com.example.wyyu.gitsamlpe.test.number.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.number.OperateUtil;
import com.example.wyyu.gitsamlpe.test.number.value.Symbol;
import com.example.wyyu.gitsamlpe.test.number.value.Value;

/**
 * Created by wyyu on 2018/8/7.
 * 操作界面
 **/

public class NumberKernelView extends LinearLayout implements View.OnClickListener {

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

    private TextView section;
    private TextView result;
    private TextView delete;
    private TextView clean;

    private String sectionTxt;
    private Value value;

    private void initKernelView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_number_kernel_view, this);

        initBasicValue();
        initClickValue();
    }

    private void initBasicValue() {

        symbolA = findViewById(R.id.kernel_symbol_a);
        symbolB = findViewById(R.id.kernel_symbol_b);
        symbolC = findViewById(R.id.kernel_symbol_c);
        symbolD = findViewById(R.id.kernel_symbol_d);

        numberA = findViewById(R.id.kernel_number_a);
        numberB = findViewById(R.id.kernel_number_b);
        numberC = findViewById(R.id.kernel_number_c);
        numberD = findViewById(R.id.kernel_number_d);

        section = findViewById(R.id.kernel_section);
        result = findViewById(R.id.kernel_result);
        delete = findViewById(R.id.kernel_symbol_delete);
        clean = findViewById(R.id.kernel_symbol_clean);

        sectionTxt = "";
    }

    private void initClickValue() {
        symbolA.setOnClickListener(this);
        symbolB.setOnClickListener(this);
        symbolC.setOnClickListener(this);
        symbolD.setOnClickListener(this);

        numberA.setOnClickListener(this);
        numberB.setOnClickListener(this);
        numberC.setOnClickListener(this);
        numberD.setOnClickListener(this);

        delete.setOnClickListener(this);
        clean.setOnClickListener(this);
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

        this.value = value;
    }

    @Override public void onClick(View v) {
        if (value == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.kernel_symbol_a:
                onClickSymbol(value.symbolA);
                break;
            case R.id.kernel_symbol_b:
                onClickSymbol(value.symbolB);
                break;
            case R.id.kernel_symbol_c:
                onClickSymbol(value.symbolC);
                break;
            case R.id.kernel_symbol_d:
                onClickSymbol(value.symbolD);
                break;
            case R.id.kernel_number_a:
                onClickNumber(value.numA);
                break;
            case R.id.kernel_number_b:
                onClickNumber(value.numB);
                break;
            case R.id.kernel_number_c:
                onClickNumber(value.numC);
                break;
            case R.id.kernel_number_d:
                onClickNumber(value.numD);
                break;
            case R.id.kernel_symbol_delete:
                onClickDelete();
                break;
            case R.id.kernel_symbol_clean:
                onClickClean();
                break;
        }
    }

    private void onClickSymbol(@Symbol int symbol) {
        sectionTxt = sectionTxt + symbol;
        section.setText(sectionTxt);
    }

    private void onClickNumber(int number) {
        sectionTxt = sectionTxt + number;
        section.setText(sectionTxt);
    }

    private void onClickDelete() {
        if (TextUtils.isEmpty(sectionTxt)) {
            return;
        }
        if (sectionTxt.length() == 1) {
            onClickClean();
            return;
        }
        sectionTxt = sectionTxt.substring(0, sectionTxt.length() - 1);
        section.setText(sectionTxt);
    }

    private void onClickClean() {
        sectionTxt = "";
        section.setText(sectionTxt);
    }
}
