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

package com.ftinc.kit.arch.util


import com.ftinc.kit.arch.Arch
import com.ftinc.kit.arch.presentation.state.Ui
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import timber.log.Timber
import java.util.concurrent.TimeUnit


operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    this.add(disposable)
}


fun <T, R> Observable<T>.scanMap(func2: (T?, T) -> R): Observable<R> {
    return this.startWith(null as T?) //emit a null change first, otherwise the .buffer() below won't emit at first (needs 2 emissions to emit)
            .buffer(2, 1) //buffer the previous and current emission
            .filter { it.size >= 2 } //when the buffer terminates (onCompleted/onError), the remaining buffer is emitted. When don't want those!
            .map { func2.invoke(it[0], it[1]) }
}


fun <T, R> Observable<T>.scanMap(initialValue: T, func2: (T, T) -> R): Observable<R> {
    return this.startWith(initialValue)
            .buffer(2, 1)
            .filter { it.size >= 2 }
            .map { func2.invoke(it[0], it[1]) }
}


data class Nullable<out T> constructor(val value: T?) {
    constructor() : this(null)


    fun isNull(): Boolean {
        return value == null
    }

    fun isNonNull(): Boolean {
        return !isNull()
    }

    override fun toString(): String {
        return value.toString()
    }

    companion object {
        val NULL = Nullable(null)
    }
}


fun <T : Any?> T.toNullable(): Nullable<T> {
    if (this == null) {
        return Nullable.NULL //reuse singleton
    } else {
        return Nullable(this)
    }
}


fun <T : Any, R : Any?> Observable<T>.mapNullable(func: (T) -> R?): Observable<Nullable<R?>> {
    return this.map { Nullable(func.invoke(it)) }
}


fun <T : Any> Observable<T>.uiDebounce(): Observable<T> {
    return this.debounce(300, java.util.concurrent.TimeUnit.MILLISECONDS)
}


fun <T : Any> Observable<T>.uiDebounce(delayInMilliseconds: Long): Observable<T> {
    return this.debounce(delayInMilliseconds, java.util.concurrent.TimeUnit.MILLISECONDS)
}


fun <T : Any> Observable<T>.mapError(throwable: Throwable): Observable<T> {
    return this.onErrorResumeNext { _: Throwable -> Observable.error<T>(throwable) }
}


fun <T : Any> Observable<T>.logState(): Observable<T> {
    return this.doOnNext { state ->
        if (Arch.loggingEnabled) {
            Timber.v("    --- $state")
        }
    }
}


fun <T : Ui.State.Change> Observable<T>.logChange(): Observable<T> {
    return this.doOnNext { change ->
        if (Arch.loggingEnabled) {
            Timber.d(change.logText)
        }
    }
}


fun <T: Any> Observable<T>.retryWithBackoff(numRetries: Int = 3, delayInSeconds: Int = 5): Observable<T> {
    return this.retryWhen { t ->
        t.zipWith(Observable.range(1, numRetries), BiFunction<Throwable, Int, Int> { _, i -> i} )
                .flatMap { retryCount ->
                    Observable.timer(Math.pow(delayInSeconds.toDouble(), retryCount.toDouble()).toLong(), TimeUnit.SECONDS)
                }
    }
}