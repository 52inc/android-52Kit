package com.ftinc.kit.extensions

import java.io.PrintWriter
import java.io.StringWriter

/**
 * Print a stacktrace to string
 */
fun Throwable.printStackTraceToString(): String {
    val sw = StringWriter(256)
    val pw = PrintWriter(sw, false)
    printStackTrace(pw)
    pw.flush()
    return sw.toString()
}
