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

import android.content.Context;
import android.graphics.PointF;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.util.TypedValue;
import android.webkit.MimeTypeMap;

import com.ftinc.kit.preferences.StringPreference;

import java.net.URLConnection;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

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
     * Generate a unique device identifier that can be replicated
     * on the device
     * <p>
     * CAVEAT This method requires the android.Manifest.permission#READ_PHONE_STATE permission
     * </p>
     * @param ctx       the application context
     * @return          the device unique id
     */
    public static String generateUniqueDeviceId(Context ctx){
        final TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(ctx.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }

    /**
     * Generate a new UUID if one is not stored. This id is session/install based so every time
     * you install/re-install or wipe data for this application, this value will update and
     * change.
     *
     * @param uuidPref      the uuid preference
     * @return              the unique device id
     */
    public static String generateUniqueDeviceId(StringPreference uuidPref){
        // Check if uuid is set
        if(uuidPref.isSet()){
            return uuidPref.get();
        }else{
            String uuid = UUID.randomUUID().toString();
            uuidPref.set(uuid);
            return uuid;
        }
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
