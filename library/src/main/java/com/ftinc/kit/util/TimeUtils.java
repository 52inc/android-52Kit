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
import android.text.format.DateUtils;

import com.ftinc.kit.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.TimeZone;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by r0adkll on 3/8/15.
 */
public class TimeUtils {
    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    /**
     * <p>
     *  Get a timestamp in a an *ago format
     *  e.g.; "just now", "a minute ago", "5 minutes ago", "10 hours ago", "2 days ago", etc...
     * </p>
     *
     * @param time      the time to format
     * @return          the formated time
     */
    public static String getTimeAgo(long time) {
        // TODO: use DateUtils methods instead
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE) {
            return "just now";
        } else if (diff < 2 * MINUTE) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE) {
            return diff / MINUTE + " minutes ago";
        } else if (diff < 90 * MINUTE) {
            return "an hour ago";
        } else if (diff < 24 * HOUR) {
            return diff / HOUR + " hours ago";
        } else if (diff < 48 * HOUR) {
            return "yesterday";
        } else {
            return diff / DAY + " days ago";
        }
    }

    /**
     * Generate a fancy timestamp based on unix epoch time that is more user friendly than just
     * a raw output by collapsing the time into manageable formats based on how much time has
     * elapsed since epoch
     *
     * @param epoch     the time in unix epoch
     * @return          the fancy timestamp
     */
    public static String fancyTimestamp(long epoch){

        // First, check to see if it's within 1 minute of the current date
        if(System.currentTimeMillis() - epoch < 60000){
            return "Just now";
        }

        // Get calendar for just now
        Calendar now = Calendar.getInstance();

        // Generate Calendar for this time
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(epoch);

        // Based on the date, determine what to print out
        // 1) Determine if time is the same day
        if(cal.get(YEAR) == now.get(YEAR)){

            if(cal.get(MONTH) == now.get(MONTH)){

                if(cal.get(DAY_OF_MONTH) == now.get(DAY_OF_MONTH)){

                    // Return just the time
                    SimpleDateFormat format = new SimpleDateFormat("h:mm a");
                    return format.format(cal.getTime());
                } else {

                    // Return the day and time
                    SimpleDateFormat format = new SimpleDateFormat("EEE, h:mm a");
                    return format.format(cal.getTime());
                }

            }else{

                SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, h:mm a");
                return format.format(cal.getTime());
            }

        }else{

            SimpleDateFormat format = new SimpleDateFormat("M/d/yy");
            return format.format(cal.getTime());

        }

    }

    /**
     * Returns "Today", "Tomorrow", "Yesterday", or a short date format.
     */
    public static String formatHumanFriendlyShortDate(final Context context, long timestamp) {
        long localTimestamp, localTime;
        long now = System.currentTimeMillis();

        TimeZone tz = TimeZone.getDefault();
        localTimestamp = timestamp + tz.getOffset(timestamp);
        localTime = now + tz.getOffset(now);

        long dayOrd = localTimestamp / 86400000L;
        long nowOrd = localTime / 86400000L;

        if (dayOrd == nowOrd) {
            return context.getString(R.string.day_title_today);
        } else if (dayOrd == nowOrd - 1) {
            return context.getString(R.string.day_title_yesterday);
        } else if (dayOrd == nowOrd + 1) {
            return context.getString(R.string.day_title_tomorrow);
        } else {
            return formatShortDate(context, new Date(timestamp));
        }
    }

    public static String formatShortDate(Context context, Date date) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        return DateUtils.formatDateRange(context, formatter, date.getTime(), date.getTime(),
                DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_NO_YEAR,
                TimeZone.getDefault().getID()).toString();
    }

}
