package com.ftinc.kit.example.ui.screens.drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ftinc.kit.drawer.Callbacks;
import com.ftinc.kit.drawer.Drawer;
import com.ftinc.kit.example.R;
import com.ftinc.kit.widget.EmptyView;
import com.ftinc.kit.winds.Winds;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

import static com.ftinc.kit.example.ui.screens.drawer.ExampleDrawerConfig.Item.BOOKMARKS;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.example.ui.screens.drawer
 * Created by drew.heavner on 5/12/15.
 */
public class DrawerActivity extends AppCompatActivity implements Callbacks {

    public static final String EXTRA_PAGE = "extra_page";
    private int mCurrentPage = BOOKMARKS.ordinal();

    @BindView(R.id.appbar)
    Toolbar mToolbar;

    @BindView(R.id.empty_layout)
    EmptyView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_example);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

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
                .toolbar(mToolbar)
                .item(mCurrentPage)
                .attach(this);

        mEmptyView.setOnActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DrawerActivity.this, "EmptyView Action Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // SHOW THE WINDS DIALOG
        Winds.openChangelogDialog(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_PAGE, mCurrentPage);
    }



    @DebugLog
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
