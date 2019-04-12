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

package com.ftinc.kit.attributr.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.ftinc.kit.attributr.R;
import com.ftinc.kit.util.BuildUtils;
import com.ftinc.kit.util.IntentUtils;
import com.ftinc.kit.util.UIUtils;

import butterknife.ButterKnife;

/**
 * Created by r0adkll on 3/12/15.
 */
public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    /***********************************************************************************************
     *
     * Constants
     *
     */

    public static final String EXTRA_LIBRARY = "extra_library";

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private Toolbar mAppbar;
    private TextView mName, mAuthor, mLicense;
    private com.ftinc.kit.attributr.model.Library mLibrary;

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set window transition elements
        if(BuildUtils.isLollipop()) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

            TransitionSet transitions = new TransitionSet()
                   .addTransition(new ChangeBounds())
                   .addTransition(new Fade());

            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
            getWindow().setSharedElementEnterTransition(transitions);
            getWindow().setSharedElementExitTransition(transitions);
        }
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);

        setContentView(R.layout.activity_license_detail);

        // Inflate components
        mAppbar = findViewById(R.id.appbar);
        mName = findViewById(R.id.name);
        mAuthor = findViewById(R.id.author);
        mLicense = findViewById(R.id.license);

        // Apply components
        setSupportActionBar(mAppbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mAppbar.setNavigationOnClickListener(this);

        // Parse extras
        parseExtras(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_LIBRARY, mLibrary);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.library_detail, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(mLibrary != null){
            MenuItem link = menu.findItem(R.id.action_link);
            if(link != null ){
                if(!TextUtils.isEmpty(mLibrary.url)){
                    link.setVisible(true);
                    link.getIcon().setColorFilter(UIUtils.getColorAttr(this, android.R.attr.textColorPrimaryInverse), PorterDuff.Mode.SRC_ATOP);
                }else{
                    link.setVisible(false);
                }
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_link){
            Intent intent = IntentUtils.openLink(mLibrary.url);
            if(IntentUtils.isIntentAvailable(this, intent)){
                startActivity(intent);
                return true;
            }else{
                Toast.makeText(this, R.string.app_for_intent_not_found, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the user clicks the toolbar nav icon
     */
    @Override
    public void onClick(View v) {
        finish();
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    private void parseExtras(Bundle icicle){

        Intent intent = getIntent();
        if(intent != null){
            mLibrary = intent.getParcelableExtra(EXTRA_LIBRARY);
        }

        if(icicle != null){
            mLibrary = icicle.getParcelable(EXTRA_LIBRARY);
        }

        if(mLibrary == null) {
            finish();
            return;
        }

        // Inflate the lib
        mName.setText(mLibrary.name);
        mAuthor.setText(mLibrary.author);
        mLicense.setText(mLibrary.getLicenseText());

        // Make sure that the menu items are properly prepared
        supportInvalidateOptionsMenu();
    }

}


