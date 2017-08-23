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
