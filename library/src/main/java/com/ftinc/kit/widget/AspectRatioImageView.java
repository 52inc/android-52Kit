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

package com.ftinc.kit.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * This was created based on a snippet from StackOverflow 
 * from user @user Michel-F. Portzert at the thread
 * http://stackoverflow.com/questions/12059328/android-imageview-fit-width
 * 
 * 
 * @author drew.heavner
 *
 */
public class AspectRatioImageView extends ImageView{

    // Ratio Type Constants
    public static final int RATIO_WIDTH = 0;
    public static final int RATIO_HEIGHT = 1;

    // The Ratio Type
    private int mRatioType = 0;

	/**
	 * Constructor
	 * @param context
	 */
	public AspectRatioImageView(Context context) {
		super(context);
	}
	
	public AspectRatioImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AspectRatioImageView(Context context, AttributeSet attrs,
                                int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Maintain Image Aspect Ratio no matter the size
	 * 
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dimHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 140, getResources().getDisplayMetrics());

		if(getDrawable() != null){
            if(mRatioType == RATIO_WIDTH) {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = Math.round(width * ((float) getDrawable().getIntrinsicHeight() / (float) getDrawable().getIntrinsicWidth()));
                setMeasuredDimension(width, height);
            }else if(mRatioType == RATIO_HEIGHT){
                int height = dimHeight;
                int width = Math.round(height * ((float) getDrawable().getIntrinsicWidth() / (float) getDrawable().getIntrinsicHeight()));
                setMeasuredDimension(width, height);
            }
		}else{
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}
