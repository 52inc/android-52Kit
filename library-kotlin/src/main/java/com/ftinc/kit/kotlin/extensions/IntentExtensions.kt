package com.ftinc.kit.kotlin.extensions


import android.content.Intent


fun Intent.clear(): Intent = this.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK)
fun Intent.top(): Intent = this.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP or android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP)
