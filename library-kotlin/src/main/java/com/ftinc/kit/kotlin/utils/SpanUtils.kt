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

package com.ftinc.kit.kotlin.utils


import android.text.SpannableString


class Span(val text: String,
           val span: Any?)


class Spanned {
    val items = arrayListOf<Span>()

    fun span(text: String, span: Any?) = items.add(Span(text, span))
    fun span(text: String) = items.add(Span(text, null))

    fun build(): SpannableString {
        val string: String = items.fold("", {
            spanText, span ->
            "$spanText${span.text} "
        })

        val s: SpannableString = SpannableString(string.trim())
        var len: Int = 0

        items.forEach {
            if(it.span != null){
                s.setSpan(it.span, len, len+it.text.length, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            len += it.text.length+1
        }

        return s
    }
}


fun spannable(init: Spanned.() -> Unit): Spanned {
    val text = Spanned()
    text.init()
    return text
}
