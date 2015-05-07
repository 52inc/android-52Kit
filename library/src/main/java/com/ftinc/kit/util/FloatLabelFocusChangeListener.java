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

package com.ftinc.kit.util;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * @deprecated Found a better Float Label library to use: "https://github.com/rengwuxian/MaterialEditText"
 */
@Deprecated
public class FloatLabelFocusChangeListener implements View.OnFocusChangeListener{

    private Context ctx;
    private View label;
    private int labelId, defaultColor, themeColor;

    /**
     * Constructor
     *
     * @param label             the 'FloatLabel' View
     * @param labelId           the id of the floating hint label
     * @param defaultColor      the default non-focused color
     * @param themeColor        the theme focused color
     */
    public FloatLabelFocusChangeListener(View label,
                                         int labelId,
                                         @ColorRes int defaultColor,
                                         @ColorRes int themeColor){
        this.ctx = label.getContext();
        this.label = label;
        this.labelId = labelId;
        this.defaultColor = defaultColor;
        this.themeColor = themeColor;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        TextView floatLabel = ButterKnife.findById(label, labelId);
        if(floatLabel != null) {
            if (!hasFocus) {
                floatLabel.setTextColor(ctx.getResources().getColor(defaultColor));
            } else {
                floatLabel.setTextColor(ctx.getResources().getColor(themeColor));
            }
        }else{
            Timber.e("Error finding float label");
        }

        onFocusChanged(v, hasFocus);
    }

    /**
     * Override this to get notified of focus change without killing functionality of
     * this class
     * s
     * @param v
     * @param hasFocus
     */
    public void onFocusChanged(View v, boolean hasFocus){}

};