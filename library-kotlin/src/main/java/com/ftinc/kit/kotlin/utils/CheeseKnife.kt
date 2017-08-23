package com.ftinc.kit.kotlin.utils

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import android.support.v4.app.Fragment as SupportFragment


fun bindArgument(key: String) : ReadOnlyProperty<SupportFragment, Int> =
        Lazy { fragment: SupportFragment, _ -> fragment.arguments.getInt(key) }


private class Lazy<in T, out V>(private val initializer: (T, KProperty<*>) -> V) : ReadOnlyProperty<T, V> {
    private object EMPTY
    private var value: Any? = EMPTY

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (value == EMPTY) {
            value = initializer(thisRef, property)
        }
        @Suppress("UNCHECKED_CAST")
        return value as V
    }
}
