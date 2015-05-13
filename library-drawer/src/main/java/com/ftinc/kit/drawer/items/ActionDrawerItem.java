/*
 * Copyright © 52inc 2015.
 * All rights reserved.
 */

package com.ftinc.kit.drawer.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftinc.kit.drawer.R;
import com.ftinc.kit.font.Face;
import com.ftinc.kit.font.FontLoader;
import com.ftinc.kit.util.UIUtils;

import butterknife.ButterKnife;

/**
 * Created by r0adkll on 11/12/14.
 */
public class ActionDrawerItem extends DrawerItem {

    private int text;
    private int icon;

    /**
     * Constructor
     *
     * @param textResId     the text of the drawer item
     * @param iconResId     the icon resource id to display
     */
    public ActionDrawerItem(int id, int textResId, int iconResId){
        super(id);
        this.text = textResId;
        this.icon = iconResId;
    }

    /**
     * Called to create this view
     *
     * @param inflater      the layout inflater to inflate a layout from system
     * @param container     the container the layout is going to be placed in
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        Context ctx = inflater.getContext();
        View view = inflater.inflate(R.layout.navdrawer_item, container, false);

        ImageView iconView = ButterKnife.findById(view, R.id.icon);
        TextView titleView = ButterKnife.findById(view, R.id.title);

        // Set the text
        FontLoader.apply(titleView, Face.ROBOTO_MEDIUM);
        titleView.setText(text);

        // Set the icon, if provided
        iconView.setVisibility(icon > 0 ? View.VISIBLE : View.GONE);
        if(icon > 0)
            iconView.setImageResource(icon);

        // configure its appearance according to whether or not it's selected
        titleView.setTextColor(selected ?
                UIUtils.getColorAttr(ctx, R.attr.colorPrimary) :
                ctx.getResources().getColor(R.color.navdrawer_text_color));
        iconView.setColorFilter(selected ?
                UIUtils.getColorAttr(ctx, R.attr.colorPrimary) :
                ctx.getResources().getColor(R.color.navdrawer_icon_tint));

        return view;
    }

}
