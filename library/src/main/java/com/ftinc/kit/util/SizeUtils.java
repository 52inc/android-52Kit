package com.ftinc.kit.util;

import android.content.Context;
import android.util.TypedValue;

public final class SizeUtils {

    private SizeUtils() {
        // Unused.
    }


    /**
     * Convert Density-Independent Pixels to actual pixels
     *
     * @param ctx       the application context
     * @param dpSize    the size in DP units
     * @return          the size in Pixel units
     */
    public static float dpToPx(Context ctx, float dpSize) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpSize, ctx.getResources().getDisplayMetrics());
    }


    /**
     * Convert Density-Independent Pixels to actual pixels
     *
     * @param ctx       the application context to convert the value with
     * @param dpSize    the size in DP units
     * @return          the size in Pixel units
     */
    public static int dipToPx(Context ctx, float dpSize){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpSize, ctx.getResources().getDisplayMetrics());
    }


    /**
     * Convert Scale-Dependent Pixels to actual pixels
     *
     * @param ctx       the application context
     * @param spSize    the size in SP units
     * @return          the size in Pixel units
     */
    public static float spToPx(Context ctx, float spSize){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spSize, ctx.getResources().getDisplayMetrics());
    }
}
