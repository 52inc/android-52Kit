/*
 * Copyright © 52inc 2015.
 * All rights reserved.
 */

package com.ftinc.kit.drawer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ftinc.kit.drawer.items.ActionDrawerItem;
import com.ftinc.kit.drawer.items.DrawerItem;
import com.ftinc.kit.drawer.items.SeperatorDrawerItem;
import com.ftinc.kit.drawer.items.SwitchDrawerItem;
import com.ftinc.kit.util.BuildUtils;
import com.ftinc.kit.util.UIUtils;
import com.ftinc.kit.widget.ScrimInsetsRelativeLayout;
import com.ftinc.kit.widget.ScrimInsetsRelativeLayout.OnInsetsCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import timber.log.Timber;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * This is the helper class for building and interacting with a navigation drawer without having
 * the specify anything drawer related outside of using this class.
 *
 * Project: TradeVersity
 * Package: com.ftinc.tradeversity.ui.widget.drawer
 * Created by drew.heavner on 5/11/15.
 */
public class Drawer {

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private final Handler mHandler = new Handler();
    private Activity mActivity;
    private Config mConfig;
    private Callbacks mCallbacks;

    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    private View mMainContent;
    private DrawerLayout mDrawerLayout;
    private ScrimInsetsRelativeLayout mDrawerPane;
    private FrameLayout mDrawerContentFrame;
    private FrameLayout mDrawerFooterFrame;

    private Map<Integer, View> mNavDrawerItemViews = new HashMap<>();
    private Map<Integer, DrawerItem> mNavDrawerItems = new HashMap<>();
    private List<DrawerItem> mDrawerItems = new ArrayList<>();
    private int mSelectedItem;

    private View mHeaderView;
    private View mFooterView;
    private LinearLayout mDrawerItemsListContainer;

    /**
     * Private constructor so that the user can only use the builder implementation
     * to construct this drawer
     *
     */
    private Drawer(Config config){
        mConfig = config;
    }

    /***********************************************************************************************
     *
     * Builder constructing methods
     *
     */

    /**
     * Build a drawer with the provided configuration needed to build it
     *
     * @param config        the drawer configuration used to build the drawer
     * @return              self for chaining
     */
    public static Drawer with(Config config){
       return new Drawer(config);
    }

    /**
     * Set the currently selected item in the drawer as to set it's selected state
     *
     * @param selectedItem      the selected item in the drawer
     * @return                  self for chaining
     */
    public Drawer item(int selectedItem){
        mSelectedItem = selectedItem;
        return this;
    }

    /**
     * Set the toolbar that you want to modify with a hamburger
     *
     * @param toolbar       the toolbar to modify
     * @return              self for chaining
     */
    public Drawer toolbar(Toolbar toolbar){
        mToolbar = toolbar;
        return this;
    }

    /**
     * Set some drawer action callback listener (open, close, sliding...)
     *
     * @param callbacks     the action callbacks
     * @return              self for chaining
     */
    public Drawer callbacks(Callbacks callbacks){
        mCallbacks = callbacks;
        return this;
    }

    /**
     * Finish and attach this drawer to an activity by hijacking its content layout
     *
     * @param activity      the activity to attach to
     * @return              self for reference
     */
    public Drawer attach(Activity activity){
        mActivity = activity;

        // Attach this drawer to the predefined Activity
        buildAndAttach();
        return this;
    }

    /***********************************************************************************************
     *
     * Public Methods
     *
     */

