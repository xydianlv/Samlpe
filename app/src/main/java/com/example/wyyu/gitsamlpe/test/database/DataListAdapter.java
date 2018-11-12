package com.example.wyyu.gitsamlpe.test.database;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2018/11/12.
 **/

public class DataListAdapter extends RecyclerView.Adapter {

    private static final int ITEM_NORMAL = 0;
    private static final int ITEM_HEAD = 1;

    private List<DataBean> dataBeanList;

    DataListAdapter() {
        dataBeanList = new LinkedList<>();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_HEAD:
                return new HeadViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_data_test_head, parent, false));
            default:
                return new DataViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_data_test_item, parent, false));
        }
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_HEAD:
                ((HeadViewHolder) holder).cacheView();
                break;
            default:
                ((DataViewHolder) holder).cacheValue(dataBeanList.get(position - 1));
                break;
        }
    }

    @Override public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_HEAD;
        }
        return ITEM_NORMAL;
    }

    @Override public int getItemCount() {
        return dataBeanList == null || dataBeanList.isEmpty() ? 1 : dataBeanList.size() + 1;
    }

    private class HeadViewHolder extends RecyclerView.ViewHolder {

        private EditText editText;
        private View button;

        HeadViewHolder(View itemView) {
            super(itemView);

            editText = itemView.findViewById(R.id.data_test_head_edit);
            button = itemView.findViewById(R.id.data_test_head_button);
        }

        void cacheView() {
            button.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if (editText == null || itemClickListener == null) {
                        return;
                    }
                    String text = editText.getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        UToast.showShort(itemView.getContext(), "文字部分不能为空");
                    } else {
                        itemClickListener.onClickAddition(text);
                    }
                }
            });
        }
    }

    private class DataViewHolder extends RecyclerView.ViewHolder {

        private TextView text;

        DataViewHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.data_test_item_text);
        }

        void cacheValue(final DataBean dataBean) {
            if (dataBean == null || TextUtils.isEmpty(dataBean.name)) {
                return;
            }
            text.setText(dataBean.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    UToast.showShort(itemView.getContext(), dataBean.name);
                }
            });
        }
    }

    void setDataBeanList(List<DataBean> dataBeanList) {
        if (dataBeanList == null || dataBeanList.isEmpty()) {
            return;
        }
        if (this.dataBeanList == null) {
            this.dataBeanList = new LinkedList<>();
        }
        this.dataBeanList.clear();
        this.dataBeanList.addAll(dataBeanList);
        notifyDataSetChanged();
    }

    private OnItemClickListener itemClickListener;

    void setOnClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {

        void onClickAddition(String text);
    }
}
