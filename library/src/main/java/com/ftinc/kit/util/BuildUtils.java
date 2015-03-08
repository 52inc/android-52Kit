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

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;

/**
 * Utility class for useful functions relating to the build/os of the device
 *
 * Created by r0adkll on 3/7/15.
 */
public class BuildUtils {

    /**
     * Return whether or not the device is running Lollipop 5.0
     * @see android.os.Build.VERSION_CODES#LOLLIPOP
     */
    public static boolean isLollipop(){
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }

    /**
     * Return whether or not the device is running KitKat 4.4
     * @see android.os.Build.VERSION_CODES#KITKAT
     */
    public static boolean isKitKat(){
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT);
    }

    /**
     * Return whether not the device is running JellyBean 4.3 or greater (MR2)
     * @see android.os.Build.VERSION_CODES#JELLY_BEAN_MR2
     */
    public static boolean isJellyBeanMR2(){
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2);
    }

    /**
     * Check to see if this device is running JellyBean 4.2 or greater (MR1)
     * @see android.os.Build.VERSION_CODES#JELLY_BEAN_MR1
     */
    public static boolean isJellyBeanMR1() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1);
    }

    /**
     * Check to see if this device is running Jelly Bean 4.1 or not
     * @see android.os.Build.VERSION_CODES#JELLY_BEAN
     */
    public static boolean isJellyBean(){
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN);
    }

    /**
     * Check to see if this device is running ICS or greater
     * @see android.os.Build.VERSION_CODES#ICE_CREAM_SANDWICH
     */
    public static boolean isICS(){
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH);
    }

    /**
     * Return whether or not this device is a tablet
     *
     * @return          true if tablet, false if phone
     */
    public static boolean isTablet(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }


    /**
     * Check for a running service
     *
     * @param ctx                   the application context
     * @param serviceClassName      the service class name
     * @return      true if service is running, false overwise
     */
    public static boolean checkForRunningService(Context ctx, String serviceClassName) {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClassName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
