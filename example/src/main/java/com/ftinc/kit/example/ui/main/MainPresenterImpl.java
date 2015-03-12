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

import android.os.Bundle;

import com.ftinc.kit.example.R;
import com.ftinc.kit.ui.attributr.Attributr;

/**
 * Created by r0adkll on 3/12/15.
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView mView;

    public MainPresenterImpl(MainView view) {
        this.mView = view;
    }

    @Override
    public void parseExtras(Bundle icicle) {

    }

    @Override
    public void saveInstanceState(Bundle outState) {

    }

    @Override
    public void onActionClicked(int id) {
        switch (id){
            case R.id.action_licenses:
                Attributr.openLicenses(mView.getActivity(), R.xml.example_config);
                break;
            case R.id.action_winds:

                break;
        }
    }
}
