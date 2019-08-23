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

package com.ftinc.kit.extensions


import android.app.Activity
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import android.view.View

internal fun Activity.find(@IdRes resId: Int) = this.findViewById<View>(resId)

fun Activity.snackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) = snackbar(find(android.R.id.content), message, duration)
fun Activity.snackbar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT) = snackbar(find(android.R.id.content), message, duration)
fun Activity.snackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) = Snackbar.make(view, message, duration).show()
fun Activity.snackbar(view: View, @StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT) = Snackbar.make(view, message, duration).show()

fun Fragment.snackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) = view?.let { snackbar(it, message, duration) }
fun Fragment.snackbar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT) = view?.let { snackbar(it, message, duration) }
fun Fragment.snackbar(view: View, message: String) = snackbar(view, message, Snackbar.LENGTH_SHORT)
fun Fragment.snackbar(view: View, @StringRes message: Int) = snackbar(view, message, Snackbar.LENGTH_SHORT)
fun Fragment.snackbar(view: View, message: String, duration: Int) = com.google.android.material.snackbar.Snackbar.make(view, message, duration).show()
fun Fragment.snackbar(view: View, @StringRes message: Int, duration: Int) = com.google.android.material.snackbar.Snackbar.make(view, message, duration).show()
