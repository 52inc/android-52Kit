/*
 * Copyright (c) 2015 52inc
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
 */

package com.ftinc.kit.util;

import android.graphics.Color;

public class ColorUtils {
    private static final int BRIGHTNESS_THRESHOLD = 130;

    private static class Yuv {
        public float y;
        public float u;
        public float v;

        public Yuv(int c) {
            int r = Color.red(c);
            int g = Color.green(c);
            int b = Color.blue(c);
            this.y = 0.299f * r + 0.587f * g + 0.114f * b;
            this.u = (b - y) * 0.493f;
            this.v = (r - y) * 0.877f;
        }
    }

    public static int getColor(int color0, int color1, float p) {
        Yuv c0 = new Yuv(color0);
        Yuv c1 = new Yuv(color1);
        float y = ave(c0.y, c1.y, p);
        float u = ave(c0.u, c1.u, p);
        float v = ave(c0.v, c1.v, p);

        int b = (int) (y + u / 0.493f);
        int r = (int) (y + v / 0.877f);
        int g = (int) (1.7f * y - 0.509f * r - 0.194f * b);

        return Color.rgb(r, g, b);
    }

    public static int getColorWithAlpha(int c0, int c1, float p){

        int r0 = Color.red(c0);
        int g0 = Color.green(c0);
        int b0 = Color.blue(c0);
        int a0 = Color.alpha(c0);

        int r1 = Color.red(c1);
        int g1 = Color.green(c1);
        int b1 = Color.blue(c1);
        int a1 = Color.alpha(c1);

        // Interpolate between values
        int r = (int) interpolate(r0, r1, p);
        int g = (int) interpolate(g0, g1, p);
        int b = (int) interpolate(b0, b1, p);
        int a = (int) interpolate(a0, a1, p);

        return Color.argb(a, r, g, b);
    }

    private static float interpolate(float a, float b, float proportion) {
        return (a + ((b - a) * proportion));
    }

    private static float ave(float src, float dst, float p) {
        return src + Math.round(p * (dst - src));
    }

    /**
     * Calculate whether a color is light or dark, based on a commonly known
     * brightness formula.
     *
     * @see {@literal http://en.wikipedia.org/wiki/HSV_color_space%23Lightness}
     */
    public static boolean isColorDark(int color) {
        return ((30 * Color.red(color) +
                59 * Color.green(color) +
                11 * Color.blue(color)) / 100) <= BRIGHTNESS_THRESHOLD;
    }
}