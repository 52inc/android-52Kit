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


import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import android.util.TypedValue
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment
import com.ftinc.kit.util.ScreenUtils


fun Context.dp(dp: Float) : Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)
fun Context.dip(dp: Float) : Int = this.dp(dp).toInt()
fun Context.sp(sp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.resources.displayMetrics)

fun Fragment.dp(dp: Float) : Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)
fun Fragment.dip(dp: Float) : Int = this.dp(dp).toInt()
fun Fragment.sp(sp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.resources.displayMetrics)

@ColorInt
fun Context.color(@ColorRes resId: Int) : Int = ContextCompat.getColor(this, resId)
fun Context.drawable(@DrawableRes resId: Int) : Drawable? = ContextCompat.getDrawable(this, resId)
fun Context.dimenPixelSize(@DimenRes resId: Int): Int = resources.getDimensionPixelSize(resId)
fun Context.dimen(@DimenRes resId: Int): Float = resources.getDimension(resId)

fun Context.smallestWidth(config: ScreenUtils.Config): Boolean = ScreenUtils.smallestWidth(this.resources, config)


@Suppress("UNCHECKED_CAST")
fun <T> Context.systemService(name: String): Lazy<T> = lazy {
    this.getSystemService(name) as T
}

fun Context.getMetaData(): Bundle? {
    val appInfo = this.packageManager.getApplicationInfo(this.packageName, PackageManager.GET_META_DATA)
    return appInfo?.metaData
}

@Suppress("UNCHECKED_CAST")
fun <T> Context.getMetaData(name: String): T? {
    val appInfo = this.packageManager.getApplicationInfo(this.packageName, PackageManager.GET_META_DATA)
    return appInfo?.metaData?.get(name)?.let { it as? T }
}
