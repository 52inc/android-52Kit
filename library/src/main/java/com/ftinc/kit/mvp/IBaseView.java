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

package com.ftinc.kit.mvp;

import android.app.Activity;

/**
 * This is the base 'View' interface for all MVP representations for this
 * UI
 *
 * Project: HealthyEating
 * Package: co.ftinc.healthyeating.ui.model
 * Created by drew.heavner on 12/5/14.
 */
public interface IBaseView {

    /**
     * Get the Activity reference for the view this is attached to
     * @return      the activity
     */
    public Activity getActivity();

    /**
     * Show a basic message snackbar
     *
     * @param text      the text to display
     */
    public void showSnackBar(String text);

    /**
     * Show the Loading indicator for the UI
     */
    public void showLoading();

    /**
     * Hide the loading indicator for the UI
     */
    public void hideLoading();

    /**
     * Close the Keyboard for the activity
     */
    public void closeKeyboard();

}
