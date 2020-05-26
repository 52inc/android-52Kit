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

package com.ftinc.kit.util

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi
import android.util.Size
import android.util.SizeF
import java.io.Serializable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import androidx.fragment.app.Fragment as SupportFragment


/*
 *
 * Fragment Properties
 *
 */

fun SupportFragment.bindBoolean(key: String, default: Boolean = false) : ExtraProperty<Boolean> = Extra(BundleAccessor.Fragment(this)) {
    it?.getBoolean(key, default) ?: default
}


fun SupportFragment.bindByte(key: String, default: Byte = 0) : ExtraProperty<Byte> = Extra(BundleAccessor.Fragment(this)) {
    it?.getByte(key, default) ?: default
}


fun SupportFragment.bindChar(key: String, default: Char = 0.toChar()) : ExtraProperty<Char> = Extra(BundleAccessor.Fragment(this)) {
    it?.getChar(key, default) ?: default
}


fun SupportFragment.bindShort(key: String, default: Short = 0) : ExtraProperty<Short> = Extra(BundleAccessor.Fragment(this)) {
    it?.getShort(key, default) ?: default
}


fun SupportFragment.bindInt(key: String, default: Int = 0) : ExtraProperty<Int> = Extra(BundleAccessor.Fragment(this)) {
    it?.getInt(key, default) ?: default
}


fun SupportFragment.bindLong(key: String, default: Long = 0) : ExtraProperty<Long> = Extra(BundleAccessor.Fragment(this)) {
    it?.getLong(key, default) ?: default
}


fun SupportFragment.bindFloat(key: String, default: Float = 0f) : ExtraProperty<Float> = Extra(BundleAccessor.Fragment(this)) {
    it?.getFloat(key, default) ?: default
}


fun SupportFragment.bindDouble(key: String, default: Double = 0.0) : ExtraProperty<Double> = Extra(BundleAccessor.Fragment(this)) {
    it?.getDouble(key, default) ?: default
}


fun SupportFragment.bindString(key: String?, default: String = "") : ExtraProperty<String> = Extra(BundleAccessor.Fragment(this)) {
    it?.getString(key, default) ?: default
}


fun SupportFragment.bindOptionalString(key: String?, default: String? = null) : ExtraProperty<String?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getString(key, default)
}


fun SupportFragment.bindCharSequence(key: String?, default: CharSequence = "") : ExtraProperty<CharSequence> = Extra(BundleAccessor.Fragment(this)) {
    it?.getCharSequence(key, default) ?: default
}


fun SupportFragment.bindOptionalCharSequence(key: String?, default: CharSequence? = null) : ExtraProperty<CharSequence?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getCharSequence(key, default)
}


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun SupportFragment.bindSize(key: String?, default: Size? = null) : ExtraProperty<Size?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getSize(key) ?: default
}


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun SupportFragment.bindSizeF(key: String?, default: SizeF? = null) : ExtraProperty<SizeF?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getSizeF(key) ?: default
}

fun <T : Parcelable> SupportFragment.bindParcelable(key: String) : ExtraProperty<T> = Extra(BundleAccessor.Fragment(this)) {
    it!!.getParcelable<T>(key)!!
}

fun <T : Parcelable> SupportFragment.bindOptionalParcelable(key: String) : ExtraProperty<T?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getParcelable<T>(key)
}

fun <T : Parcelable> SupportFragment.bindParcelableList(key: String) : ExtraProperty<List<T>> = Extra(BundleAccessor.Fragment(this)) {
    it!!.getParcelableArrayList<T>(key)!!.toList()
}

fun <T : Parcelable> SupportFragment.bindOptionalParcelableList(key: String) : ExtraProperty<List<T>?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getParcelableArrayList<T>(key)?.toList()
}

@Suppress("UNCHECKED_CAST")
fun <T : Serializable> SupportFragment.bindSerializable(key: String?) : ExtraProperty<T?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getSerializable(key)?.let { it as T }
}

/**
 * @see [BundleBuilder.enum]
 */
inline fun <reified E : Enum<E>> SupportFragment.bindEnum(key: String) : ExtraProperty<E> = Extra(BundleAccessor.Fragment(this)) {
    val name = it?.getString(key)
    java.lang.Enum.valueOf(E::class.java, name)
}


/**
 * @see [BundleBuilder.enum]
 */
inline fun <reified E : Enum<E>> SupportFragment.bindOptionalEnum(key: String) : ExtraProperty<E?> = Extra(BundleAccessor.Fragment(this)) {
    val name = it?.getString(key)
    try {
        java.lang.Enum.valueOf(E::class.java, name)
    } catch (e: Exception) {
        null
    }
}


fun SupportFragment.bindIntArrayList(key: String?) : ExtraProperty<ArrayList<Int>?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getIntegerArrayList(key)
}


