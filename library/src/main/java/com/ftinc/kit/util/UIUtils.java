/*
 * Copyright (c) 2015 52inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ftinc.kit.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;

import com.ftinc.kit.R;

/**
 * Created by r0adkll on 11/13/14.
 */
public class UIUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startActivityWithTransition(Activity activity,
                                                   Intent intent,
                                                   final View clickedView,
                                                   final String transitionName) {
        ActivityOptions options = null;
        if (BuildUtils.isLollipop() && clickedView != null && !TextUtils.isEmpty(transitionName)) {
            options = ActivityOptions
                    .makeSceneTransitionAnimation(activity, clickedView, transitionName);
        }

        if(BuildUtils.isJellyBean()) {
            activity.startActivity(intent, (options != null) ? options.toBundle() : null);
        }else{
            activity.startActivity(intent);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startActivityWithTransition(Activity activity,
                                                   Intent intent,
                                                   Pair<View, String>... transitions){
        ActivityOptions options = null;
        if (BuildUtils.isLollipop() && transitions != null) {
            options = ActivityOptions
                    .makeSceneTransitionAnimation(activity, transitions);
        }

        if(BuildUtils.isJellyBean()) {
            activity.startActivity(intent, (options != null) ? options.toBundle() : null);
        }else{
            activity.startActivity(intent);
        }
    }

    public static void setAccessibilityIgnore(View view) {
        view.setClickable(false);
        view.setFocusable(false);
        view.setContentDescription("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        }
    }

    @SuppressLint("NewApi")
    public static void setBackground(View view, Drawable drawable){
        if(BuildUtils.isJellyBean()){
            view.setBackground(drawable);
        }else{
            view.setBackgroundDrawable(drawable);
        }
    }

    public static int getActionBarSize(Context ctx) {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = ctx.obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    /**
     * Get the selectableItemBackground attribute drawable
     * @return
     */
    public static Drawable getSelectableItemBackground(Context ctx){
        int[] attrs = new int[] { R.attr.selectableItemBackground /* index 0 */};
        TypedArray ta = ctx.obtainStyledAttributes(attrs);
        Drawable drawableFromTheme = ta.getDrawable(0 /* index */);
        ta.recycle();
        return drawableFromTheme;
    }

    /**
     * Get the selectableItemBackground attribute drawable
     * @return
     */
    public static Drawable getSelectableItemBackgroundBorderless(Context ctx){
        int[] attrs = new int[] { R.attr.selectableItemBackgroundBorderless /* index 0 */};
        TypedArray ta = ctx.obtainStyledAttributes(attrs);
        Drawable drawableFromTheme = ta.getDrawable(0 /* index */);
        ta.recycle();
        return drawableFromTheme;
    }

    /**
     * Get the color value for give attribute
     * @param ctx
     * @param colorAttrId
     * @return
     */
    public static int getColorAttr(Context ctx, @AttrRes int colorAttrId){
        int[] attrs = new int[] { colorAttrId /* index 0 */};
        TypedArray ta = ctx.obtainStyledAttributes(attrs);
        int colorFromTheme = ta.getColor(0, 0);
        ta.recycle();
        return colorFromTheme;
    }

    /**
     * Get a Drawable attribute value
     *
     * @param ctx
     * @param drawableAttrId
     * @return
     */
    public static Drawable getDrawableAttr(Context ctx, @AttrRes int drawableAttrId){
        int[] attrs = new int[] { drawableAttrId /* index 0 */};
        TypedArray ta = ctx.obtainStyledAttributes(attrs);
        Drawable drawable = ta.getDrawable(0);
        ta.recycle();
        return drawable;
    }

    public static String makeFragmentName(View view, int index) {
        return "android:switcher:" + view.getId() + ":" + index;
    }

    /**
     * Get the status bar height
     */
    public static int getStatusBarHeight(Context ctx) {
        int result = 0;
        int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = ctx.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * Get the usuable status bar height with a translucent status bar
     *
     * @param ctx
     * @return
     */
    public static int getUsableStatusBarHeight(Context ctx){
        if(BuildUtils.isKitKat()){
            return getStatusBarHeight(ctx);
        }
        return 0;
    }

}
