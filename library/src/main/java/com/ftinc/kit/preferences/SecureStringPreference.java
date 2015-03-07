/*
 * Copyright (c) 2015 52inc
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
 */

package com.ftinc.kit.preferences;

public class SecureStringPreference {
  private final SecurePreferences preferences;
  private final String key;
  private final String defaultValue;

  public SecureStringPreference(SecurePreferences preferences, String key) {
    this(preferences, key, null);
  }

  public SecureStringPreference(SecurePreferences preferences, String key, String defaultValue) {
    this.preferences = preferences;
    this.key = key;
    this.defaultValue = defaultValue;
  }

  public String get() {
      String value = preferences.getString(key);
      if(value == null) return defaultValue;
      return value;
  }

  public boolean isSet() {
    return preferences.containsKey(key);
  }

  public void set(String value) {
    preferences.putApply(key, value);
  }

  public void delete() {
    preferences.removeValue(key);
  }
}
