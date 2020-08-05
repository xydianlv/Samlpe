package com.example.wyyu.gitsamlpe.test.text.xfermode;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2020-06-08.
 **/

public class XModeItemAdapter extends RecyclerView.Adapter {

    private List<String> list;

    XModeItemAdapter() {
        this.list = new ArrayList<>();
    }

    void bindList(List<String> list) {
        if (list == null || list.isEmpty()) {
            this.list = null;
        } else {
            if (this.list == null) {
                this.list = new ArrayList<>();
            }
            this.list.clear();
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new XModeItemHolder(LayoutInflater.from(viewGroup.getContext())
            .inflate(XModeItemHolder.LAYOUT, viewGroup, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((XModeItemHolder) viewHolder).bindText(list.get(i));
    }

    @Override public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private static final class XModeItemHolder extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_x_mode_item;

        private TextView textView;

        XModeItemHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.x_mode_item_text);
        }

        void bindText(String text) {
            textView.setText(TextUtils.isEmpty(text) ? "" : text);
        }
    }
}
