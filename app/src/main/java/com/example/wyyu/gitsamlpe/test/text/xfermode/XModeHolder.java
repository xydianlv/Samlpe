package com.example.wyyu.gitsamlpe.test.text.xfermode;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import java.util.Arrays;

/**
 * Created by wyyu on 2020-06-08.
 **/

public class XModeHolder extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.layout_x_mode_holder;

    private SimpleDraweeView viewBack;
    private XModeItemAdapter adapter;

    XModeHolder(@NonNull View itemView) {
        super(itemView);

        viewBack = itemView.findViewById(R.id.x_mode_holder_back);
        adapter = new XModeItemAdapter();

        RecyclerView recyclerView = itemView.findViewById(R.id.x_mode_holder_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new OverDecoration());
    }

    void bindData(XModeData data) {
        viewBack.setImageRequest(ImageRequestBuilder.newBuilderWithResourceId(data.resId)
            .setResizeOptions(new ResizeOptions(30, 30))
            .setPostprocessor(new IterativeBoxBlurPostProcessor(1, 30))
            .build());

        adapter.bindList(
            TextUtils.isEmpty(data.text) ? null : Arrays.asList(data.text.split("\n")));
    }
}
