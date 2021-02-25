package com.example.wyyu.gitsamlpe.test.keyboard.show;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2021/1/8.
 **/

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.layout_keyboard_list_holder, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).textView.setText(String.valueOf(position));
    }

    @Override public int getItemCount() {
        return 12;
    }

    private static final class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.keyboard_list_holder_index);
        }
    }
}
