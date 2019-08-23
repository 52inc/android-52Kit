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

package com.ftinc.kit.extensions

import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.Dimension.DP
import androidx.annotation.Dimension.SP

fun View.dp(@Dimension(unit = DP) dp: Int) : Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.resources.displayMetrics)
fun View.dip(@Dimension(unit = DP) dp: Int) : Int = dp(dp).toInt()
fun View.sp(@Dimension(unit = SP) sp: Int): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), this.resources.displayMetrics)
fun View.sip(@Dimension(unit = SP) sp: Int): Int = sp(sp).toInt()

@ColorInt
fun View.color(@ColorRes resId: Int) : Int = ContextCompat.getColor(this.context, resId)
fun View.drawable(@DrawableRes resId: Int) : Drawable? = ContextCompat.getDrawable(this.context, resId)
fun View.plural(@PluralsRes resId: Int, quantity: Int, vararg args: Any): String {
    return this.resources.getQuantityString(resId, quantity, *args)
}
fun View.string(@StringRes resId: Int): String? = context.getString(resId)
fun View.string(@StringRes resId: Int, vararg args: Any): String = context.getString(resId, *args)
fun View.dimenPixelSize(@DimenRes resId: Int): Int = resources.getDimensionPixelSize(resId)
fun View.dimen(@DimenRes resId: Int): Float = resources.getDimension(resId)
fun View.int(@IntegerRes resId: Int): Int = resources.getInteger(resId)

fun EditText.moveCursorToEnd() {
    this.setSelection(this.text.length)
}

/**
 * Set a rect to the bounds of a [View]
 * @param view the view to apply the bounds of
 */
fun Rect.set(view: View): Rect {
    set(view.left, view.top, view.right, view.bottom)
    return this
}

/**
 * Iterate through every child in a ViewGroup apply [action] to them
 */
fun ViewGroup.forEach(action: (View) -> Unit) {
    val count = childCount
    for (i in 0 until count) {
        val child = getChildAt(i)
        action(child)
    }
}

/**
 * Iterate through a ViewGroup's children and apply the [action] to their
 * casted form of [T]
 */
inline fun <reified T : View> ViewGroup.forEachAs(action: (T) -> Unit) {
    val count = childCount
    for (i in 0 until count) {
        val child = getChildAt(i)
        if (child is T) {
            action(child)
        }
    }
}

fun ViewGroup.forEachIndexed(action: (Int, View) -> Unit) {
    val count = childCount
    for (i in 0 until count) {
        val child = getChildAt(i)
        action(i, child)
    }
}


@Suppress("UNCHECKED_CAST")
fun ViewGroup.first(selector: (View) -> Boolean): View? {
    val count = childCount
    for (i in (0 until count)) {
        val child = getChildAt(i)
        if (selector(child)) {
            return child
        }
    }
    return null
}

fun View.layout(bounds: RectF) {
    this.layout(
            bounds.left.toInt(),
            bounds.top.toInt(),
            bounds.right.toInt(),
            bounds.bottom.toInt()
    )
}

fun View.layout(x: Int, y: Int) {
    this.layout(
            x,
            y,
            x + measuredWidth,
            y + measuredHeight
    )
}

fun View.layoutWidth(width: Int) {
    val lp = this.layoutParams
    lp.width = width
    this.layoutParams = lp
}

fun View.layoutHeight(height: Int) {
    val lp = this.layoutParams
    lp.height = height
    this.layoutParams = lp
}

fun View.addLayoutHeight(height: Int) {
    val lp = this.layoutParams
    lp.height = lp.height + height
    this.layoutParams = lp
}

fun View.margins(left: Int? = null,
                 top: Int? = null,
                 right: Int? = null,
                 bottom: Int? = null) {
    val lp = this.layoutParams as? ViewGroup.MarginLayoutParams
    lp?.let { params ->
        left?.let { params.leftMargin = it }
        top?.let { params.topMargin = it }
        right?.let { params.rightMargin = it }
        bottom?.let { params.bottomMargin = it }
        this.layoutParams = lp
    }
}

fun View.marginsRelative(start: Int? = null,
                         top: Int? = null,
                         end: Int? = null,
                         bottom: Int? = null) {
    val lp = this.layoutParams as? ViewGroup.MarginLayoutParams
    lp?.let { params ->
        start?.let { params.marginStart = it }
        top?.let { params.topMargin = it }
        end?.let { params.marginEnd = it }
        bottom?.let { params.bottomMargin = it }
        this.layoutParams = lp
    }
}
