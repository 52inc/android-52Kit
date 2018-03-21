/*
 * Copyright (c) 2018 52inc.
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

package com.ftinc.kit.kotlin.extensions

import java.text.SimpleDateFormat
import java.util.*


fun Date.toCalendar(): Calendar {
    val c = Calendar.getInstance()
    c.time = this
    return c
}


fun Calendar.clearTime(): Calendar {
    val current = Calendar.getInstance()
    val year = current[Calendar.YEAR]
    val month = current[Calendar.MONTH]
    val day = current[Calendar.DAY_OF_MONTH]

    current.clear()
    current[Calendar.YEAR] = year
    current[Calendar.MONTH] = month
    current[Calendar.DAY_OF_MONTH] = day

    return current
}


fun Calendar.setDate(year: Int, month: Int, dayOfMonth: Int): Calendar {
    this.clearTime()
    this[Calendar.YEAR] = year
    this[Calendar.MONTH] = month
    this[Calendar.DAY_OF_MONTH] = dayOfMonth
    return this
}


fun Long.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.clear()
    cal.timeInMillis = this
    return cal
}


fun Long.get(field: Int): Int = this.toCalendar().get(field)


fun Calendar.isToday(): Boolean {
    val current = Calendar.getInstance()
    return current.get(Calendar.YEAR) == this.get(Calendar.YEAR) &&
            current.get(Calendar.DAY_OF_YEAR) == this.get(Calendar.DAY_OF_YEAR)
}


fun Calendar.isYesterday(): Boolean {
    val current = Calendar.getInstance()
    current.add(Calendar.DAY_OF_YEAR, -1)
    return current.get(Calendar.YEAR) == this.get(Calendar.YEAR) &&
            current.get(Calendar.DAY_OF_YEAR) == this.get(Calendar.DAY_OF_YEAR)
}


fun Calendar.isThisWeek(): Boolean {
    val current = Calendar.getInstance()
    return current.get(Calendar.YEAR) == this.get(Calendar.YEAR) &&
            current.get(Calendar.WEEK_OF_YEAR) == this.get(Calendar.WEEK_OF_YEAR)
}


fun Calendar.isThisYear(): Boolean {
    val current = Calendar.getInstance()
    return current.get(Calendar.YEAR) == this.get(Calendar.YEAR)
}


fun Date.dateOfBirth(): String {
    val f = SimpleDateFormat("M/d/yyyy", Locale.getDefault())
    return f.format(this)
}