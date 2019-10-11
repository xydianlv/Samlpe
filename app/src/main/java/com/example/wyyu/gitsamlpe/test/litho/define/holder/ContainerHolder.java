package com.example.wyyu.gitsamlpe.test.litho.define.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.custom.widget.LithoContainer;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineData;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import com.facebook.litho.Row;
import com.facebook.litho.widget.Image;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;

/**
 * Created by wyyu on 2019-10-11.
 **/

public class ContainerHolder extends RecyclerView.ViewHolder {

    private DefineData defineData;

    public ContainerHolder(@NonNull View itemView) {
        super(itemView);

        initContainer(new ComponentContext(itemView.getContext()), (LithoContainer) itemView);
    }

    private void initContainer(ComponentContext context, LithoContainer container) {
        container.removeAllViews();

        container.addView(LithoView.create(context, loadComponent(context)));
    }

    private Component loadComponent(ComponentContext context) {
        return Column.create(context)
            .widthDip(WindowManager.LayoutParams.MATCH_PARENT)
            .child(Text.create(context)
                .marginDip(YogaEdge.HORIZONTAL, 14.0f)
                .marginDip(YogaEdge.TOP, 12.0f)
                .text(defineData == null ? "空内容" : defineData.title)
                .textColor(0xff333333)
                .textSizeDip(14.0f)
                .build())
            .child(Image.create(context)
                .marginDip(YogaEdge.HORIZONTAL, 14.0f)
                .marginDip(YogaEdge.TOP, 12.0f)
                .widthDip(100.0f)
                .heightDip(100.0f)
                .drawableRes(defineData == null ? R.mipmap.image_test_1 : defineData.iconId)
                .scaleType(ImageView.ScaleType.CENTER_CROP)
                .build())
            .child(Row.create(context)
                .widthDip(WindowManager.LayoutParams.MATCH_PARENT)
                .marginDip(YogaEdge.TOP, 12.0f)
                .heightDip(6.0f)
                .backgroundColor(0xffededf2)
                .build())
            .build();
    }

    public void cacheValue(DefineData defineData) {
        this.defineData = defineData;
    }
}
