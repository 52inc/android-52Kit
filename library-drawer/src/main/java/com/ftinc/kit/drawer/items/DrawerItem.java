/*
 * Copyright (c) 2017 52inc.
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
 *
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
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, int highlightColor);

    @Override
    public String toString() {
        return String.format("%s [id: %d, selected: %b]", this.getClass().getName(), id, selected);
    }
}
