package com.example.wyyu.gitsamlpe.test.multi.waterfall;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.image.matisse.AspectRatioFrameLayout;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class WaterfallItem extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.layout_waterfall_item;

    private AspectRatioFrameLayout ratioFrameLayout;
    private ImageView itemImage;
    private TextView itemTitle;
    private TextView itemText;

    WaterfallItem(@NonNull View itemView) {
        super(itemView);

        ratioFrameLayout = itemView.findViewById(R.id.waterfall_item_image_container);
        itemImage = itemView.findViewById(R.id.waterfall_item_image);
        itemTitle = itemView.findViewById(R.id.waterfall_item_title);
        itemText = itemView.findViewById(R.id.waterfall_item_text);
    }

    public void cacheValue(ItemBean itemBean) {

        ratioFrameLayout.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        ratioFrameLayout.setAspectRatio(
            itemBean.imageBean.width * 1.0f / itemBean.imageBean.height);

        itemImage.setImageResource(itemBean.imageBean.resId);
        itemTitle.setText(itemBean.title);

        itemText.setMaxLines(itemBean.index % 2 == 0 ? 2 : 4);
        itemText.setText(itemBean.content);
    }

    int[] loadImageLocation() {
        if (itemImage == null) {
            return null;
        }
        int[] location = new int[2];
        itemImage.getLocationOnScreen(location);
        return new int[] {
            location[0], location[1] - UIUtils.getStatusHeightByDimen(itemView.getContext()),
            itemImage.getWidth(), itemImage.getHeight()
        };
    }
}
