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

import java.util.concurrent.TimeUnit

import java.util.concurrent.TimeUnit.DAYS
import java.util.concurrent.TimeUnit.HOURS
import java.util.concurrent.TimeUnit.MICROSECONDS
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.MINUTES
import java.util.concurrent.TimeUnit.NANOSECONDS
import java.util.concurrent.TimeUnit.SECONDS

/**
 * An object that measures elapsed time in nanoseconds. It is useful to measure
 * elapsed time using this class instead of direct calls to [ ][System.nanoTime] for a few reasons:
 *
 *
 *  * An alternate time source can be substituted, for testing or performance
 * reasons.
 *  * As documented by `nanoTime`, the value returned has no absolute
 * meaning, and can only be interpreted as relative to another timestamp
 * returned by `nanoTime` at a different time. `Stopwatch` is a
 * more effective abstraction because it exposes only these relative values,
 * not the absolute ones.
 *
 *
 *
 * Basic usage:
 * <pre>
 * Stopwatch stopwatch = Stopwatch.[createStarted][.createStarted]();
 * doSomething();
 * stopwatch.[stop][.stop](); // optional
 *
 * long millis = stopwatch.elapsed(MILLISECONDS);
 *
 * log.info("time: " + stopwatch); // formatted string like "12.3 ms"</pre>
 *
 *
 * Stopwatch methods are not idempotent; it is an error to start or stop a
 * stopwatch that is already in the desired state.
 *
 *
 * When testing code that uses this class, use
 * supply a fake or mock ticker.
 * TODO(kevinb): restore the "such as" --> This allows you to simulate any valid behavior of
 * the stopwatch.
 *
 *
 * **Note:** This class is not thread-safe.
 *
 * @author Kevin Bourrillion
 */
class Stopwatch private constructor() {

    /**
     * Returns `true` if [.start] has been called on this stopwatch,
     * and [.stop] has not been called since the last call to `start()`.
     */
    var isRunning: Boolean = false
        private set
    private var elapsedNanos: Long = 0
    private var startTick: Long = 0

    /**
     * Starts the stopwatch.
     *
     * @return this `Stopwatch` instance
     * @throws IllegalStateException if the stopwatch is already running.
     */
    fun start(): Stopwatch {
        isRunning = true
        startTick = System.nanoTime()
        return this
    }

    /**
     * Stops the stopwatch. Future reads will return the fixed duration that had
     * elapsed up to this point.
     *
     * @return this `Stopwatch` instance
     * @throws IllegalStateException if the stopwatch is already stopped.
     */
    fun stop(): Stopwatch {
        val tick = System.nanoTime()
        isRunning = false
        elapsedNanos += tick - startTick
        return this
    }

    /**
     * Sets the elapsed time for this stopwatch to zero,
     * and places it in a stopped state.
     *
     * @return this `Stopwatch` instance
     */
    fun reset(): Stopwatch {
        elapsedNanos = 0
        isRunning = false
        return this
    }

    private fun elapsedNanos(): Long {
        return if (isRunning) System.nanoTime() - startTick + elapsedNanos else elapsedNanos
    }

    /**
     * Returns the current elapsed time shown on this stopwatch, expressed
     * in the desired time unit, with any fraction rounded down.
     *
     *
     * Note that the overhead of measurement can be more than a microsecond, so
     * it is generally not useful to specify [TimeUnit.NANOSECONDS]
     * precision here.
     *
     * @since 14.0 (since 10.0 as `elapsedTime()`)
     */
    fun elapsed(desiredUnit: TimeUnit): Long {
        return desiredUnit.convert(elapsedNanos(), NANOSECONDS)
    }

    /**
     * Returns a string representation of the current elapsed time.
     */
    override fun toString(): String {
        val nanos = elapsedNanos()

        val unit = chooseUnit(nanos)
        val value = nanos.toDouble() / NANOSECONDS.convert(1, unit)

        // Too bad this functionality is not exposed as a regular method call
        return String.format("%.4g %s", value, abbreviate(unit))
    }

    companion object {

        /**
         * Creates (but does not start) a new stopwatch using [System.nanoTime]
         * as its time source.
         */
        fun createUnstarted(): Stopwatch {
            return Stopwatch()
        }

        /**
         * Creates (and starts) a new stopwatch using [System.nanoTime]
         * as its time source.
         */
        fun createStarted(): Stopwatch {
            return Stopwatch().start()
        }

        private fun chooseUnit(nanos: Long): TimeUnit {
            if (DAYS.convert(nanos, NANOSECONDS) > 0) {
                return DAYS
            }
            if (HOURS.convert(nanos, NANOSECONDS) > 0) {
                return HOURS
            }
            if (MINUTES.convert(nanos, NANOSECONDS) > 0) {
                return MINUTES
            }
            if (SECONDS.convert(nanos, NANOSECONDS) > 0) {
                return SECONDS
            }
            if (MILLISECONDS.convert(nanos, NANOSECONDS) > 0) {
                return MILLISECONDS
            }
            return if (MICROSECONDS.convert(nanos, NANOSECONDS) > 0) {
                MICROSECONDS
            } else NANOSECONDS
        }

        private fun abbreviate(unit: TimeUnit): String {
            return when (unit) {
                NANOSECONDS -> "ns"
                MICROSECONDS -> "\u03bcs"
                MILLISECONDS -> "ms"
                SECONDS -> "s"
                MINUTES -> "min"
                HOURS -> "h"
                DAYS -> "d"
                else -> throw AssertionError()
            }
        }
    }
}
