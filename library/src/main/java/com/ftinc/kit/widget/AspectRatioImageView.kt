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

package com.ftinc.kit.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import com.ftinc.kit.R
import kotlin.math.roundToInt


class AspectRatioImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    enum class RatioType {
        WIDTH, HEIGHT
    }

    var ratioType = RatioType.WIDTH
        set(value) {
            if (field == value) return
            field = value
            requestLayout()
        }

    init {
        parseAttributes(attrs, defStyle)
    }

    private fun parseAttributes(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView, defStyle, 0)
        if (a != null) {
            ratioType = RatioType.values()[a.getInt(R.styleable.AspectRatioImageView_ratioType,
                    RatioType.WIDTH.ordinal)]
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val drawable = drawable
        if (getDrawable() != null) {
            when(ratioType) {
                RatioType.WIDTH -> {
                    val width = MeasureSpec.getSize(widthMeasureSpec)
                    val height = (width * (drawable.intrinsicHeight.toFloat()
                            / drawable.intrinsicWidth.toFloat())).roundToInt()
                    setMeasuredDimension(width, height)
                }
                RatioType.HEIGHT -> {
                    val height = MeasureSpec.getSize(heightMeasureSpec)
                    val width = (height * drawable.intrinsicWidth.toFloat()
                            / drawable.intrinsicHeight.toFloat()).roundToInt()
                    setMeasuredDimension(width, height)
                }
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}
