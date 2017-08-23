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

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.IllegalFormatException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Utility for helpful functions that involve common formatting
 *
 * Created by r0adkll on 3/7/15.
 */
public class FormatUtils {

    /**
     * Precision ENUM for condensing a file size
     */
    public enum Precision {
        ONE_DIGIT("%.1f"),
        TWO_DIGIT("%.2f"),
        THREE_DIGIT("%.3f"),
        NONE("%.0f");

        private final String mFormat;
        Precision(String format){
            this.mFormat = format;
        }

        public String getFormat(){
            return mFormat;
        }
    }

    /**
     * Format a time in milliseconds to 'hh:mm:ss' format
     *
     * @param millis		the milliseconds time
     * @return				the time in 'hh:mm:ss' format
     */
    public static String formatTimeText(int millis){
        // Format the milliseconds into the form - 'hh:mm:ss'
        return String.format(Locale.US, "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    /**
     * Format a time in milliseconds to 'mm:ss' format
     *
     * @param millis		the milliseconds time
     * @return				the time in 'mm:ss' format
     */
    public static String formatTimeTextMS(long millis){
        // Format the milliseconds into the form - 'mm:ss'
        return String.format(Locale.US, "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    /**
     * Condense a file size in bytes to it's highest form (i.e. KB, MB, GB, etc)
     *
     * @param bytes		    the size in bytes
     * @param precision     the precision constant {@code ONE_DIGIT}, {@code TWO_DIGIT}, {@code THREE_DIGIT}
     * @return			the condensed string
     */
    public static String condenseFileSize(long bytes, String precision) throws IllegalFormatException {

        // Kilobyte Check
        float kilo = bytes / 1024f;
        float mega = kilo / 1024f;
        float giga = mega / 1024f;
        float tera = giga / 1024f;
        float peta = tera / 1024f;

        // Determine which value to send back
        if(peta > 1)
            return String.format(precision + " PB", peta);
        else if (tera > 1)
            return String.format(precision + " TB", tera);
        else if(giga > 1)
            return String.format(precision + " GB", giga);
        else if(mega > 1)
            return String.format(precision + " MB", mega);
        else if(kilo > 1)
            return String.format(precision + " KB", kilo);
        else
            return bytes + " b";

    }

    /**
     * Condense a file size in bytes to it's highest form (i.e. KB, MB, GB, etc)
     *
     * @param bytes         the size in bytes to condense
     * @param precision     the precision of the decimal place
     * @return              the condensed file size
     */
    public static String condenseFileSize(long bytes, Precision precision){
        return condenseFileSize(bytes, precision.getFormat());
    }

    /**
     * Condense a file size in bytes to it's highest form (i.e. KB, MB, GB, etc)
     *
     * @param bytes		the size in bytes
     * @return			the condensed string
     */
    public static String condenseFileSize(long bytes){
        return condenseFileSize(bytes, Precision.NONE);
    }

    /**
     * Generates the MD5 digest for a given String based on UTF-8. The digest is padded with zeroes in the front if
     * necessary.
     *
     * @return MD5 digest (32 characters).
     */
    public static String generateMD5String(String stringToEncode) {
        return generateDigestString(stringToEncode, "MD5", "UTF-8", 32);
    }

    /**
     * Generates the SHA-1 digest for a given String based on UTF-8. The digest is padded with zeroes in the front if
     * necessary. The SHA-1 algorithm is considers to produce less collisions than MD5.
     *
     * @return SHA-1 digest (40 characters).
     */
    public static String generateSHA1String(String stringToEncode) {
        return generateDigestString(stringToEncode, "SHA-1", "UTF-8", 40);
    }

    public static String generateSHA256String(String stringToEncode){
        return generateDigestString(stringToEncode, "SHA-256", "UTF-8", 0);
    }

    public static String generateDigestString(String stringToEncode, String digestAlgo, String encoding, int lengthToPad) {
        // Loosely inspired by http://workbench.cadenhead.org/news/1428/creating-md5-hashed-passwords-java
        MessageDigest digester;
        try {
            digester = MessageDigest.getInstance(digestAlgo);
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException(nsae);
        }
        try {
            digester.update(stringToEncode.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return toHexString(digester.digest(), lengthToPad);
    }

    public static String toHexString(byte[] bytes, int lengthToPad) {
        BigInteger hash = new BigInteger(1, bytes);
        String digest = hash.toString(16);

        while (digest.length() < lengthToPad) {
            digest = "0" + digest;
        }
        return digest;
    }

}
