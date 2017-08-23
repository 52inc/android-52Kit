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

package com.ftinc.kit.drawer.model;


import com.f2prateek.rx.preferences.Preference;


public class BooleanPreferenceImpl implements IPreference<Boolean> {

    private Preference<Boolean> preference;

    public BooleanPreferenceImpl(Preference<Boolean> preference){
        this.preference = preference;
    }

    @Override
    public void set(Boolean value) {
        preference.set(value);
    }

    @Override
    public Boolean get() {
        return preference.get();
    }
}
