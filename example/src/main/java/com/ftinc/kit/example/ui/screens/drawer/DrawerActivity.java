package com.ftinc.kit.example.ui.screens.drawer;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.ftinc.kit.drawer.Callbacks;
import com.ftinc.kit.drawer.Drawer;
import com.ftinc.kit.example.R;
import com.ftinc.kit.mvp.BaseActivity;
import com.ftinc.kit.widget.EmptyView;
import com.ftinc.kit.widget.ScrimInsetsRelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

import static com.ftinc.kit.example.ui.screens.drawer.ExampleDrawerConfig.Item.*;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.example.ui.screens.drawer
 * Created by drew.heavner on 5/12/15.
 */
public class DrawerActivity extends BaseActivity implements Callbacks {

    public static final String EXTRA_PAGE = "extra_page";
    private int mCurrentPage = BOOKMARKS.ordinal();

    @InjectView(R.id.screen_content)
    ScrimInsetsRelativeLayout mScreenContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_example);
        ButterKnife.inject(this);

        // Parse Extras
        Intent intent = getIntent();
        if(intent != null){
            mCurrentPage = intent.getIntExtra(EXTRA_PAGE, BOOKMARKS.ordinal());
        }

        if(savedInstanceState != null){
            mCurrentPage = savedInstanceState.getInt(EXTRA_PAGE, BOOKMARKS.ordinal());
        }

        ExampleDrawerConfig.Item item = ExampleDrawerConfig.Item.from(mCurrentPage);
        getSupportActionBar().setTitle(item.name());

        // Configure the drawer
        Drawer.with(new ExampleDrawerConfig(this))
                .callbacks(this)
                .toolbar(getActionBarToolbar())
                .item(mCurrentPage)
                .attach(this);

        // Modify the window content to not fit systems window
        ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
        content.getChildAt(0).setFitsSystemWindows(false);

        int statusBarHeight = getStatusBarHeight();
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getActionBarToolbar().getLayoutParams();
        params.topMargin = statusBarHeight;
        getActionBarToolbar().setLayoutParams(params);

        // Do the same for the drawer window
        View drawer = findViewById(R.id.navdrawer);
        drawer.setPadding(0, statusBarHeight, 0, 0);


    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_PAGE, mCurrentPage);
    }




    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }
}
