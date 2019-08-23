package com.ftinc.kit.extensions

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils

@ColorInt
fun Int.contrastColor(context: Context,
                      @ColorRes lightColor: Int,
                      @ColorRes darkColor: Int): Int {
    return if (ColorUtils.calculateContrast(Color.WHITE, this) < 3.0) {
        ContextCompat.getColor(context, darkColor)
    } else {
        ContextCompat.getColor(context, lightColor)
    }
}
