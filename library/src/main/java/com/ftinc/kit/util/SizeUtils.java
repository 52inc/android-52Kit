/*
 * Copyright (c) 2017 52inc.
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
