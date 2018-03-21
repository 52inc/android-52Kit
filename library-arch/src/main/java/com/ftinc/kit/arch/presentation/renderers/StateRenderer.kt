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


/**
 * Responsible for rendering a view's state.
 */
interface StateRenderer<in VS> {

    /**
     * Accepts a pojo representing the current state of the view in order to render it on to the screen of the user.
     * @param state state to render
     */
    fun render(state: VS)
}