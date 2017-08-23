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


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.ftinc.kit.R;


public class ProgressIndicator extends View {


    private int mProgress = 0;
    private int mMax = 0;

    private int mProgressColor;

    private boolean mIsAnimating = false;
    private float mAnimValue = 0f;

    private Paint mPaint;


    public ProgressIndicator(Context context) {
        this(context, null);
    }

    public ProgressIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs, defStyleAttr);
        init();
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    private void init(){

        mPaint = new Paint();
        mPaint.setColor(mProgressColor);

    }

    private void parseAttributes(Context context, AttributeSet attrs, int defStyle){
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ProgressIndicator, defStyle, 0);
        if (a == null) {
            setBackgroundColor(context.getResources().getColor(R.color.primary_material_light));
            mProgressColor = context.getResources().getColor(R.color.primary_dark_material_light);
            return;
        }

        mProgress = a.getInteger(R.styleable.ProgressIndicator_progress, 0);
        mMax = a.getInteger(R.styleable.ProgressIndicator_max, 0);
        mProgressColor = a.getColor(R.styleable.ProgressIndicator_progressColor,
                context.getResources().getColor(R.color.primary_dark_material_light));

        a.recycle();
    }


    public void setProgress(final int progress){

        // Animate to new progress
        float oldProgress = ((float)mProgress) / ((float)mMax);
        float newProgress = ((float)progress) / ((float)mMax);
        float oldWidth = oldProgress * getWidth();
        float newWidth = newProgress * getWidth();
        ValueAnimator anim = ValueAnimator.ofFloat(oldWidth, newWidth);
        anim.setDuration(300);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimValue = (float)animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIsAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimating = false;
                mProgress = progress;
                invalidate();
            }
        });
        anim.start();

    }

    public void setMax(int max){
        mMax = max;
        invalidate();
    }

    public void setProgressColor(int color){
        mProgressColor = color;
        mPaint.setColor(mProgressColor);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!mIsAnimating) {
            // Render the progress bar
            float progress = ((float) mProgress) / ((float) mMax);
            float width = getWidth() * progress;

            // Draw the progress
            canvas.drawRect(0, 0, width, getHeight(), mPaint);
        }else{
            canvas.drawRect(0, 0, mAnimValue, getHeight(), mPaint);
        }
    }
}
