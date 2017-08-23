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

package com.ftinc.kit.eula;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.eula
 * Created by drew.heavner on 6/16/15.
 */
public class EulaActivity extends AppCompatActivity {

    /***********************************************************************************************
     *
     * Static Methods
     *
     */

    /**
     * Generate a pre-populated intent to launch this activity with
     *
     * @param ctx           the context to create the intent with
     * @param logoResId     the resource id of the logo you want to use
     * @param eulaText      the EULA text to display
     * @return              the intent to launch
     */
    public static Intent createIntent(Context ctx, int logoResId, CharSequence eulaText){
        Intent intent = new Intent(ctx, EulaActivity.class);
        intent.putExtra(EXTRA_LOGO, logoResId);
        intent.putExtra(EXTRA_EULA_TEXT, eulaText);
        return intent;
    }

    /***********************************************************************************************
     *
     * Constants
     *
     */

    public static final String EXTRA_LOGO = "extra_logo_resource_id";
    public static final String EXTRA_EULA_TEXT = "extra_eula_text";

    /***********************************************************************************************
     *
     * Variables
     *
     */

    /*@BindView(R2.id.logo)*/ ImageView mLogo;
    private TextView mEulaText;
    private TextView mActionCancel;
    private TextView mActionAccept;

    private int mLogoResId;
    private CharSequence mEulaContent;

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eula);
        initUI();
        parseExtras(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_LOGO, mLogoResId);
        outState.putCharSequence(EXTRA_EULA_TEXT, mEulaContent);
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    void initUI(){
        mLogo = ButterKnife.findById(this, R.id.logo);
        mEulaText = ButterKnife.findById(this, R.id.eula_text);
        mActionCancel = ButterKnife.findById(this, R.id.action_cancel);
        mActionAccept = ButterKnife.findById(this, R.id.action_accept);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });

        mActionAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
            }
        });
    }

    void parseExtras(Bundle icicle){
        Intent intent = getIntent();

        if(intent != null){
            mLogoResId = intent.getIntExtra(EXTRA_LOGO, -1);
            if(mLogoResId != -1) mLogo.setImageResource(mLogoResId);

            mEulaContent = intent.getCharSequenceExtra(EXTRA_EULA_TEXT);
            mEulaText.setText(mEulaContent);
        }

        if(intent != null){
            mLogoResId = icicle.getInt(EXTRA_LOGO, -1);
            if(mLogoResId != -1) mLogo.setImageResource(mLogoResId);

            mEulaContent = icicle.getCharSequence(EXTRA_EULA_TEXT, "No Text Found");
            mEulaText.setText(mEulaContent);
        }

    }

}
