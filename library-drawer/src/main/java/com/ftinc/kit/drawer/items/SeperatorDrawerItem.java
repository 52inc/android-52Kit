/*
 * Copyright © 52inc 2015.
 * All rights reserved.
 */

package com.ftinc.kit.drawer.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftinc.kit.drawer.R;
import com.ftinc.kit.util.UIUtils;

/**
 * Created by r0adkll on 11/13/14.
 */
public class SeperatorDrawerItem extends DrawerItem{

    /**
     * Blank Constructor
     */
    public SeperatorDrawerItem(){
        super(-2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, int highlightColor) {
        View view = inflater.inflate(R.layout.navdrawer_separator, container, false);
        UIUtils.setAccessibilityIgnore(view);

        int color = UIUtils.getColorAttr(container.getContext(), R.attr.drawerSeparatorColor);
        if(color != 0) view.setBackgroundColor(color);

        return view;
    }
}
