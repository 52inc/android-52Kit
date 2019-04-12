/*
 * Copyright (c) 2019 52inc.
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
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.IntDef;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.ftinc.kit.R;


public class AspectRatioImageView extends AppCompatImageView {


    public static final int RATIO_WIDTH = 0;
    public static final int RATIO_HEIGHT = 1;

    @IntDef({RATIO_WIDTH, RATIO_HEIGHT})
	public @interface RatioType {}

    private int ratioType = 0;


	public AspectRatioImageView(Context context) {
		this(context, null);
	}
	
	public AspectRatioImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AspectRatioImageView(Context context, AttributeSet attrs,
                                int defStyle) {
		super(context, attrs, defStyle);
		parseAttributes(context, attrs, defStyle);
	}

	public void setRatioType(@RatioType int ratioType) {
		this.ratioType = ratioType;
		requestLayout();
	}

	private void parseAttributes(Context context, AttributeSet attrs, int defStyle) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView, defStyle, 0);
		if (a != null) {
			ratioType = a.getInt(R.styleable.AspectRatioImageView_ratioType, RATIO_WIDTH);
			a.recycle();
		}
	}

	/**
	 * Maintain Image Aspect Ratio no matter the size
	 * 
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Drawable drawable = getDrawable();
		if (getDrawable() != null) {
			if (ratioType == RATIO_WIDTH) {
				int width = MeasureSpec.getSize(widthMeasureSpec);
				int height = Math.round(width * (((float) drawable.getIntrinsicHeight()) / ((float) drawable.getIntrinsicWidth())));
				setMeasuredDimension(width, height);
			} else if (ratioType == RATIO_HEIGHT) {
				int height = MeasureSpec.getSize(heightMeasureSpec);
				int width = Math.round(height * ((float) drawable.getIntrinsicWidth()) / ((float) drawable.getIntrinsicHeight()));
				setMeasuredDimension(width, height);
			}
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}
