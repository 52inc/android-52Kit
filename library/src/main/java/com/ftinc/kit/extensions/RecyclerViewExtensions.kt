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

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.util.TypedValue
import androidx.annotation.*

fun ViewHolder.dp(dp: Int) : Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.itemView.resources.displayMetrics)
fun ViewHolder.dip(dp: Int) : Int = dp(dp).toInt()
fun ViewHolder.sp(sp: Int): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), this.itemView.resources.displayMetrics)
fun ViewHolder.sip(sp: Int): Int = sp(sp).toInt()

@ColorInt fun ViewHolder.color(@ColorRes resId: Int): Int = this.itemView.color(resId)
@ColorInt fun ViewHolder.color(@ColorRes resId: Int?): Int? = resId?.let { this.itemView.color(it) }
@Px fun ViewHolder.dimenPixelSize(@DimenRes resId: Int): Int = itemView.resources.getDimensionPixelSize(resId)
@Dimension fun ViewHolder.dimen(@DimenRes resId: Int): Float = itemView.resources.getDimension(resId)

fun ViewHolder.string(@StringRes resId: Int): String = this.itemView.context.getString(resId)
fun ViewHolder.string(@StringRes resId: Int, vararg args: Any): String = this.itemView.context.getString(resId, *args)
fun ViewHolder.plural(@PluralsRes resId: Int, quantity: Int, vararg args: Any): String = this.itemView.context.resources.getQuantityString(resId, quantity, *args)