    /**
     * Return whether or not hte drawer is open
     * @return
     */
    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.START);
    }

    /**
     * Close the navigation drawer programmatically
     */
    public void closeDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(Gravity.START);
        }
    }

    /**
     * Open the navigation drawer programmatically
     */
    public void openDrawer(){
        if(mDrawerLayout != null){
            mDrawerLayout.openDrawer(Gravity.START);
        }
    }


    /***********************************************************************************************
     *
     * Internal Helper Methods
     *
     */

    /**
     * Build the nav drawer layout, inflate it, then attach it to the
     * activity
     */
    @SuppressLint("NewApi")
    private void buildAndAttach(){
        // Disable any pending transition on the activity since we are transforming it
        mActivity.overridePendingTransition(0, 0);

        // Setup window flags if Lollipop
        if(BuildUtils.isLollipop()) {
            Window window = mActivity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        // attach layout and pane to UI
        hiJackDecor();

        // Setup
        setupDrawer();

        // Populate and Inflate
        populateNavDrawer();

    }

    /**
     * Hijack the activities decor/content view to inject the drawer layout and pane
     * into the activity without having to define the DrawerLayout as the root element in
     * an Activities layout to enable the drawer
     */
    private void hiJackDecor(){

        // Hijack the decorview
        ViewGroup decorView = (ViewGroup)mActivity.getWindow().getDecorView();
        ViewGroup mainContent = (ViewGroup) decorView.findViewById(android.R.id.content);
        mMainContent = mainContent.getChildAt(0);
        mainContent.removeViewAt(0);

        // Inflate DrawerLayout
        mDrawerLayout = inflateDrawerLayout(mainContent);

        // Insert the old activity content into the drawer content frame
        mDrawerContentFrame.addView(mMainContent, new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));

        // Add the drawer-injected layout back into the decor
        mainContent.addView(mDrawerLayout);
    }

    /**
     * Setup the navigation drawer layout and whatnot
     */
    private void setupDrawer(){

        // Set the drawer layout statusbar color
        int statusBarColor = mConfig.getStatusBarColor(mActivity);
        if(statusBarColor != -1) mDrawerLayout.setStatusBarBackgroundColor(statusBarColor);

        // Build the header view
        final FrameLayout headerContainer = ButterKnife.findById(mDrawerPane, R.id.header_container);
        mHeaderView = mConfig.inflateHeader(mActivity.getLayoutInflater(), headerContainer);

        // If hour header view is enabled, add to layout and bind the data from the config
        if(mHeaderView != null){
            headerContainer.addView(mHeaderView);
            mHeaderView.post(new Runnable() {
                @Override
                public void run() {
                    mConfig.bindHeader(mHeaderView);
                }
            });
        }

        // Inflate footer view
        mFooterView = mConfig.inflateFooter(mActivity.getLayoutInflater(), mDrawerFooterFrame);
        if(mFooterView != null){
            mDrawerFooterFrame.addView(mFooterView);
            mFooterView.post(new Runnable() {
                @Override
                public void run() {
                    mConfig.bindFooter(mFooterView);
                }
            });
        }

        // Configure the scrim inset pane view
        final int headerHeight = mActivity.getResources().getDimensionPixelSize(
                R.dimen.navdrawer_chosen_account_height);
        mDrawerPane.setOnInsetsCallback(new OnInsetsCallback() {
            @Override
            public void onInsetsChanged(Rect insets) {
                Timber.i("Drawer Pane insets changed: %s", insets);

                if (mHeaderView != null) {
                    mConfig.onInsetsChanged(mHeaderView, insets);

                    ViewGroup.LayoutParams lp2 = headerContainer.getLayoutParams();
                    lp2.height = headerHeight + insets.top;
                    headerContainer.setLayoutParams(lp2);
                }else{

                    ViewGroup.LayoutParams lp2 = headerContainer.getLayoutParams();
                    lp2.height = insets.top;
                    headerContainer.setLayoutParams(lp2);

                }
            }
        });

        // Setup the drawer toggle
        if(mToolbar != null){
            mDrawerToggle = new ActionBarDrawerToggle(mActivity,
                    mDrawerLayout,
                    mToolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close){

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    if(mCallbacks != null) mCallbacks.onDrawerClosed(drawerView);
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    if(mCallbacks != null) mCallbacks.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    super.onDrawerSlide(drawerView, mConfig.shouldAnimateIndicator() ? slideOffset : 0);
                    if(mCallbacks != null) mCallbacks.onDrawerSlide(drawerView, slideOffset);
                }
            };

            // Defer code dependent on restoration of previous instance state.

            mDrawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    mDrawerToggle.syncState();
                }
            });
            mDrawerLayout.setDrawerListener(mDrawerToggle);
        }

        // Setup the drawer shadow
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

    }

    /**
     * Populate the navigation drawer
     *
     */
    private void populateNavDrawer(){
        // Clear out any existing items
        mDrawerItems.clear();

        // Inflate the drawer items from the config
        mConfig.inflateItems(mDrawerItems);

        // Now generate the items into the view
        createNavDrawerItems();

    }

    /**
     * Populate the nav drawer items into the view
     */
    private void createNavDrawerItems(){
        if (mDrawerItemsListContainer == null) {
            return;
        }

        mNavDrawerItemViews.clear();
        mDrawerItemsListContainer.removeAllViews();
        for (DrawerItem item: mDrawerItems) {
            item.setSelected(item.getId() == mSelectedItem);
            View view = item.onCreateView(mActivity.getLayoutInflater(), mDrawerItemsListContainer);
            if(!(item instanceof SeperatorDrawerItem)){
                view.setId(item.getId());
                mNavDrawerItemViews.put(item.getId(), view);
                mNavDrawerItems.put(item.getId(), item);

                // Set the view's click listener
                if(!(item instanceof SwitchDrawerItem)) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onNavDrawerItemClicked(v.getId());
                        }
                    });
                }

            }

            mDrawerItemsListContainer.addView(view);
        }
    }

    /**
     * Return whether or not a drawer item is special
     * @param itemId
     * @return
     */
    private boolean isSpecialItem(int itemId) {
        DrawerItem item = mNavDrawerItems.get(itemId);
        return item instanceof ActionDrawerItem;
    }

    /**
     * Call when a nav drawer item is clicked
     *
     * @param itemId        the id of the item clicked
     */
    private void onNavDrawerItemClicked(final int itemId) {
        if (itemId == mSelectedItem) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }

        if (isSpecialItem(itemId)) {
            goToNavDrawerItem(itemId);
        } else {
            // launch the target Activity after a short delay, to allow the close animation to play
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToNavDrawerItem(itemId);
                }
            }, mConfig.getLaunchDelay());

            // change the active item on the list so the user can see the item changed
            setSelectedNavDrawerItem(itemId);

            // fade out the main content
