package com.example.wyyu.gitsamlpe.test.litho.multi;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.litho.Datum;
import com.example.wyyu.gitsamlpe.test.litho.multi.spec.MultiList;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import java.util.List;

/**
 * Created by wyyu on 2019-09-27.
 **/

public class ActivityLithoMulti extends ToolbarActivity {

    @BindView(R.id.multi_container) FrameLayout container;

    private ComponentContext componentContext;
    private LithoMultiModel multiModel;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litho_multi);

        initActivity();
    }

    private void initActivity() {
        initToolbar("LithoTest", 0xffffffff, 0xff84919b);

        initView();
        initModel();

        loadList();
    }

    private void initView() {
        componentContext = new ComponentContext(this);
    }

    private void initModel() {
        multiModel = ViewModelProviders.of(this).get(LithoMultiModel.class);
    }

    private void loadList() {
        multiModel.loadList(new LithoMultiModel.LoadCallback() {
            @Override public void onSuccess(List<Datum> datumList) {
                container.addView(LithoView.create(componentContext,
                    MultiList.create(componentContext).dataModels(datumList).build()));
            }

            @Override public void onFailure() {

            }
        });
    }
}
