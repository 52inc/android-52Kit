package com.ftinc.kit.kotlin.extensions


import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View


fun View.dpToPx(dp: Float) : Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)
fun View.dipToPx(dp: Float) : Int = this.dpToPx(dp).toInt()
fun View.spToPx(sp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.resources.displayMetrics)

@ColorInt fun View.color(@ColorRes resId: Int) : Int = ContextCompat.getColor(this.context, resId)
fun View.drawable(@DrawableRes resId: Int) : Drawable = ContextCompat.getDrawable(this.context, resId)
fun View.visible() { this.visibility = View.VISIBLE }
fun View.invisible(){ this.visibility = View.INVISIBLE }
fun View.gone(){ this.visibility = View.GONE }

fun View.setVisible(visible: Boolean) {
    this.visibility = if(visible) View.VISIBLE else View.GONE
}

fun View.setVisibleWeak(visible: Boolean) {
    this.visibility = if(visible) View.VISIBLE else View.INVISIBLE
}
