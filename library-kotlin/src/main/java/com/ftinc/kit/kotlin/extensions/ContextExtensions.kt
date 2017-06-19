package com.ftinc.kit.kotlin.extensions


import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.TypedValue


fun Context.dpToPx(dp: Float) : Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)
fun Context.dipToPx(dp: Float) : Int = this.dpToPx(dp).toInt()
fun Context.spToPx(sp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.resources.displayMetrics)

@ColorInt fun Context.color(@ColorRes resId: Int) : Int = ContextCompat.getColor(this, resId)
fun Context.drawable(@DrawableRes resId: Int) : Drawable = ContextCompat.getDrawable(this, resId)
