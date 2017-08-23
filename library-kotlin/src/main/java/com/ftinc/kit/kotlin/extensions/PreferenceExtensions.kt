package com.ftinc.kit.kotlin.extensions


import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


interface Preferences {

    val sharedPreferences: SharedPreferences


    abstract class Preference<T : Any?>(val key: String) : ReadWriteProperty<Preferences, T>


    class StringPreference(key: String, val default: String? = null) : Preference<String?>(key) {

        override fun getValue(thisRef: Preferences, property: KProperty<*>): String? {
            return thisRef.sharedPreferences.getString(key, default)
        }


        override fun setValue(thisRef: Preferences, property: KProperty<*>, value: String?) {
            thisRef.sharedPreferences.edit().putString(key, value).apply()
        }
    }


    class IntPreference(key: String, val default: Int = 0) : Preference<Int>(key) {

        override fun getValue(thisRef: Preferences, property: KProperty<*>): Int {
            return thisRef.sharedPreferences.getInt(key, default)
        }


        override fun setValue(thisRef: Preferences, property: KProperty<*>, value: Int) {
            thisRef.sharedPreferences.edit().putInt(key, value).apply()
        }
    }


    class LongPreference(key: String, val default: Long = 0) : Preference<Long>(key) {

        override fun getValue(thisRef: Preferences, property: KProperty<*>): Long {
            return thisRef.sharedPreferences.getLong(key, default)
        }


        override fun setValue(thisRef: Preferences, property: KProperty<*>, value: Long) {
            thisRef.sharedPreferences.edit().putLong(key, value).apply()
        }
    }


    class BooleanPreference(key: String, val default: Boolean = false) : Preference<Boolean>(key) {

        override fun getValue(thisRef: Preferences, property: KProperty<*>): Boolean {
            return thisRef.sharedPreferences.getBoolean(key, default)
        }


        override fun setValue(thisRef: Preferences, property: KProperty<*>, value: Boolean) {
            thisRef.sharedPreferences.edit().putBoolean(key, value).apply()
        }
    }


    class StringSetPreference(key: String, val default: Set<String> = HashSet()) : Preference<Set<String>>(key) {

        override fun getValue(thisRef: Preferences, property: KProperty<*>): Set<String> {
            return thisRef.sharedPreferences.getStringSet(key, default)
        }


        override fun setValue(thisRef: Preferences, property: KProperty<*>, value: Set<String>) {
            thisRef.sharedPreferences.edit().putStringSet(key, value).apply()
        }
    }
}