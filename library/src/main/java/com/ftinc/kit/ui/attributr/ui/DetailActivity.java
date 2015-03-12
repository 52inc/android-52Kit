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

package com.ftinc.kit.ui.attributr.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.ftinc.kit.R;

/**
 * Created by r0adkll on 3/12/15.
 */
public class DetailActivity extends ActionBarActivity {

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



    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_detail);


    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */


}


