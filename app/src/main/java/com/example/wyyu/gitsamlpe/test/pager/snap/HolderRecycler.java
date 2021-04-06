package com.example.wyyu.gitsamlpe.test.pager.snap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2021/3/18.
 **/

public class HolderRecycler extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.layout_holder_recycler;

    public HolderRecycler(@NonNull View itemView) {
        super(itemView);

        RecyclerView recyclerView = itemView.findViewById(R.id.pager_holder_recycler);

        recyclerView.setLayoutManager(
            new LinearLayoutManager(itemView.getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(new RecyclerAdapter());
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
    }

    private static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HolderRecyclerChild(LayoutInflater.from(parent.getContext())
                .inflate(HolderRecyclerChild.LAYOUT, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((HolderRecyclerChild) holder).bindItem(position);
        }

        @Override public int getItemCount() {
            return 6;
        }
    }

    private static class HolderRecyclerChild extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_holder_recycler_child;

        private TextView textView;

        public HolderRecyclerChild(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recycler_child_text);
        }

        public void bindItem(int index) {
            textView.setText(String.format("页面%s", index));
        }
    }
}
