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

import java.util.IllegalFormatException
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Utility for condensing a filesize
 */
object BytesUtil {

    enum class Precision(val format: String) {
        ONE_DIGIT("%.1f"),
        TWO_DIGIT("%.2f"),
        THREE_DIGIT("%.3f"),
        NONE("%.0f")
    }

    /**
     * Condense a file size in bytes to it's highest form (i.e. KB, MB, GB, etc)
     * @param bytes the size in bytes
     * @param precision the precision constant [Precision]
     * @return the condensed string
     */
    @Throws(IllegalFormatException::class)
    fun condense(bytes: Long, precision: String): String {

        // Kilobyte Check
        val kilo = bytes / 1024f
        val mega = kilo / 1024f
        val giga = mega / 1024f
        val tera = giga / 1024f
        val peta = tera / 1024f

        // Determine which value to send back
        return if (peta > 1)
            String.format("$precision PB", peta)
        else if (tera > 1)
            String.format("$precision TB", tera)
        else if (giga > 1)
            String.format("$precision GB", giga)
        else if (mega > 1)
            String.format("$precision MB", mega)
        else if (kilo > 1)
            String.format("$precision KB", kilo)
        else
            "$bytes b"

    }

    /**
     * Condense a file size in bytes to it's highest form (i.e. KB, MB, GB, etc)
     * @param bytes the size in bytes to condense
     * @param precision the precision of the decimal place
     * @return the condensed file size
     */
    @JvmOverloads
    fun condense(bytes: Long, precision: Precision = Precision.NONE): String {
        return condense(bytes, precision.format)
    }
}
