package com.example.wyyu.gitsamlpe.test.number;

import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.test.number.value.Creator;
import com.example.wyyu.gitsamlpe.test.number.widget.NumberKernelView;

/**
 * Created by wyyu on 2018/8/3.
 **/

public class ActivityNumber extends FullScreenActivity {

    @BindView(R.id.game_num_kernel_view) NumberKernelView kernelView;
    @BindView(R.id.game_num_other) View other;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        initActivity();
    }

    private void initActivity() {

        other.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                setData();
            }
        });
        setData();
    }

    private void setData() {
        kernelView.setKernelValue(Creator.getCreator().getOperateValue(Creator.EquationType.一级));
    }
}