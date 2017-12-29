package com.example.wyyu.gitsamlpe.test;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wyyu.gitsamlpe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class ListViewMain extends LinearLayout {

    private ListAdapter listAdapter;

    public ListViewMain(Context context) {
        super(context);
        initListViewMain();
    }

    public ListViewMain(Context context, AttributeSet attrs) {
        super(context, attrs);
        initListViewMain();
    }

    public ListViewMain(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initListViewMain();
    }

    private void initListViewMain() {
        LayoutInflater.from(getContext()).inflate(R.layout.list_view_main, this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.main_list_view);

        listAdapter = new ListAdapter(getContext());

        recyclerView.setAdapter(listAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void addNewItem(String itemTitle, OnClickListener clickListener) {
        listAdapter.addNewListItem(itemTitle, clickListener);
    }

    public void refreshList() {
        listAdapter.notifyDataSetChanged();
    }


    private static class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<ItemData> itemDataList;
        private Context context;

        ListAdapter(Context context) {
            this.context = context;

            itemDataList = new ArrayList<>();
        }

        void addNewListItem(String itemTitle, OnClickListener clickListener) {
            itemDataList.add(new ItemData(itemTitle, clickListener));
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ListHolder(LayoutInflater.from(context).inflate(R.layout.list_view_main_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ListHolder) holder).setData(itemDataList.get(position), position == itemDataList.size() - 1);
        }

        @Override
        public int getItemCount() {
            return itemDataList.size();
        }

        private static class ListHolder extends RecyclerView.ViewHolder {

            private TextView itemTitle;
            private View itemDivide;

            ListHolder(View itemView) {
                super(itemView);

                itemDivide = itemView.findViewById(R.id.main_list_item_divide);
                itemTitle = itemView.findViewById(R.id.main_list_item_title);
            }

            void setData(ItemData itemData, boolean isLast) {

                itemView.setOnClickListener(itemData.clickListener);
                itemTitle.setText(itemData.itemTitle);

                itemDivide.setVisibility(isLast ? GONE : VISIBLE);
            }
        }

        private class ItemData {
            OnClickListener clickListener;
            String itemTitle;

            ItemData(String itemTitle, OnClickListener clickListener) {
                this.itemTitle = itemTitle;
                this.clickListener = clickListener;
            }
        }
    }

}
