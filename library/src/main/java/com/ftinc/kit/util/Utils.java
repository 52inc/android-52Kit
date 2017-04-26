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

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;
import android.webkit.MimeTypeMap;

import java.net.URLConnection;
import java.util.Calendar;
import java.util.Random;

/**
 * This is the standard utils file containing common util functions
 * throughout my applications.
 * 
 * @author drew.heavner
 *
 */
public class Utils {

	/**
	 * Constants
	 */

	/**
	 * Variables
	 */
	
	// Random generator for use in the application
	private static Random random = new Random();

	/**
	 * Get the Random Number Generator
	 * @return  the static random class
	 */
	public static Random getRandom(){ return random; }

    /**
     * Get the time from epoch in seconds
     *
     * @return      epoch seconds
     */
    public static long time(){
        return System.currentTimeMillis()/1000;
    }

    /**
     * Return whether or not a given string is a valid email address according to
     * the {@link Patterns#EMAIL_ADDRESS}
     *
     * @param email     the string to validate
     * @return          true if string is a valid email
     */
    public static boolean isValidEmail(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).find();
    }

    /**
     * Return whether or not the current device is an emulator
     */
    public static boolean isEmulator(){
        return "google_sdk".equals(Build.PRODUCT) ||
                Build.PRODUCT.contains("sdk_google_phone") ||
                "sdk".equals(Build.PRODUCT) ||
                "sdk_x86".equals(Build.PRODUCT) ||
                "vbox86p".equals(Build.PRODUCT);
    }


    /**
     * Get uri to any resource type
     * @param context - context
     * @param resId - resource id
     * @throws Resources.NotFoundException if the given ID does not exist.
     * @return - Uri to resource by given id
     */
    public static Uri getUriFromResource(@NonNull Context context, @AnyRes int resId) throws Resources.NotFoundException {
        /** Return a Resources instance for your application's package. */
        Resources res = context.getResources();
        /**
         * Creates a Uri which parses the given encoded URI string.
         * @param uriString an RFC 2396-compliant, encoded URI
         * @throws NullPointerException if uriString is null
         * @return Uri for this given uri string
         */
        /** return uri */
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));
    }
	
	/**
	 * Get the Device's GMT Offset
	 * @return	the gmt offset in hours
	 */
	public static int getGMTOffset(){
		Calendar now = Calendar.getInstance();
		return (now.get(Calendar.ZONE_OFFSET) + now.get(Calendar.DST_OFFSET))  / 3600000;
	}
	
	/**
	 * Get the MIME type of a file
	 * @param url
	 * @return
	 */
	public static String getMimeType(String url)
	{
	    String type = null;
	    String extension = MimeTypeMap.getFileExtensionFromUrl(url);
	    if (extension != null) {
	        MimeTypeMap mime = MimeTypeMap.getSingleton();
	        type = mime.getMimeTypeFromExtension(extension);
	    }
	    return type;
	}

    public static String getMimeTypeFromExt(String ext){
        String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
        if(mime != null)
            return mime;
        else
            return "";
    }
	
	/**
	 * Parse a file's mime type from the file extension
	 * 
	 * @param filename
	 * @return
	 */
	public static String parseMimeType(String filename){
		return URLConnection.guessContentTypeFromName(filename);
	}

    /**
     * Compute the distance between two points
     *
     * @param p1		the first point
     * @param p2		the second point
     * @return			the distance between the two points
     */
    public static float distance(PointF p1, PointF p2){
        return (float) Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow(p2.y - p1.y,2));
    }

    /**
     * Clamp Integer values to a given range
     *
     * @param value     the value to clamp
     * @param min       the minimum value
     * @param max       the maximum value
     * @return          the clamped value
     */
    public static int clamp(int value, int min, int max){
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Clamp Float values to a given range
     *
     * @param value     the value to clamp
     * @param min       the minimum value
     * @param max       the maximum value
     * @return          the clamped value
     */
    public static float clamp(float value, float min, float max){
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Clamp Long values to a given range
     *
     * @param value     the value to clamp
     * @param min       the minimum value
     * @param max       the maximum value
     * @return          the clamped value
     */
    public static long clamp(long value, long min, long max){
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Clamp Double values to a given range
     *
     * @param value     the value to clamp
     * @param min       the minimum value
     * @param max       the maximum value
     * @return          the clamped value
     */
    public static double clamp(double value, double min, double max){
        return Math.max(min, Math.min(max, value));
    }


    /**
     * Compares two {@code long} values.
     * @return 0 if lhs = rhs, less than 0 if lhs &lt; rhs, and greater than 0 if lhs &gt; rhs.
     * @since 1.7
     */
    public static int compare(long lhs, long rhs) {
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }

    /**
     * Compares two {@code int} values.
     * @return 0 if lhs = rhs, less than 0 if lhs &lt; rhs, and greater than 0
     *         if lhs &gt; rhs.
     * @since 1.7
     */
    public static int compare(int lhs, int rhs) {
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }

    /**
     * Parse a float from a String in a safe manner.
     *
     * @param val           the string to parse
     * @param defVal        the default value to return if parsing fails
     * @return              the parsed float, or default value
     */
    public static float parseFloat(String val, float defVal){
        if(TextUtils.isEmpty(val)) return defVal;
        try{
            return Float.parseFloat(val);
        }catch (NumberFormatException e){
            return defVal;
        }
    }

    /**
     * Parse a int from a String in a safe manner.
     *
     * @param val           the string to parse
     * @param defValue      the default value to return if parsing fails
     * @return              the parsed int, or default value
     */
    public static int parseInt(String val, int defValue){
        if(TextUtils.isEmpty(val)) return defValue;
        try{
            return Integer.parseInt(val);
        }catch (NumberFormatException e){
            return defValue;
        }
    }

    /**
     * Parse a long from a String in a safe manner.
     *
     * @param val           the string to parse
     * @param defValue      the default value to return if parsing fails
     * @return              the parsed long, or default value
     */
    public static long parseLong(String val, long defValue){
        if(TextUtils.isEmpty(val)) return defValue;
        try{
            return Long.parseLong(val);
        }catch (NumberFormatException e){
            return defValue;
        }
    }

    /**
     * Parse a double from a String in a safe manner
     *
     * @param val           the string to parse
     * @param defValue      the default value to return in parsing fails
     * @return              the parsed double, or default value
     */
    public static double parseDouble(String val, double defValue){
        if(TextUtils.isEmpty(val)) return defValue;
        try{
            return Double.parseDouble(val);
        }catch(NumberFormatException e){
            return defValue;
        }
    }

}
