package com.example.wyyu.gitsamlpe.test.database;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.message.Message;
import com.example.wyyu.gitsamlpe.framework.message.MsgCallBack;
import com.example.wyyu.gitsamlpe.framework.message.MsgReceiver;
import com.example.wyyu.gitsamlpe.framework.message.MsgType;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import java.util.List;

/**
 * Created by wyyu on 2018/11/12.
 **/

public class ActivityDataBase extends ToolbarActivity implements MsgReceiver {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityDataBase.class));
    }

    @BindView(R.id.database_test_list) RecyclerView recyclerView;

    private DataListAdapter adapter;
    private DataListModel model;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);

        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

    private void initActivity() {
        initToolbar("DataBase");

        initRecyclerView();

        model = ViewModelProviders.of(this).get(DataListModel.class);
        loadDataList();

        addMessageListener(MsgType.DATA_CHANGE, new MsgCallBack() {
            @Override public void onMessageCallBack(Message message) {
                loadDataList();
            }
        });
    }

    private void initRecyclerView() {

        adapter = new DataListAdapter();
        adapter.setOnClickListener(new DataListAdapter.OnItemClickListener() {
            @Override public void onClickAddition(String text) {
                if (model != null) {
                    model.saveNewData(ActivityDataBase.this, text);
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);
    }

    private void loadDataList() {

        model.loadDataList(new DataListModel.LoadListener() {
            @Override public void loadSuccess(List<DataBean> dataBeanList) {
                if (adapter != null) {
                    adapter.setDataBeanList(dataBeanList);
                }
            }

            @Override public void loadFailure() {
                UToast.showShort(ActivityDataBase.this, "数据获取失败");
            }
        });
    }
}
