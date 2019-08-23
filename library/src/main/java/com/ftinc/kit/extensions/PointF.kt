package com.ftinc.kit.extensions

import android.graphics.PointF
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * CAVEAT: this uses the [sqrt] function which can be costly if used in frequent iterations or
 * drawing code.
 */
infix fun PointF.distanceTo(other: PointF): Double {
    return sqrt((other.x - x).toDouble().pow(2.0) + (other.y - y).toDouble().pow(2.0))
}
