/*
 * Copyright (c) 2019 52inc.
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


import android.content.Intent
import com.ftinc.kit.kotlin.utils.Bundler


/**
 * Add the [Intent.FLAG_ACTIVITY_NEW_TASK] and [Intent.FLAG_ACTIVITY_CLEAR_TASK] flags to an intent
 */
fun Intent.clear(): Intent = this.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)


/**
 * Add the [Intent.FLAG_ACTIVITY_CLEAR_TOP] and [Intent.FLAG_ACTIVITY_SINGLE_TOP] flags to an intent
 */
fun Intent.top(): Intent = this.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)


/**
 * Generate a Data based Intent for returning in [Context.startActivityForResult(intent, requestCode)]
 */
fun dataIntent(init: Bundler.() -> Unit): Intent {
    val intent = Intent()
    val bundler = Bundler()
    bundler.init()
    intent.putExtras(bundler.build())
    return intent
}