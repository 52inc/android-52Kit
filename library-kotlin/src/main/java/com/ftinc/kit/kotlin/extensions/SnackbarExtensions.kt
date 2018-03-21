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


import android.app.Activity
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v14.preference.PreferenceFragment
import android.support.v4.app.Fragment
import android.view.View


fun Activity.find(@IdRes resId: Int) = this.findViewById<View>(resId)

fun Activity.snackbar(message: String) = snackbar(find(android.R.id.content), message)

fun Activity.snackbar(@StringRes message: Int) = snackbar(find(android.R.id.content), message)

fun Activity.snackbar(view: View, message: String) = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()

fun Activity.snackbar(view: View, @StringRes message: Int) = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()

fun Fragment.snackbar(message: String) = view?.let { snackbar(it, message) }

fun Fragment.snackbar(@StringRes message: Int) = view?.let { snackbar(it, message) }

fun Fragment.snackbar(view: View, message: String) = snackbar(view, message, Snackbar.LENGTH_SHORT)

fun Fragment.snackbar(view: View, @StringRes message: Int) = snackbar(view, message, Snackbar.LENGTH_SHORT)

fun Fragment.snackbar(view: View, message: String, duration: Int) = Snackbar.make(view, message, duration).show()

fun Fragment.snackbar(view: View, @StringRes message: Int, duration: Int) = Snackbar.make(view, message, duration).show()

fun PreferenceFragment.snackbar(message: String) = view?.let { snackbar(it, message) }

fun PreferenceFragment.snackbar(@StringRes message: Int) = view?.let { snackbar(it, message) }

fun PreferenceFragment.snackbar(message: String, duration: Int) = view?.let { snackbar(it, message, duration) }

fun PreferenceFragment.snackbar(@StringRes message: Int, duration: Int) = view?.let { snackbar(it, message, duration) }

fun PreferenceFragment.snackbar(view: View, message: String) = snackbar(view, message, Snackbar.LENGTH_SHORT)

fun PreferenceFragment.snackbar(view: View, @StringRes message: Int) = snackbar(view, message, Snackbar.LENGTH_SHORT)

fun PreferenceFragment.snackbar(view: View, message: String, duration: Int) = Snackbar.make(view, message, duration).show()

fun PreferenceFragment.snackbar(view: View, @StringRes message: Int, duration: Int) = Snackbar.make(view, message, duration).show()