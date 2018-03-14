package com.example.wyyu.gitsamlpe.test.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.test.recyclerview.weight.FastScrollBar;
import com.example.wyyu.gitsamlpe.util.CommonUtil;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class ActivityFastScrollTest extends FullScreenActivity {

    private RecyclerView recyclerView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_scroll_test);

        initFastScrollTest();
    }

    private void initFastScrollTest() {

        initRecyclerView();

        initFastScrollView();

        editText = findViewById(R.id.top_text_view);

    }

    private void initRecyclerView() {

        recyclerView = findViewById(R.id.fast_scroll_recycler);

        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 60);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new FastViewAdapter(this));

        ItemTouchHelper touchHelper=new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override public int getMovementFlags(RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                RecyclerView.ViewHolder target) {
                return false;
            }

            @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                //是否可拖拽
                return true;
            }
        });

        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void initFastScrollView() {

        FastScrollBar fastScrollBar = findViewById(R.id.fast_scroll_bar);

        fastScrollBar.setRecyclerView(recyclerView);
    }

    private static class FastViewAdapter extends RecyclerView.Adapter {

        private static final int ITEM_COUNT = 100;
        private Context context;

        FastViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FastViewHolder(LayoutInflater.from(context).inflate(R.layout.fast_recycler_view_item, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextView) holder.itemView.findViewById(R.id.fast_recycler_item_text)).setText("" + position + "");
        }

        @Override
        public int getItemCount() {
            return ITEM_COUNT;
        }

        private class FastViewHolder extends RecyclerView.ViewHolder {

            FastViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        ULog.show("ScrollActivity -> dispatchKeyEvent");

        CommonUtil.hideSoftInput(ActivityFastScrollTest.this, editText);

        return super.dispatchTouchEvent(ev);
    }

}
