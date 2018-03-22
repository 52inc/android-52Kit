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

package com.ftinc.kit.arch.presentation.state


import com.ftinc.kit.arch.presentation.renderers.StateRenderer

/**
 * The 'View'
 */
interface Ui<VS : Ui.State<C>, C : Ui.State.Change> : StateRenderer<VS> {

    /**
     * The view state for this UI
     */
    val state: VS


    /**
     * The interface for a view state in the MVI structure
     */
    interface State<C : State.Change> {

        /**
         * Reduce this state with a given change
         */
        fun reduce(change: C): State<C>


        /**
         * Model to dictact how the state can change and reduce
         */
        abstract class Change(val logText: String)
    }
}
