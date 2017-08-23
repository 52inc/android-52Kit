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

package com.ftinc.kit.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import java.util.List;

/**
 * A composite TextWatcher that can contain any number of TextWatchers and contains the ability
 * to enable/disable them at will
 */
public class CompositeTextWatcher implements TextWatcher {

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private SparseArray<TextWatcher> mWatchers = new SparseArray<>(3);
    private SparseBooleanArray mEnabledKeys = new SparseBooleanArray(3);

    /***********************************************************************************************
     *
     * Constructors
     *
     */

    /**
     * Create a new text watcher based on one or more text watchers. This will auto-generate id's based
     * on order.
     *
     * @param watchers      one or more text watchers to composite
     */
    public CompositeTextWatcher(TextWatcher... watchers){
        for (int i = 0; i < watchers.length; i++) {
            mWatchers.put(i, watchers[i]);
            mEnabledKeys.put(i, true);
        }
    }

    /**
     * Create a new text watcher based on one or more text watchers. This will auto-generate id's based
     * on order.
     *
     * @param watchers      one or more text watchers to composite
     */
    public CompositeTextWatcher(List<TextWatcher> watchers){
        for (int i = 0; i < watchers.size(); i++) {
            mWatchers.put(i, watchers.get(i));
            mEnabledKeys.put(i, true);
        }
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    /**
     * Add a {@link TextWatcher} to this composite view
     *
     * @param watcher       the {@link TextWatcher} to add
     * @return              the generated id for the new watcher
     */
    public int addWatcher(TextWatcher watcher){
        int id = mWatchers.size();
        addWatcher(id, watcher);
        return id;
    }

    /**
     * Add a text watcher with the specifying id
     *
     * @param id            the id of the {@link TextWatcher} to add
     * @param watcher       the {@link TextWatcher} to add
     */
    public void addWatcher(int id, TextWatcher watcher){
        addWatcher(id, watcher, true);
    }

    /**
     * Add a {@link TextWatcher} with a specified Id and whether or not it is enabled by default
     *
     * @param id            the id of the {@link TextWatcher} to add
     * @param watcher       the {@link TextWatcher} to add
     * @param enabled       whether or not it is enabled by default
     */
    public void addWatcher(int id, TextWatcher watcher, boolean enabled){
        mWatchers.put(id, watcher);
        mEnabledKeys.put(id, enabled);
    }

    /**
     * Enable or Disable a text watcher for the given Id
     *
     * @param id            the id of the {@link TextWatcher} to add
     * @param enabled       whether or not to enable or disable
     */
    public void enableWatcher(int id, boolean enabled){
        mEnabledKeys.put(id, enabled);
    }

    /**
     * Enable or Disable a text watcher by reference
     *
     * @param watcher       The {@link TextWatcher} to enable or disable
     * @param enabled       whether or not to enable or disable
     */
    public void enableWatcher(TextWatcher watcher, boolean enabled){
        int index = mWatchers.indexOfValue(watcher);
        if(index >= 0){
            int key = mWatchers.keyAt(index);
            mEnabledKeys.put(key, enabled);
        }
    }

    /***********************************************************************************************
     *
     * TextWatcher Methods
     *
     */

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        for (int i = 0; i < mWatchers.size(); i++) {
            int key = mWatchers.keyAt(i);
            if(mEnabledKeys.get(key)) {
                mWatchers.valueAt(i).beforeTextChanged(s, start, count, after);
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        for (int i = 0; i < mWatchers.size(); i++) {
            int key = mWatchers.keyAt(i);
            if(mEnabledKeys.get(key)) {
                mWatchers.valueAt(i).onTextChanged(s, start, before, count);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        for (int i = 0; i < mWatchers.size(); i++) {
            int key = mWatchers.keyAt(i);
            if(mEnabledKeys.get(key)) {
                mWatchers.valueAt(i).afterTextChanged(s);
            }
        }
    }
}
