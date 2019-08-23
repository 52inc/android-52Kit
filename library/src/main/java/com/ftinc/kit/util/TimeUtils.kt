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

package com.ftinc.kit.util

object TimeUtils {

    private const val SECOND = 1000
    private const val MINUTE = 60 * SECOND
    private const val HOUR = 60 * MINUTE
    private const val DAY = 24 * HOUR

    /**
     * Get a timestamp in a an *ago format
     * e.g.; "just now", "a minute ago", "5 minutes ago", "10 hours ago", "2 days ago", etc...
     * @param time the time to format
     * @return the formatted time
     */
    fun getRelativeTimeSpan(time: Long): String? {
        var _time = time

        // If time is in seconds, convert to milliseconds
        if (_time < 1000000000000L) {
            _time *= 1000
        }

        val now = System.currentTimeMillis()
        if (_time > now || _time <= 0) {
            return null
        }

        val diff = now - _time
        return if (diff < MINUTE) {
            "just now"
        } else if (diff < 2 * MINUTE) {
            "a minute ago"
        } else if (diff < 50 * MINUTE) {
            (diff / MINUTE).toString() + " minutes ago"
        } else if (diff < 90 * MINUTE) {
            "an hour ago"
        } else if (diff < 24 * HOUR) {
            (diff / HOUR).toString() + " hours ago"
        } else if (diff < 48 * HOUR) {
            "yesterday"
        } else {
            (diff / DAY).toString() + " days ago"
        }
    }

}