fun SupportFragment.bindStringArrayList(key: String?) : ExtraProperty<ArrayList<String>?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getStringArrayList(key)
}


fun SupportFragment.bindCharSequenceArrayList(key: String?) : ExtraProperty<ArrayList<CharSequence>?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getCharSequenceArrayList(key)
}


fun SupportFragment.bindBooleanArray(key: String?) : ExtraProperty<BooleanArray?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getBooleanArray(key)
}


fun SupportFragment.bindByteArray(key: String?) : ExtraProperty<ByteArray?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getByteArray(key)
}


fun SupportFragment.bindShortArray(key: String?) : ExtraProperty<ShortArray?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getShortArray(key)
}


fun SupportFragment.bindCharArray(key: String?) : ExtraProperty<CharArray?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getCharArray(key)
}


fun SupportFragment.bindIntArray(key: String?) : ExtraProperty<IntArray?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getIntArray(key)
}


fun SupportFragment.bindLongArray(key: String?) : ExtraProperty<LongArray?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getLongArray(key)
}


fun SupportFragment.bindFloatArray(key: String?) : ExtraProperty<FloatArray?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getFloatArray(key)
}


fun SupportFragment.bindDoubleArray(key: String?) : ExtraProperty<DoubleArray?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getDoubleArray(key)
}


fun SupportFragment.bindStringArray(key: String?) : ExtraProperty<Array<String>?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getStringArray(key)
}


fun SupportFragment.bindCharSequenceArray(key: String?) : ExtraProperty<Array<CharSequence>?> = Extra(BundleAccessor.Fragment(this)) {
    it?.getCharSequenceArray(key)
}


/*
 *
 * Activity Properties
 *
 */

fun Activity.bindBoolean(key: String, default: Boolean = false) : ExtraProperty<Boolean> = Extra(BundleAccessor.Activity(this)) {
    it?.getBoolean(key, default) ?: default
}


fun Activity.bindByte(key: String, default: Byte = 0) : ExtraProperty<Byte> = Extra(BundleAccessor.Activity(this)) {
    it?.getByte(key, default) ?: default
}


fun Activity.bindChar(key: String, default: Char = 0.toChar()) : ExtraProperty<Char> = Extra(BundleAccessor.Activity(this)) {
    it?.getChar(key, default) ?: default
}


fun Activity.bindShort(key: String, default: Short = 0) : ExtraProperty<Short> = Extra(BundleAccessor.Activity(this)) {
    it?.getShort(key, default) ?: default
}


fun Activity.bindInt(key: String, default: Int = 0) : ExtraProperty<Int> = Extra(BundleAccessor.Activity(this)) {
    it?.getInt(key, default) ?: default
}


fun Activity.bindLong(key: String, default: Long = 0) : ExtraProperty<Long> = Extra(BundleAccessor.Activity(this)) {
    it?.getLong(key, default) ?: default
}


fun Activity.bindFloat(key: String, default: Float = 0f) : ExtraProperty<Float> = Extra(BundleAccessor.Activity(this)) {
    it?.getFloat(key, default) ?: default
}


fun Activity.bindDouble(key: String, default: Double = 0.0) : ExtraProperty<Double> = Extra(BundleAccessor.Activity(this)) {
    it?.getDouble(key, default) ?: default
}


fun Activity.bindString(key: String?, default: String = "") : ExtraProperty<String> = Extra(BundleAccessor.Activity(this)) {
    it?.getString(key, default) ?: default
}


fun Activity.bindOptionalString(key: String?, default: String? = null) : ExtraProperty<String?> = Extra(BundleAccessor.Activity(this)) {
    it?.getString(key, default)
}


fun Activity.bindCharSequence(key: String?, default: CharSequence = "") : ExtraProperty<CharSequence> = Extra(BundleAccessor.Activity(this)) {
    it?.getCharSequence(key, default) ?: default
}


fun Activity.bindOptionalCharSequence(key: String?, default: CharSequence? = null) : ExtraProperty<CharSequence?> = Extra(BundleAccessor.Activity(this)) {
    it?.getCharSequence(key, default)
}


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.bindSize(key: String?, default: Size? = null) : ExtraProperty<Size?> = Extra(BundleAccessor.Activity(this)) {
    it?.getSize(key) ?: default
}


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.bindSizeF(key: String?, default: SizeF? = null) : ExtraProperty<SizeF?> = Extra(BundleAccessor.Activity(this)) {
    it?.getSizeF(key) ?: default
}

fun <T : Parcelable> Activity.bindParcelable(key: String) : ExtraProperty<T> = Extra(BundleAccessor.Activity(this)) {
    it!!.getParcelable<T>(key)!!
}

fun <T : Parcelable> Activity.bindOptionalParcelable(key: String) : ExtraProperty<T?> = Extra(BundleAccessor.Activity(this)) {
    it?.getParcelable<T>(key)
}

