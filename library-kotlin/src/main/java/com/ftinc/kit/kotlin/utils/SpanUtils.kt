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
