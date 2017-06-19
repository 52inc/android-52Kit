package com.ftinc.kit.kotlin.extensions


import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView.ViewHolder
import android.util.TypedValue


fun ViewHolder.dpToPx(dp: Float) : Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.itemView.resources.displayMetrics)
fun ViewHolder.dipToPx(dp: Float) : Int = this.dpToPx(dp).toInt()
fun ViewHolder.spToPx(sp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.itemView.resources.displayMetrics)
@ColorInt fun ViewHolder.color(@ColorRes resId: Int): Int = this.itemView.color(resId)
@ColorInt fun ViewHolder.color(@ColorRes resId: Int?): Int? = resId?.let { this.itemView.color(it) }
fun ViewHolder.string(@StringRes resId: Int, vararg args: Any): String = this.itemView.context.getString(resId, *args)
