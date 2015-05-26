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

package com.ftinc.kit.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftinc.kit.R;
import com.ftinc.kit.font.FontLoader;
import com.ftinc.kit.font.Face;

/**
 * Project: Chipper
 * Package: com.r0adkll.chipper.ui.widget
 * Created by drew.heavner on 11/20/14.
 */
public class EmptyView extends RelativeLayout {

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private ImageView mIcon;
    private TextView mMessage;

    private int mEmptyIcon = -1;
    private int mEmptyIconSize = -1;
    private int mAccentColor;
    private String mEmptyMessage = "You currently don't have any items";

    /***********************************************************************************************
     *
     * Constructors
     *
     */

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(context, attrs, defStyleAttr);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs, defStyleAttr);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs, 0);
        init();
    }

    public EmptyView(Context context) {
        super(context);
        init();
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    @Override
    public boolean isInEditMode() {
        return true;
    }

    /**
     * Parse XML attributes
     *
     * @param attrs     the attributes to parse
     */
    private void parseAttributes(Context context, AttributeSet attrs, int defStyle){
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.EmptyView, defStyle, 0);
        if (a == null) {
            mAccentColor = context.getResources().getColor(R.color.black26);
            return;
        }

        // Parse attributes
        mEmptyMessage = a.getString(R.styleable.EmptyView_emptyMessage);
        mAccentColor = a.getColor(R.styleable.EmptyView_emptyAccentColor, context.getResources().getColor(R.color.black26));
        mEmptyIcon = a.getResourceId(R.styleable.EmptyView_emptyIcon, -1);
        mEmptyIconSize = a.getDimensionPixelSize(R.styleable.EmptyView_emptyIconSize, -1);

        a.recycle();
    }

    /**
     * Initialize the Empty Layout
     */
    private void init(){

        // Create the Empty Layout
        LinearLayout container = new LinearLayout(getContext());
        mIcon = new ImageView(getContext());
        mMessage = new TextView(getContext());

        // Setup the layout
        LayoutParams containerParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        container.setGravity(Gravity.CENTER);
        container.setOrientation(LinearLayout.VERTICAL);
        containerParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        // Setup the Icon
        if(mEmptyIcon > 0) {
            int width = mEmptyIconSize == -1 ? LayoutParams.WRAP_CONTENT : mEmptyIconSize;
            int height = mEmptyIconSize == -1 ? LayoutParams.WRAP_CONTENT : mEmptyIconSize;
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(width, height);
            int padding = getResources().getDimensionPixelSize(R.dimen.half_padding);
            mIcon.setPadding(0, 0, 0, padding);
            mIcon.setColorFilter(mAccentColor, PorterDuff.Mode.SRC_IN);
            mIcon.setImageResource(mEmptyIcon);
            container.addView(mIcon, iconParams);
        }else{
            mIcon.setVisibility(View.GONE);
        }

        // Setup the message
        LinearLayout.LayoutParams msgParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mMessage.setTextColor(mAccentColor);
        mMessage.setGravity(Gravity.CENTER);
        mMessage.setText(mEmptyMessage);
        FontLoader.apply(mMessage, Face.ROBOTO_REGULAR);

        // Add to the layout
        container.addView(mMessage, msgParams);

        // Add to view
        addView(container, containerParams);
    }

    public void setAccentColor(int colorResId){
        mAccentColor = getResources().getColor(colorResId);
        mIcon.setColorFilter(mAccentColor, PorterDuff.Mode.SRC_IN);
        mMessage.setTextColor(mAccentColor);
    }

    public void setIcon(int resId){
        mIcon.setImageResource(resId);
    }

    public void setEmptyMessage(CharSequence message){
        mMessage.setText(message);
    }

}
