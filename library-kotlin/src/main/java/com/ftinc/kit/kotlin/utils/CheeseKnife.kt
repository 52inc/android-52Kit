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

package com.ftinc.kit.kotlin.utils


import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Size
import android.util.SizeF
import java.io.Serializable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import android.support.v4.app.Fragment as SupportFragment


fun bindBoolean(key: String, default: Boolean = false) : BundleProperty<Boolean> = Argument {
    it?.getBoolean(key, default) ?: default
}


fun bindByte(key: String, default: Byte = 0) : BundleProperty<Byte> = Argument {
    it?.getByte(key, default) ?: default
}


fun bindChar(key: String, default: Char = 0.toChar()) : BundleProperty<Char> = Argument {
    it?.getChar(key, default) ?: default
}


fun bindShort(key: String, default: Short = 0) : BundleProperty<Short> = Argument {
    it?.getShort(key, default) ?: default
}


fun bindInt(key: String, default: Int = 0) : BundleProperty<Int> = Argument {
    it?.getInt(key, default) ?: default
}


fun bindLong(key: String, default: Long = 0) : BundleProperty<Long> = Argument {
    it?.getLong(key, default) ?: default
}


fun bindFloat(key: String, default: Float = 0f) : BundleProperty<Float> = Argument {
    it?.getFloat(key, default) ?: default
}


fun bindDouble(key: String, default: Double = 0.0) : BundleProperty<Double> = Argument {
    it?.getDouble(key, default) ?: default
}


fun bindString(key: String?, default: String = "") : BundleProperty<String> = Argument {
    it?.getString(key, default) ?: default
}


fun bindOptionalString(key: String?, default: String? = null) : BundleProperty<String?> = Argument {
    it?.getString(key, default)
}


fun bindCharSequence(key: String?, default: CharSequence = "") : BundleProperty<CharSequence> = Argument {
    it?.getCharSequence(key, default) ?: default
}


fun bindOptionalCharSequence(key: String?, default: CharSequence? = null) : BundleProperty<CharSequence?> = Argument {
    it?.getCharSequence(key, default)
}


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun bindSize(key: String?, default: Size? = null) : BundleProperty<Size?> = Argument {
    it?.getSize(key) ?: default
}


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun bindSizeF(key: String?, default: SizeF? = null) : BundleProperty<SizeF?> = Argument {
    it?.getSizeF(key) ?: default
}


@Suppress("UNCHECKED_CAST")
fun <T : Serializable> bindSerializable(key: String?) : BundleProperty<T?> = Argument {
    it?.getSerializable(key)?.let { it as T }
}

/**
 * @see [Bundler.enum]
 */
inline fun <reified E : Enum<E>> bindEnum(key: String) : BundleProperty<E> = Argument {
    val name = it?.getString(key)
    java.lang.Enum.valueOf(E::class.java, name)
}


fun bindIntArrayList(key: String?) : BundleProperty<ArrayList<Int>?> = Argument {
    it?.getIntegerArrayList(key)
}


fun bindStringArrayList(key: String?) : BundleProperty<ArrayList<String>?> = Argument {
    it?.getStringArrayList(key)
}


fun bindCharSequenceArrayList(key: String?) : BundleProperty<ArrayList<CharSequence>?> = Argument {
    it?.getCharSequenceArrayList(key)
}


fun bindBooleanArray(key: String?) : BundleProperty<BooleanArray?> = Argument {
    it?.getBooleanArray(key)
}


fun bindByteArray(key: String?) : BundleProperty<ByteArray?> = Argument {
    it?.getByteArray(key)
}


fun bindShortArray(key: String?) : BundleProperty<ShortArray?> = Argument {
    it?.getShortArray(key)
}


fun bindCharArray(key: String?) : BundleProperty<CharArray?> = Argument {
    it?.getCharArray(key)
}


fun bindIntArray(key: String?) : BundleProperty<IntArray?> = Argument {
    it?.getIntArray(key)
}


fun bindLongArray(key: String?) : BundleProperty<LongArray?> = Argument {
    it?.getLongArray(key)
}


fun bindFloatArray(key: String?) : BundleProperty<FloatArray?> = Argument {
    it?.getFloatArray(key)
}


fun bindDoubleArray(key: String?) : BundleProperty<DoubleArray?> = Argument {
    it?.getDoubleArray(key)
}


fun bindStringArray(key: String?) : BundleProperty<Array<String>?> = Argument {
    it?.getStringArray(key)
}


fun bindCharSequenceArray(key: String?) : BundleProperty<Array<CharSequence>?> = Argument {
    it?.getCharSequenceArray(key)
}


interface BundleProperty<out T> : ReadOnlyProperty<SupportFragment, T>


class Argument<out V>(private val initializer: (Bundle?) -> V) : BundleProperty<V> {
    private object EMPTY
    private var value: Any? = EMPTY

    override fun getValue(thisRef: android.support.v4.app.Fragment, property: KProperty<*>): V {
        if (value == EMPTY) {
            value = initializer(thisRef.arguments)
        }
        @Suppress("UNCHECKED_CAST")
        return value as V
    }
}
