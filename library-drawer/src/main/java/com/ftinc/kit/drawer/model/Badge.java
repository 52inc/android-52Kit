package com.ftinc.kit.drawer.model;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.drawer.model
 * Created by drew.heavner on 7/30/15.
 */
public class Badge {

    /***********************************************************************************************
     *
     * Static Methods
     *
     */

    /**
     * Start building a badge object with the given instance of context
     *
     * @param ctx       the context in which to construct the badge with
     * @return          the badge builder
     */
    public static Builder with(Context ctx){
        return new Builder(ctx);
    }

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private int color;
    private int textColor;
    private String text;

    /**
     * Private Constructor
     */
    private Badge(){}

    /***********************************************************************************************
     *
     * Getters
     *
     */

    @ColorInt
    public int getColor() {
        return color;
    }

    @ColorInt
    public int getTextColor() {
        return textColor;
    }

    public String getText() {
        return text;
    }

    /***********************************************************************************************
     *
     * Builder
     *
     */

    public static class Builder{

        private Context context;
        private Badge badge;

        public Builder(Context ctx){
            context = ctx;
            badge = new Badge();
        }

        public Builder color(@ColorRes int resId){
            badge.color = context.getResources().getColor(resId);
            return this;
        }

        public Builder badgeColor(@ColorInt int color) {
            badge.color = color;
            return this;
        }

        public Builder textColor(@ColorRes int resId){
            badge.textColor = context.getResources().getColor(resId);
            return this;
        }

        public Builder badgeTextColor(@ColorInt int color) {
            badge.textColor = color;
            return this;
        }

        public Builder text(String text){
            badge.text = text;
            return this;
        }

        public Builder text(@StringRes int resId){
            badge.text = context.getString(resId);
            return this;
        }

        public Badge build(){
            return badge;
        }

    }

}
