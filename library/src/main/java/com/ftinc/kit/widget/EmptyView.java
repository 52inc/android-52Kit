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
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftinc.kit.R;
import com.ftinc.kit.font.FontLoader;
import com.ftinc.kit.font.Face;
import com.ftinc.kit.util.UIUtils;
import com.ftinc.kit.util.Utils;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Project: Chipper
 * Package: com.r0adkll.chipper.ui.widget
 * Created by drew.heavner on 11/20/14.
 */
public class EmptyView extends RelativeLayout {

    enum MessageTypeface{
        REGULAR(Face.ROBOTO_REGULAR),
        MEDIUM(Face.ROBOTO_MEDIUM),
        BOLD(Face.ROBOTO_BOLD),
        THIN(Face.ROBOTO_THIN),
        LIGHT(Face.ROBOTO_LIGHT);

        private final Face mFont;

        MessageTypeface(Face font){
            mFont = font;
        }

        public Face getTypeface(){
            return mFont;
        }

        public static MessageTypeface from(int attrEnum){
            return values()[attrEnum];
        }

    }

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private ImageView mIcon;
    private TextView mMessage;
    private TextView mAction;

    private int mEmptyIcon = -1;
    private int mEmptyIconSize = -1;
    private int mEmptyIconColor = -1;

    private CharSequence mEmptyMessage = "You currently don't have any items";
    private int mEmptyMessageColor = -1;
    private Face mEmptyMessageTypeface = Face.ROBOTO_REGULAR;
    private int mEmptyMessageTextSize;

