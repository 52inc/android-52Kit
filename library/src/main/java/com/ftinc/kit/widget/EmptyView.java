/*
 * Copyright (c) 2018 52inc.
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
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftinc.kit.R;
import com.ftinc.kit.font.Face;
import com.ftinc.kit.font.FontLoader;
import com.ftinc.kit.util.SizeUtils;
import com.ftinc.kit.util.UIUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class EmptyView extends RelativeLayout {

    /***********************************************************************************************
     *
     * Constants
     *
     */

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

    public static final int STATE_EMPTY = 0;
    public static final int STATE_LOADING = 1;

    @IntDef({STATE_EMPTY, STATE_LOADING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface States{}

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private ImageView mIcon;
    private TextView mMessage;
    private TextView mAction;
    private ProgressBar mProgress;

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

    private OnClickListener mActionClickListener;

    private int mState = STATE_EMPTY;

    /***********************************************************************************************
     *
     * Constructors
     *
     */

    public EmptyView(Context context) {
        this(context, null);
        parseAttributes(context, null, 0);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs, 0);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("EmptyView - Icon[resource: %d, size: %d, color: %d]", mEmptyIcon, mEmptyIconSize, mEmptyIconColor)).append("\n");
        builder.append(String.format("EmptyView - Message[text: %s, color: %d, typeface: %s, size: %d]", mEmptyMessage, mEmptyMessageColor, mEmptyMessageTypeface.name(), mEmptyMessageTextSize)).append("\n");
        builder.append(String.format("EmptyView - Action[text: %s, color: %d, size: %d]", mEmptyActionText, mEmptyActionColor, mEmptyActionTextSize)).append("\n");
        return builder.toString();
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

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
            mEmptyMessageTextSize = (int) SizeUtils.dpToPx(context, 18);
            mEmptyActionTextSize = (int) SizeUtils.dpToPx(context, 14);
            mEmptyMessageTypeface = Face.ROBOTO_REGULAR;
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
                (int) SizeUtils.dpToPx(context, 18));

        mEmptyActionColor = a.getColor(R.styleable.EmptyView_emptyActionColor, defaultColor);
        mEmptyActionText = a.getString(R.styleable.EmptyView_emptyActionText);
        mEmptyActionTextSize = a.getDimensionPixelSize(R.styleable.EmptyView_emptyActionTextSize,
                (int) SizeUtils.dpToPx(context, 14));

        mState = a.getInt(R.styleable.EmptyView_emptyState, STATE_EMPTY);

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
        mProgress = new ProgressBar(getContext());

        // Setup the layout
        LayoutParams containerParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        container.setGravity(Gravity.CENTER);
        container.setOrientation(LinearLayout.VERTICAL);
        containerParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        // Setup the Icon
        if(mEmptyIcon > 0) {
            mIcon.setImageResource(mEmptyIcon);
        }else{
            mIcon.setVisibility(View.GONE);
        }

        mIcon.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.activity_padding));
        mIcon.setColorFilter(mEmptyIconColor, PorterDuff.Mode.SRC_IN);
        int width = mEmptyIconSize == -1 ? WRAP_CONTENT : mEmptyIconSize;
        int height = mEmptyIconSize == -1 ? WRAP_CONTENT : mEmptyIconSize;
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(width, height);
        container.addView(mIcon, iconParams);

        // Setup the message
        LinearLayout.LayoutParams msgParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        mMessage.setGravity(Gravity.CENTER);
        mMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, mEmptyMessageTextSize);
        mMessage.setTextColor(mEmptyMessageColor);
        mMessage.setText(mEmptyMessage);

        // Add to the layout
        container.addView(mMessage, msgParams);

        // Setup the Action Label
        LinearLayout.LayoutParams actionParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        actionParams.topMargin = getResources().getDimensionPixelSize(R.dimen.activity_padding);
        int padding = (int) SizeUtils.dpToPx(getContext(), 8);
        mAction.setText(mEmptyActionText);
        mAction.setTextColor(mEmptyActionColor);
        mAction.setTextSize(TypedValue.COMPLEX_UNIT_PX, mEmptyActionTextSize);
        mAction.setAllCaps(true);
        mAction.setPadding(padding, padding, padding, padding);
        mAction.setVisibility(TextUtils.isEmpty(mEmptyActionText) ? View.GONE : View.VISIBLE);
        UIUtils.setBackground(mAction, UIUtils.getSelectableItemBackground(getContext()));
        mAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActionClickListener != null) mActionClickListener.onClick(v);
            }
        });

        if(!isInEditMode()){
            FontLoader.apply(mMessage, mEmptyMessageTypeface);
            FontLoader.apply(mAction, Face.ROBOTO_MEDIUM);
        }

        container.addView(mAction, actionParams);

        LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        mProgress.setVisibility(View.GONE);

        container.addView(mProgress, progressParams);

        // Add to view
        addView(container, containerParams);

        // Switch the states
        if(mState == STATE_LOADING){
            setLoading();
        }
    }

    /***********************************************************************************************
     *
     * Public setters and getters
     *
     */


    /**
     * Set the Icon by resource identifier
     *
     * @param resId     the icon resource identifier
     */
    public void setIcon(@DrawableRes int resId){
        mIcon.setImageResource(resId);
    }


    /**
     * Set the icon for this empty view
     *
     * @param drawable      the drawable icon
     */
    public void setIcon(Drawable drawable){
        mIcon.setImageDrawable(drawable);
    }


    /**
     * Set the size of the icon in the center of the view
     *
     * @param size      the pixel size of the icon in the center
     */
    public void setIconSize(int size){
        mEmptyIconSize = size;
        int width = mEmptyIconSize == -1 ? WRAP_CONTENT : mEmptyIconSize;
        int height = mEmptyIconSize == -1 ? WRAP_CONTENT : mEmptyIconSize;
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(width, height);
        mIcon.setLayoutParams(iconParams);
    }


    /**
     * Set the color of the icon
     *
     * @param color     the icon color to tint with
     */
    public void setIconColor(@ColorInt int color){
        mEmptyIconColor = color;
        mIcon.setColorFilter(mEmptyIconColor, PorterDuff.Mode.SRC_IN);
    }


    /**
     * Set the color of the icon
     *
     * @param resId     the color resource identifier for the icon
     */
    public void setIconColorRes(@ColorRes int resId){
        setIconColor(getResources().getColor(resId));
    }


    /**
     * Get the icon for this empty view
     *
     * @return      the empty view icon
     */
    public Drawable getIcon(){
        return mIcon.getDrawable();
    }

    /**
     * Set the message of the empty view
     *
     * @param message       the message text
     */
    public void setEmptyMessage(CharSequence message){
        mEmptyMessage = message.toString();
        mMessage.setText(message);
    }


    /**
     * Set the message of the empty view
     *
     * @param resId         the message text resource identifier
     */
    public void setEmptyMessage(@StringRes int resId){
        setEmptyMessage(getResources().getString(resId));
    }


    /**
     * Set the text color of the message textview
     *
     * @param color     the color integer to set the text to
     */
    public void setEmptyMessageColor(@ColorInt int color){
        mMessage.setTextColor(color);
    }


    /**
     * Set the text color of the message textview
     *
     * @param resId     the resource id of the color you want to set the text to
     */
    public void setEmptyMessageColorResource(@ColorRes int resId){
        setEmptyMessageColor(getResources().getColor(resId));
    }


    /**
     * Get the message of the empty view
     *
     * @return      the message text
     */
    public CharSequence getEmptyMessage(){
        return mEmptyMessage;
    }


    /**
     * Set the typeface of the message text
     *
     * @param typeface      the message text typeface
     */
    public void setMessageTypeface(Typeface typeface){
        mMessage.setTypeface(typeface);
    }


    /**
     * Set they message typeface with the {@link FontLoader} {@link Face} enums
     *
     * @param typeface      the typeface enum
     */
    public void setMessageTypeface(Face typeface){
        FontLoader.apply(mMessage, typeface);
    }


    /**
     * Set the action button label, this in-turn enables it. Pass null to disable.
     *
     * @param label     set the action label, thus enabling it
     */
    public void setActionLabel(CharSequence label){
        mEmptyActionText = label;
        mAction.setText(mEmptyActionText);
        mAction.setVisibility(TextUtils.isEmpty(mEmptyActionText) ? View.GONE : View.VISIBLE);
    }


    /**
     * Set the action button label
     *
     * @param resId     the label string resource id
     */
    public void setActionLabelRes(@StringRes int resId){
        setActionLabel(getResources().getString(resId));
    }


    /**
     * Get the action button label
     *
     * @return      the action label
     */
    public CharSequence getActionLabel(){
        return mEmptyActionText;
    }


    /**
     * Set the action button text color
     *
     * @param color     the color resource id
     */
    public void setActionColor(@ColorInt int color){
        mEmptyActionColor = color;
        mAction.setTextColor(mEmptyActionColor);
    }


    /**
     * Set the action button text color
     *
     * @param color     the color integer
     */
    public void setActionColorRes(@ColorRes int color){
        setActionColor(getResources().getColor(color));
    }


    /**
     * Get the action button text color
     *
     * @return      the label color
     */
    @ColorInt
    public int getActionColor(){
        return mEmptyActionColor;
    }


    /**
     * Set this view to loading state to show a loading indicator and hide the other parts
     * of this view.
     *
     * @deprecated see {@link #setLoading(boolean)} and {@link #setState(int)}
     */
    @Deprecated
    public void setLoading(){
        mState = STATE_LOADING;
        mProgress.setVisibility(View.VISIBLE);
        mAction.setVisibility(View.GONE);
        mMessage.setVisibility(View.GONE);
        mIcon.setVisibility(View.GONE);
    }


    /**
     * Set this view to it's empty state showing the icon, message, and action if configured
     *
     * @deprecated see {@link #setLoading(boolean)} and {@link #setState(int)}
     */
    @Deprecated
    public void setEmpty(){
        mState = STATE_EMPTY;
        mProgress.setVisibility(View.GONE);
        if(mEmptyIcon != -1) mIcon.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(mEmptyMessage)) mMessage.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(mEmptyActionText)) mAction.setVisibility(View.VISIBLE);
    }


    /**
     * Set the state of this view
     * @param state set the state as loading or empty
     */
    public void setState(@States int state) {
        switch (state) {
            case STATE_LOADING:
                setLoading();
                break;
            case STATE_EMPTY:
                setEmpty();
                break;
        }
    }


    /**
     * Convience function for {@link #setLoading()} and {@link #setEmpty()}
     * @param isLoading set whether or not this view should show it's loading state
     */
    public void setLoading(boolean isLoading) {
        if (isLoading) {
            setLoading();
        }
        else {
            setEmpty();
        }
    }


    /**
     * Set the action click listener callback
     *
     * @param listener      the listener to be called when the user selects the EmptyView action
     */
    public void setOnActionClickListener(OnClickListener listener){
        mActionClickListener = listener;
    }


}
