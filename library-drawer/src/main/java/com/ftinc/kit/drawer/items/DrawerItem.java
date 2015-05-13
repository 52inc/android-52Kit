/*
 * Copyright © 52inc 2015.
 * All rights reserved.
 */

package com.ftinc.kit.drawer.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by r0adkll on 11/12/14.
 */
public abstract class DrawerItem {

    /* The Identifier for this item */
    protected int id;
    protected boolean selected;

    /**
     * Constructor
     */
    public DrawerItem(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    /**
     * Called to inflate a drawer items' view
     *
     * @param inflater      the layout inflater to inflate a layout from system
     * @param container     the container the layout is going to be placed in
     * @return              the view for this particular drawer item
     */
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container);

    @Override
    public String toString() {
        return String.format("%s [id: %d, selected: %b]", this.getClass().getName(), id, selected);
    }
}
