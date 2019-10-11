package com.example.wyyu.gitsamlpe.test.litho.define.component;

import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineData;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineImage;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import com.facebook.litho.StateContainer;
import com.facebook.litho.annotations.State;
import com.facebook.litho.widget.Image;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;

/**
 * Created by wyyu on 2019-10-10.
 **/

public class ContentTest extends Component {

    private DefineData defineData;
    private LithoView contentView;

    public ContentTest() {
        super("ContentTest");
        contentView = null;
    }

    @Override public ContentTest makeShallowCopy() {
        ContentTest contentCopy = (ContentTest) super.makeShallowCopy();
        contentCopy.defineData = defineData;
        return contentCopy;
    }

    @Override protected Component onCreateLayout(ComponentContext componentContext) {

        DefineImage defineImage = defineData == null ? null : defineData.imageBean;
        String content = defineData == null ? "content" : defineData.content;

        return Column.create(componentContext)
            .paddingDip(YogaEdge.HORIZONTAL, 14)
            .paddingDip(YogaEdge.TOP, 10)
            .child(Text.create(componentContext)
                .text(content)
                .textColor(0xff333333)
                .textSizeDip(16)
                .spacingMultiplier(1.3f)
                .maxLines(4)
                .ellipsize(TextUtils.TruncateAt.END)
                .build())
            .child(loadMultiImage(componentContext, defineImage))
            .build();
    }

    private Component loadMultiImage(ComponentContext componentContext, DefineImage defineImage) {
        if (defineImage == null) {
            return Column.create(componentContext).widthDip(0).heightDip(0).build();
        } else if (defineImage.height > defineImage.width) {
            return Image.create(componentContext)
                .paddingDip(YogaEdge.TOP, 12)
                .heightDip(232)
                .widthDip(WindowManager.LayoutParams.WRAP_CONTENT)
                .scaleType(ImageView.ScaleType.FIT_START)
                .drawableRes(defineImage.resId)
                .build();
        } else {
            return Image.create(componentContext)
                .paddingDip(YogaEdge.TOP, 12)
                .widthDip(232)
                .heightDip(WindowManager.LayoutParams.WRAP_CONTENT)
                .scaleType(ImageView.ScaleType.FIT_START)
                .drawableRes(defineImage.resId)
                .build();
        }
    }

    @Override public boolean hasState() {
        return true;
    }

    public LithoView getContentView(ComponentContext componentContext) {
        if (contentView == null) {
            contentView = LithoView.create(componentContext, onCreateLayout(componentContext));
        }
        return contentView;
    }

    public void setDefineData(DefineData defineData) {
        this.defineData = defineData;
        this.contentView.rerenderForAccessibility(true);
    }
}
