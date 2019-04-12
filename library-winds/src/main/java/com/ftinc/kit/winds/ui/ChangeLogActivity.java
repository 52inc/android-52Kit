/*
 * Copyright (c) 2019 52inc.
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

package com.ftinc.kit.winds.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ftinc.kit.winds.R;
import com.ftinc.kit.winds.Winds;

import butterknife.ButterKnife;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.winds.ui
 * Created by drew.heavner on 3/13/15.
 */
public class ChangeLogActivity extends AppCompatActivity implements View.OnClickListener {

    /***********************************************************************************************
     *
     * Constants
     *
     */

    public static final String EXTRA_CONFIG = "extra_configuration";

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private Toolbar mAppbar;
    private FrameLayout mContainer;
    private int mConfigResId;

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changelog);

        mAppbar = findViewById(R.id.appbar);
        mContainer = findViewById(R.id.container);

        configAppBar();
        parseExtras(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_CONFIG, mConfigResId);
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    private void parseExtras(Bundle icicle){

        Intent intent = getIntent();
        if(intent != null){
            mConfigResId = intent.getIntExtra(EXTRA_CONFIG, -1);
        }

        if(icicle != null){
            mConfigResId = icicle.getInt(EXTRA_CONFIG, -1);
        }

        if(mConfigResId == -1){
            finish();
            return;
        }

        // Parse configuration
        RecyclerView recycler = Winds.draft(this, mConfigResId);

        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        mContainer.addView(recycler, params);
    }

    /**
     * Configure the Appbar
     */
    private void configAppBar(){
        setSupportActionBar(mAppbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.changelog_activity_title);
        mAppbar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