fun <T : Parcelable> Activity.bindParcelableList(key: String) : ExtraProperty<List<T>> = Extra(BundleAccessor.Activity(this)) {
    it!!.getParcelableArrayList<T>(key)!!.toList()
}

fun <T : Parcelable> Activity.bindOptionalParcelableList(key: String) : ExtraProperty<List<T>?> = Extra(BundleAccessor.Activity(this)) {
    it?.getParcelableArrayList<T>(key)?.toList()
}

@Suppress("UNCHECKED_CAST")
fun <T : Serializable> Activity.bindSerializable(key: String?) : ExtraProperty<T?> = Extra(BundleAccessor.Activity(this)) {
    it?.getSerializable(key)?.let { it as T }
}

/**
 * @see [BundleBuilder.enum]
 */
inline fun <reified E : Enum<E>> Activity.bindEnum(key: String) : ExtraProperty<E> = Extra(BundleAccessor.Activity(this)) {
    val name = it?.getString(key)
    java.lang.Enum.valueOf(E::class.java, name)
}

/**
 * @see [BundleBuilder.enum]
 */
inline fun <reified E : Enum<E>> Activity.bindOptionalEnum(key: String) : ExtraProperty<E?> = Extra(BundleAccessor.Activity(this)) {
    val name = it?.getString(key)
    try {
        java.lang.Enum.valueOf(E::class.java, name)
    } catch (e: Exception) {
        null
    }
}


fun Activity.bindIntArrayList(key: String?) : ExtraProperty<ArrayList<Int>?> = Extra(BundleAccessor.Activity(this)) {
    it?.getIntegerArrayList(key)
}


fun Activity.bindStringArrayList(key: String?) : ExtraProperty<ArrayList<String>?> = Extra(BundleAccessor.Activity(this)) {
    it?.getStringArrayList(key)
}


fun Activity.bindCharSequenceArrayList(key: String?) : ExtraProperty<ArrayList<CharSequence>?> = Extra(BundleAccessor.Activity(this)) {
    it?.getCharSequenceArrayList(key)
}


fun Activity.bindBooleanArray(key: String?) : ExtraProperty<BooleanArray?> = Extra(BundleAccessor.Activity(this)) {
    it?.getBooleanArray(key)
}


fun Activity.bindByteArray(key: String?) : ExtraProperty<ByteArray?> = Extra(BundleAccessor.Activity(this)) {
    it?.getByteArray(key)
}


fun Activity.bindShortArray(key: String?) : ExtraProperty<ShortArray?> = Extra(BundleAccessor.Activity(this)) {
    it?.getShortArray(key)
}


fun Activity.bindCharArray(key: String?) : ExtraProperty<CharArray?> = Extra(BundleAccessor.Activity(this)) {
    it?.getCharArray(key)
}


fun Activity.bindIntArray(key: String?) : ExtraProperty<IntArray?> = Extra(BundleAccessor.Activity(this)) {
    it?.getIntArray(key)
}


fun Activity.bindLongArray(key: String?) : ExtraProperty<LongArray?> = Extra(BundleAccessor.Activity(this)) {
    it?.getLongArray(key)
}


fun Activity.bindFloatArray(key: String?) : ExtraProperty<FloatArray?> = Extra(BundleAccessor.Activity(this)) {
    it?.getFloatArray(key)
}


fun Activity.bindDoubleArray(key: String?) : ExtraProperty<DoubleArray?> = Extra(BundleAccessor.Activity(this)) {
    it?.getDoubleArray(key)
}


fun Activity.bindStringArray(key: String?) : ExtraProperty<Array<String>?> = Extra(BundleAccessor.Activity(this)) {
    it?.getStringArray(key)
}


fun Activity.bindCharSequenceArray(key: String?) : ExtraProperty<Array<CharSequence>?> = Extra(BundleAccessor.Activity(this)) {
    it?.getCharSequenceArray(key)
}


sealed class BundleAccessor {

    abstract val bundle: Bundle?


    class Activity(private val activity: android.app.Activity) : BundleAccessor() {

        override val bundle: Bundle?
            get() = activity.intent?.extras
    }


    class Fragment(private val fragment: androidx.fragment.app.Fragment) : BundleAccessor() {

        override val bundle: Bundle?
            get() = fragment.arguments
    }
}


interface ExtraProperty<out T> : ReadOnlyProperty<Any, T>


class Extra<out V>(private val accessor: BundleAccessor, private val initializer: (Bundle?) -> V) : ExtraProperty<V> {
    private object EMPTY
    private var value: Any? = EMPTY

    override fun getValue(thisRef: Any, property: KProperty<*>): V {
        if (value == EMPTY) {
            value = initializer(accessor.bundle)
        }
        @Suppress("UNCHECKED_CAST")
        return value as V
    }
}
