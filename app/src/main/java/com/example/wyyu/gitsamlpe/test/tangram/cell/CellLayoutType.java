package com.example.wyyu.gitsamlpe.test.tangram.cell;

import androidx.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyyu on 2019-08-21.
 **/

@Retention(RetentionPolicy.SOURCE) @StringDef({
    CellLayoutType.ONE_COLUMN, CellLayoutType.TWO_COLUMN, CellLayoutType.THREE_COLUMN,
    CellLayoutType.FOUR_COLUMN, CellLayoutType.BANNER, CellLayoutType.FLOAT,
    CellLayoutType.WATER_FALL, CellLayoutType.STICKY, CellLayoutType.ONE_PLUS,
    CellLayoutType.SCROLL_FIX
}) public @interface CellLayoutType {

    // 组件在列表中单列展示
    String ONE_COLUMN = "container-oneColumn";

    // 组件在列表中双列展示
    String TWO_COLUMN = "container-twoColumn";

    // 组件在列表中三列展示
    String THREE_COLUMN = "container-threeColumn";

    // 组件在列表中四列展示
    String FOUR_COLUMN = "container-fourColumn";

    // 组件在不居中组合为可滑动的 Banner 展示
    // 需设置 Style，否则组件高度会充满整个界面
    String BANNER = "container-banner";

    // 组件悬浮在列表上方展示
    // 可通过设置 Style 指定展示位置
    // 只能展示一个 CELL
    String FLOAT = "container-float";

    // 组件在列表中双列瀑布流展示
    String WATER_FALL = "container-waterfall";

    // 组件在列表滑出该 Item 所在 Position 时，悬浮在列表顶部展示
    // 只能展示一个 CELL
    String STICKY = "container-sticky";

    // 组件在布局中组合为 左侧显示一个，右侧并列多个进行展示
    String ONE_PLUS = "container-onePlusN";

    // 组件在列表上方悬浮展示，并可滑动到任意位置
    // 只能展示一个 CELL
    String SCROLL_FIX = "container-scrollFix";
}
