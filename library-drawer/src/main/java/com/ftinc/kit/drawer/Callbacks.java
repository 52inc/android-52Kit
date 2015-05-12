/*
 * Copyright © 52inc 2015.
 * All rights reserved.
 */

package com.ftinc.kit.drawer;

import android.view.View;

/**
 * Project: TradeVersity
 * Package: com.ftinc.tradeversity.ui.widget.drawer
 * Created by drew.heavner on 5/11/15.
 */
public interface Callbacks {

    void onDrawerOpened(View drawerView);
    void onDrawerClosed(View drawerView);
    void onDrawerSlide(View drawerView, float slideOffset);

}
