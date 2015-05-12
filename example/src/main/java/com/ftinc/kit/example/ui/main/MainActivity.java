/*
 * Copyright (c) 2015 52inc
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
 */

package com.ftinc.kit.example.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ftinc.kit.example.R;
import com.ftinc.kit.mvp.BaseActivity;
import com.ftinc.kit.winds.Winds;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements MainView{

    /***********************************************************************************************
     *
     * Variables
     *
     */

    @InjectView(R.id.appbar)
    Toolbar mToolbar;

    @Inject
    InputMethodManager mImm;

    @Inject
    MainPresenter mPresenter;

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        // Parse the extras here
        mPresenter.parseExtras(savedInstanceState);

        // Check the changelog
        Winds.checkChangelogDialog(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.saveInstanceState(outState);
    }

    @OnClick({R.id.action_licenses, R.id.action_winds})
    void onActionClick(View v){
        mPresenter.onActionClicked(v.getId());
    }

    /***********************************************************************************************
     *
     * Base Methods
     *
     */

    @Override
    protected Object[] getModules() {
        return new Object[]{
            new MainModule(this)
        };
    }

    /***********************************************************************************************
     *
     * View Methods
     *
     */

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showSnackBar(String text) {
        Snackbar.with(this)
                .text(text)
                .swipeToDismiss(true)
                .type(SnackbarType.MULTI_LINE)
                .show(this);
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    @Override
    public void closeKeyboard() {

    }
}
