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


import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.util.TypedValue


fun ViewHolder.dpToPx(dp: Float) : Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.itemView.resources.displayMetrics)
fun ViewHolder.dipToPx(dp: Float) : Int = this.dpToPx(dp).toInt()
fun ViewHolder.spToPx(sp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.itemView.resources.displayMetrics)
@ColorInt fun ViewHolder.color(@ColorRes resId: Int): Int = this.itemView.color(resId)
@ColorInt fun ViewHolder.color(@ColorRes resId: Int?): Int? = resId?.let { this.itemView.color(it) }
fun ViewHolder.string(@StringRes resId: Int): String = this.itemView.context.getString(resId)
fun ViewHolder.string(@StringRes resId: Int, vararg args: Any): String = this.itemView.context.getString(resId, *args)
fun ViewHolder.plural(@PluralsRes resId: Int, quantity: Int, vararg args: Any): String = this.itemView.context.resources.getQuantityString(resId, quantity, *args)