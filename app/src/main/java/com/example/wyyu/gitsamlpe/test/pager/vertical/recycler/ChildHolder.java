package com.example.wyyu.gitsamlpe.test.pager.vertical.recycler;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2021/3/18.
 **/

public class ChildHolder extends RecyclerView.ViewHolder {

    public static int LAYOUT = R.layout.layout_child_holder;

    private TextView textView;

    public ChildHolder(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.child_text);
    }

    public void bindIndex(int index) {
        textView.setText(String.format("Index ： %s", index));
    }
}
