package com.example.wyyu.gitsamlpe.test.litho.define.component;

import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineData;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.Row;
import com.facebook.litho.StateContainer;
import com.facebook.litho.widget.Image;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;

/**
 * Created by wyyu on 2019-10-10.
 **/

public class HeaderTest extends Component {

    private DefineData defineData;
    private HeaderTest preHeader;

    public HeaderTest() {
        super("HeaderTest");
        defineData = null;
        preHeader = null;
    }

    @Override public HeaderTest makeShallowCopy() {
        HeaderTest headerCopy = (HeaderTest) super.makeShallowCopy();
        //headerCopy.defineData = defineData;
        //headerCopy.preHeader = this;

        if (preHeader == null) {
            headerCopy.preHeader = this;
            headerCopy.defineData = defineData;
        } else {
            HeaderTest fun = this;
            while (fun.preHeader != null) {
                fun = fun.preHeader;
            }
            headerCopy.preHeader = fun;
            headerCopy.defineData = fun.defineData;
        }

        return headerCopy;
    }

    @Override protected Component onCreateLayout(ComponentContext componentContext) {

        String info = defineData == null ? "info" : String.valueOf(defineData.index);
        int iconId = defineData == null ? R.mipmap.multi_image_9 : defineData.iconId;
        String title = defineData == null ? "Title" : defineData.title;

        if (preHeader != null) {
            preHeader.setScopedContext(componentContext);
        }

        return Row.create(componentContext)
            .paddingDip(YogaEdge.HORIZONTAL, 14)
            .paddingDip(YogaEdge.TOP, 12)
            .child(Image.create(componentContext)
                .drawableRes(iconId)
                .widthDip(32)
                .heightDip(32)
                .scaleType(ImageView.ScaleType.CENTER_CROP))
            .child(Column.create(componentContext)
                .marginDip(YogaEdge.LEFT, 8)
                .child(Text.create(componentContext)
                    .text(title)
                    .textSizeDip(14)
                    .textColor(0xff333333)
                    .build())
                .child(Text.create(componentContext)
                    .text(info)
                    .textSizeDip(11)
                    .textColor(0xff666666)
                    .build())
                .build())
            .child(Image.create(componentContext)
                .drawableRes(R.mipmap.icon_test)
                .positionType(YogaPositionType.ABSOLUTE)
                .positionDip(YogaEdge.RIGHT, 14)
                .positionDip(YogaEdge.TOP, 16)
                .widthDip(14)
                .heightDip(14))
            .build();
    }

    @Override public boolean hasState() {
        return true;
    }

    public void setDefineData(DefineData defineData) {
        this.defineData = defineData;

        ComponentContext treeContext = getScopedContext();
        if (treeContext != null) {
            treeContext.updateStateSync(new StateContainer.StateUpdate(0), "");
        }
    }
}
