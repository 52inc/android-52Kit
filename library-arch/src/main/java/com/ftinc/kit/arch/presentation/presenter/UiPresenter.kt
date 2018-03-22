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

package com.ftinc.kit.arch.presentation.presenter


import com.ftinc.kit.arch.presentation.state.Ui
import com.ftinc.kit.arch.util.logChange
import com.ftinc.kit.arch.util.logState
import com.ftinc.kit.arch.util.plusAssign
import io.reactivex.Observable


abstract class UiPresenter<VS : Ui.State<C>, C : Ui.State.Change>(
        val ui: Ui<VS, C>
) : Presenter() {

    @Suppress("UNCHECKED_CAST")
    override fun start() {
        val merged = smashObservables()
                .logChange()

        disposables += merged.scan(ui.state, { state, change -> state.reduce(change) as VS })
                .logState()
                .subscribe(ui::render)
    }


    abstract fun smashObservables(): Observable<C>
}