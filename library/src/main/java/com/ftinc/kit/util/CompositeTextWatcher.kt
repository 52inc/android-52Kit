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

import android.text.Editable
import android.text.TextWatcher
import android.util.SparseArray
import android.util.SparseBooleanArray
import androidx.core.util.forEach

/**
 * A composite TextWatcher that can contain any number of TextWatchers and contains the ability
 * to enable/disable them at will
 */
class CompositeTextWatcher : TextWatcher {

    private val mWatchers = SparseArray<TextWatcher>(3)
    private val mEnabledKeys = SparseBooleanArray(3)

    /**
     * Create a new text watcher based on one or more text watchers. This will auto-generate id's
     * based on order.
     * @param watchers one or more text watchers to composite
     */
    constructor(vararg watchers: TextWatcher) : this(watchers.toList())

    /**
     * Create a new text watcher based on one or more text watchers. This will auto-generate id's
     * based on order.
     * @param watchers      one or more text watchers to composite
     */
    constructor(watchers: Iterable<TextWatcher>) {
        watchers.forEachIndexed { index, textWatcher ->
            mWatchers.put(index, textWatcher)
            mEnabledKeys.put(index, true)
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        mWatchers.forEach { id, watcher ->
            if (mEnabledKeys.get(id)) {
                watcher.beforeTextChanged(s, start, count, after)
            }
        }
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        mWatchers.forEach { id, watcher ->
            if (mEnabledKeys.get(id)) {
                watcher.onTextChanged(s, start, before, count)
            }
        }
    }

    override fun afterTextChanged(s: Editable) {
        mWatchers.forEach { id, watcher ->
            if (mEnabledKeys.get(id)) {
                watcher.afterTextChanged(s)
            }
        }
    }

    /**
     * Add a [TextWatcher] to this composite view
     * @param watcher the [TextWatcher] to add
     * @return the generated id for the new watcher
     */
    fun add(watcher: TextWatcher): Int {
        val id = mWatchers.size()
        add(id, watcher)
        return id
    }

    /**
     * Add a [TextWatcher] with a specified Id and whether or not it is enabled by default
     * @param id the id of the [TextWatcher] to add
     * @param watcher the [TextWatcher] to add
     * @param enabled whether or not it is enabled by default
     */
    @JvmOverloads
    fun add(id: Int, watcher: TextWatcher, enabled: Boolean = true) {
        mWatchers.put(id, watcher)
        mEnabledKeys.put(id, enabled)
    }

    /**
     * Enable or Disable a text watcher for the given Id
     * @param id the id of the [TextWatcher] to add
     * @param enabled whether or not to enable or disable
     */
    fun enable(id: Int, enabled: Boolean) {
        mEnabledKeys.put(id, enabled)
    }

    /**
     * Enable or Disable a text watcher by reference
     * @param watcher The [TextWatcher] to enable or disable
     * @param enabled whether or not to enable or disable
     */
    fun enable(watcher: TextWatcher, enabled: Boolean) {
        val index = mWatchers.indexOfValue(watcher)
        if (index >= 0) {
            val key = mWatchers.keyAt(index)
            mEnabledKeys.put(key, enabled)
        }
    }

    operator fun plusAssign(watcher: TextWatcher) {
        add(watcher)
    }

    operator fun set(id: Int, watcher: TextWatcher) {
        add(id, watcher)
    }

    operator fun set(id: Int, enabled: Boolean) {
        enable(id, enabled)
    }
}
