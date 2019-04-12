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

package com.ftinc.kit.kotlin.extensions


import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.util.TypedValue
import android.view.View


fun View.dpToPx(dp: Float) : Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)
fun View.dipToPx(dp: Float) : Int = this.dpToPx(dp).toInt()
fun View.spToPx(sp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.resources.displayMetrics)

@ColorInt fun View.color(@ColorRes resId: Int) : Int = ContextCompat.getColor(this.context, resId)
fun View.drawable(@DrawableRes resId: Int) : Drawable? = ContextCompat.getDrawable(this.context, resId)
fun View.plural(@PluralsRes resId: Int, quantity: Int, vararg args: Any): String {
    return this.resources.getQuantityString(resId, quantity, *args)
}
fun View.string(@StringRes resId: Int): String? = this.context.getString(resId)
fun View.string(@StringRes resId: Int, vararg args: Any): String = this.context.getString(resId, *args)

fun View.visible() { this.visibility = View.VISIBLE }
fun View.invisible(){ this.visibility = View.INVISIBLE }
fun View.gone(){ this.visibility = View.GONE }

fun View.setVisible(visible: Boolean) {
    this.visibility = if(visible) View.VISIBLE else View.GONE
}

fun View.setVisibleWeak(visible: Boolean) {
    this.visibility = if(visible) View.VISIBLE else View.INVISIBLE
}
