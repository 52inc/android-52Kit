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

package com.ftinc.kit.arch.presentation.renderers


import com.ftinc.kit.arch.presentation.Stateful
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable


abstract class DisposableStateRenderer<T>(
        val main: Scheduler,
        val comp: Scheduler
) : StateRenderer<T>, Stateful {

    protected val disposables = CompositeDisposable()
    protected val state: Relay<T> = PublishRelay.create<T>().toSerialized()

    override fun stop() {
        disposables.clear()
    }

    override fun render(state: T) {
        this.state.accept(state)
    }

    protected fun <T> Observable<T>.addToLifecycle(): Observable<T> {
        return this.subscribeOn(comp)
                .observeOn(main)
    }
}
