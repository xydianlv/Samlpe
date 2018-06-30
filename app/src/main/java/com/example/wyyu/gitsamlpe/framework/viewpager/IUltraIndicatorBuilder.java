package com.example.wyyu.gitsamlpe.framework.viewpager;

import android.graphics.Bitmap;

/**
 * Created by wyyu on 2018/6/29.
 **/

public interface IUltraIndicatorBuilder {

    /**
     * Set focused color for indicator.
     * @param focusColor
     * @return
     */
    IUltraIndicatorBuilder setFocusColor(int focusColor);

    /**
     * Set normal color for indicator.
     * @param normalColor
     * @return
     */
    IUltraIndicatorBuilder setNormalColor(int normalColor);

    /**
     * Set stroke color for indicator.
     * @param strokeColor
     * @return
     */
    IUltraIndicatorBuilder setStrokeColor(int strokeColor);

    /**
     * Set stroke width for indicator.
     * @param strokeWidth
     * @return
     */
    IUltraIndicatorBuilder setStrokeWidth(int strokeWidth);

    /**
     * Set spacing between indicator item ，the default value is item's height.
     * @param indicatorPadding
     * @return
     */
    IUltraIndicatorBuilder setIndicatorPadding(int indicatorPadding);

    /**
     * Set the corner radius of the indicator item.
     * @param radius
     * @return
     */
    IUltraIndicatorBuilder setRadius(int radius);

    /**
     * Sets the orientation of the layout.
     * @param orientation
     * @return
     */
    IUltraIndicatorBuilder setOrientation(UltraViewPager.Orientation orientation);

    /**
     * Set the location at which the indicator should appear on the screen.
     *
     * @param gravity android.view.Gravity
     * @return
     */
    IUltraIndicatorBuilder setGravity(int gravity);

    /**
     * Set focused resource ID for indicator.
     * @param focusResId
     * @return
     */
    IUltraIndicatorBuilder setFocusResId(int focusResId);

    /**
     * Set normal resource ID for indicator.
     * @param normalResId
     * @return
     */
    IUltraIndicatorBuilder setNormalResId(int normalResId);

    /**
     * Set focused icon for indicator.
     * @param bitmap
     * @return
     */
    IUltraIndicatorBuilder setFocusIcon(Bitmap bitmap);

    /**
     * Set normal icon for indicator.
     * @param bitmap
     * @return
     */
    IUltraIndicatorBuilder setNormalIcon(Bitmap bitmap);

    /**
     * Set margins for indicator.
     * @param left   the left margin in pixels
     * @param top    the top margin in pixels
     * @param right  the right margin in pixels
     * @param bottom the bottom margin in pixels
     * @return
     */
    IUltraIndicatorBuilder setMargin(int left, int top, int right, int bottom);

    /**
     * Combine all of the options that have been set and return a new IUltraIndicatorBuilder object.
     */
    void build();
}