    private CharSequence mEmptyActionText;
    private int mEmptyActionColor = -1;
    private int mEmptyActionTextSize;

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
        int defaultColor = context.getResources().getColor(R.color.black26);
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.EmptyView, defStyle, 0);
        if (a == null) {
            mEmptyMessageColor = defaultColor;
            mEmptyIconColor = defaultColor;
            mEmptyActionColor = defaultColor;
            mEmptyMessageTextSize = (int) Utils.dpToPx(context, 18);
            mEmptyActionTextSize = (int) Utils.dpToPx(context, 14);
            return;
        }

        // Parse attributes
        mEmptyIcon = a.getResourceId(R.styleable.EmptyView_emptyIcon, -1);
        mEmptyIconSize = a.getDimensionPixelSize(R.styleable.EmptyView_emptyIconSize, -1);
        mEmptyIconColor = a.getColor(R.styleable.EmptyView_emptyIconColor, defaultColor);

        mEmptyMessage = a.getString(R.styleable.EmptyView_emptyMessage);
        mEmptyMessageColor = a.getColor(R.styleable.EmptyView_emptyMessageColor, defaultColor);
        int typeface = a.getInt(R.styleable.EmptyView_emptyMessageTypeface, 0);
        mEmptyMessageTypeface = MessageTypeface.from(typeface).getTypeface();
        mEmptyMessageTextSize = a.getDimensionPixelSize(R.styleable.EmptyView_emptyMessageTextSize,
                (int)Utils.dpToPx(context, 18));

        mEmptyActionColor = a.getColor(R.styleable.EmptyView_emptyActionColor, defaultColor);
        mEmptyActionText = a.getString(R.styleable.EmptyView_emptyActionText);
        mEmptyActionTextSize = a.getDimensionPixelSize(R.styleable.EmptyView_emptyActionTextSize,
                (int)Utils.dpToPx(context, 14));

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
        mAction = new TextView(getContext());

        // Setup the layout
        LayoutParams containerParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        container.setGravity(Gravity.CENTER);
        container.setOrientation(LinearLayout.VERTICAL);
        containerParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        // Setup the Icon
        if(mEmptyIcon > 0) {
            int width = mEmptyIconSize == -1 ? WRAP_CONTENT : mEmptyIconSize;
            int height = mEmptyIconSize == -1 ? WRAP_CONTENT : mEmptyIconSize;
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(width, height);
            int padding = getResources().getDimensionPixelSize(R.dimen.activity_padding);
            mIcon.setPadding(0, 0, 0, padding);
            mIcon.setColorFilter(mEmptyIconColor, PorterDuff.Mode.SRC_IN);
            mIcon.setImageResource(mEmptyIcon);
            container.addView(mIcon, iconParams);
        }else{
            mIcon.setVisibility(View.GONE);
        }

        // Setup the message
        LinearLayout.LayoutParams msgParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        mMessage.setTextSize(mEmptyMessageTextSize, TypedValue.COMPLEX_UNIT_PX);
        mMessage.setTextColor(mEmptyMessageColor);
        mMessage.setGravity(Gravity.CENTER);
        mMessage.setText(mEmptyMessage);

        // Add to the layout
        container.addView(mMessage, msgParams);

        // Setup the Action Label
        LinearLayout.LayoutParams actionParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        actionParams.topMargin = getResources().getDimensionPixelSize(R.dimen.activity_padding);
        actionParams.gravity = Gravity.CENTER_HORIZONTAL;
        int padding = (int) Utils.dpToPx(getContext(), 8);
        mAction.setText(mEmptyActionText);
        mAction.setTextColor(mEmptyActionColor);
        mAction.setTextSize(mEmptyActionTextSize, TypedValue.COMPLEX_UNIT_PX);
        mAction.setAllCaps(true);
        mAction.setPadding(padding, padding, padding, padding);
        mAction.setVisibility(TextUtils.isEmpty(mEmptyActionText) ? View.GONE : View.VISIBLE);
        UIUtils.setBackground(mAction, UIUtils.getSelectableItemBackground(getContext()));

        if(!isInEditMode()){
            FontLoader.apply(mMessage, mEmptyMessageTypeface);
            FontLoader.apply(mAction, Face.ROBOTO_MEDIUM);
        }

        container.addView(mAction, actionParams);

        // Add to view
        addView(container, containerParams);
    }

    /***********************************************************************************************
     *
     * Public setters and getters
     *
     */

    public void setIcon(@DrawableRes int resId){
        mIcon.setImageResource(resId);
    }

    public void setIcon(Drawable drawable){
        mIcon.setImageDrawable(drawable);
    }

    public void setIconSize(int size){
        mEmptyIconSize = size;
        int width = mEmptyIconSize == -1 ? WRAP_CONTENT : mEmptyIconSize;
        int height = mEmptyIconSize == -1 ? WRAP_CONTENT : mEmptyIconSize;
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(width, height);
        mIcon.setLayoutParams(iconParams);
    }

    public void setIconColor(@ColorInt int color){
        mEmptyIconColor = color;
        mIcon.setColorFilter(mEmptyIconColor, PorterDuff.Mode.SRC_IN);
    }

    public void setIconColorRes(@ColorRes int resId){
        setIconColor(getResources().getColor(resId));
    }

    public Drawable getIcon(){
        return mIcon.getDrawable();
    }

    public void setEmptyMessage(CharSequence message){
        mEmptyMessage = message.toString();
        mMessage.setText(message);
    }

    public void setEmptyMessage(@StringRes int resId){
        setEmptyMessage(getResources().getString(resId));
    }

    public CharSequence getEmptyMessage(){
        return mEmptyMessage;
    }

    public void setMessageTypeface(Typeface typeface){
        mMessage.setTypeface(typeface);
    }

    public void setMessageTypeface(Face typeface){
        FontLoader.apply(mMessage, typeface);
    }

    public void setActionLabel(CharSequence label){
        mEmptyActionText = label;
        mAction.setText(mEmptyActionText);
        mAction.setVisibility(TextUtils.isEmpty(mEmptyActionText) ? View.GONE : View.VISIBLE);
    }

    public void setActionLabelRes(@StringRes int resId){
        setActionLabel(getResources().getString(resId));
    }

    public CharSequence getActionLabel(){
        return mEmptyActionText;
    }

    public void setActionColor(@ColorInt int color){
        mEmptyActionColor = color;
        mAction.setTextColor(mEmptyActionColor);
    }

    public void setActionColorRes(@ColorRes int color){
        setActionColor(getResources().getColor(color));
    }

    @ColorInt
    public int getActionColor(){
        return mEmptyActionColor;
    }




}
