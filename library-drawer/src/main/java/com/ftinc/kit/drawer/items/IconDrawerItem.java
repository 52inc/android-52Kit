/*
 * Copyright © 52inc 2015.
 * All rights reserved.
 */

package com.ftinc.kit.drawer.items;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftinc.kit.drawer.R;
import com.ftinc.kit.drawer.model.Badge;
import com.ftinc.kit.font.Face;
import com.ftinc.kit.font.FontLoader;
import com.ftinc.kit.util.UIUtils;

import butterknife.ButterKnife;

/**
 * Created by r0adkll on 11/12/14.
 */
public class IconDrawerItem extends DrawerItem {

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private int textResId;
    private int iconResId;
    private Badge badge;

    /**
     * Constructor
     */
    private IconDrawerItem(int id){
        super(id);
    }

    /**
     * Called to create this view
     *
     * @param inflater      the layout inflater to inflate a layout from system
     * @param container     the container the layout is going to be placed in
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, int highlightColor) {
        Context ctx = inflater.getContext();
        View view = inflater.inflate(R.layout.navdrawer_item, container, false);

        ImageView iconView = ButterKnife.findById(view, R.id.icon);
        TextView titleView = ButterKnife.findById(view, R.id.title);
        TextView badgeView = ButterKnife.findById(view, R.id.badge);

        // Set the textResId
        FontLoader.apply(titleView, Face.ROBOTO_MEDIUM);
        titleView.setText(textResId);

        // Set the iconResId, if provided
        iconView.setVisibility(iconResId > 0 ? View.VISIBLE : View.GONE);
        if(iconResId > 0)
            iconView.setImageResource(iconResId);

        // configure its appearance according to whether or not it's selected
        titleView.setTextColor(selected ?
                highlightColor :
                UIUtils.getColorAttr(ctx, android.R.attr.textColorPrimary));

        iconView.setColorFilter(selected ?
                highlightColor :
                ctx.getResources().getColor(R.color.navdrawer_icon_tint));

        // Setup the badge view
        if(badge != null) {
            Drawable badgeBackground = DrawableCompat
                    .wrap(ctx.getResources().getDrawable(R.drawable.badge));
            DrawableCompat.setTint(badgeBackground, badge.getColor());
            badgeView.setBackground(badgeBackground);
            badgeView.setTextColor(badge.getTextColor());
            badgeView.setText(badge.getText());
            badgeView.setVisibility(View.VISIBLE);
        }

        return view;
    }

    /***********************************************************************************************
     *
     * Builder
     *
     */

    public static class Builder{

        private IconDrawerItem item;

        public Builder(int itemId){
            item = new IconDrawerItem(itemId);
        }

        public Builder text(@StringRes int resId){
            item.textResId = resId;
            return this;
        }

        public Builder icon(@DrawableRes int resId){
            item.iconResId = resId;
            return this;
        }

        public Builder badge(Badge badge){
            item.badge = badge;
            return this;
        }

        public IconDrawerItem build(){
            return item;
        }

    }

}