//            if (mMainContent != null && mConfig.getFadeOutDuration() != -1) {
//                mMainContent.animate().alpha(0).setDuration(mConfig.getFadeOutDuration());
//            }
        }

        mDrawerLayout.closeDrawer(Gravity.START);
    }

    /**
     * Travel to a specific drawer item destination
     *
     * @param item
     */
    private void goToNavDrawerItem(int item) {
        mConfig.onDrawerItemClicked(mNavDrawerItems.get(item));
    }


    /**
     * Sets up the given navdrawer item's appearance to the selected state. Note: this could
     * also be accomplished (perhaps more cleanly) with state-based layouts.
     */
    private void setSelectedNavDrawerItem(int itemId) {
        for(DrawerItem item: mDrawerItems){
            formatNavDrawerItem(item, itemId == item.getId());
        }
    }

    /**
     * Format a nav drawer item based on current selected states
     * @param item
     * @param selected
     */
    private void formatNavDrawerItem(DrawerItem item, boolean selected) {
        if (item instanceof SeperatorDrawerItem || item instanceof SwitchDrawerItem) {
            // not applicable
            return;
        }

        // Get the associated view
        View view = mNavDrawerItemViews.get(item.getId());

        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);

        // configure its appearance according to whether or not it's selected
        titleView.setTextColor(selected ?
                UIUtils.getColorAttr(mActivity, R.attr.colorPrimary) :
                getResources().getColor(R.color.navdrawer_text_color));

        iconView.setColorFilter(selected ?
                UIUtils.getColorAttr(mActivity, R.attr.colorPrimary) :
                getResources().getColor(R.color.navdrawer_icon_tint), PorterDuff.Mode.SRC_ATOP);
    }

    /***********************************************************************************************
     *
     * View Builders
     *
     */

    /**
     * Build the root drawer layout
     *
     * @return      the root drawer layout
     */
    private DrawerLayout inflateDrawerLayout(ViewGroup parent){
        DrawerLayout drawer = (DrawerLayout) mActivity.getLayoutInflater()
                .inflate(R.layout.material_drawer, parent, false);

        // Find the associated views
        mDrawerPane = ButterKnife.findById(drawer, R.id.navdrawer);
        mDrawerContentFrame = ButterKnife.findById(drawer, R.id.drawer_content_frame);
        mDrawerFooterFrame = ButterKnife.findById(mDrawerPane, R.id.footer);
        mDrawerItemsListContainer = ButterKnife.findById(mDrawerPane, R.id.navdrawer_items_list);

        // Return the drawer
        return drawer;
    }

    /**
     * Get the reference to the apps resources
     * @return
     */
    private Resources getResources(){
        return mActivity.getResources();
    }

}
