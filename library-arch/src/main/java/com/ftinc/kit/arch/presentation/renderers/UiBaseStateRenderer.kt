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

package com.ftinc.kit.arch.presentation.renderers


import com.ftinc.kit.arch.presentation.BaseActions
import com.ftinc.kit.arch.presentation.state.BaseState
import com.ftinc.kit.arch.presentation.state.Ui
import com.ftinc.kit.arch.util.logChange
import com.ftinc.kit.arch.util.logState
import com.ftinc.kit.arch.util.mapNullable
import com.ftinc.kit.arch.util.plusAssign
import io.reactivex.Observable
import io.reactivex.Scheduler


/**
 * [BaseState] Ui Renderer
 *
 * @see [BaseState]
 * @see [BaseActions]
 */
abstract class UiBaseStateRenderer<VS : BaseState<C>, C : Ui.State.Change, out A : BaseActions>(
        val actions: A,
        main: Scheduler,
        comp: Scheduler
) : DisposableStateRenderer<VS>(main, comp) {

    override fun start() {

        disposables += state
                .map { it.isLoading }
                .distinctUntilChanged()
                .addToLifecycle()
                .subscribe { actions.showLoading(it) }


        disposables += state
                .mapNullable { it.error }
                .distinctUntilChanged()
                .addToLifecycle()
                .subscribe {
                    if (it.value != null) {
                        actions.showError(it.value)
                    } else {
                        actions.hideError()
                    }
                }

        onStart()
    }


    abstract fun onStart()
}