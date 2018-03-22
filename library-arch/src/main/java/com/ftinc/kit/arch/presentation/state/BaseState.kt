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

import com.ftinc.kit.arch.presentation.BaseActions
import com.ftinc.kit.arch.presentation.renderers.UiBaseStateRenderer


/**
 * Base [Ui.State] implementation that contains [isLoading] and [error] fields for common uses
 *
 * @param isLoading field indicating that data is being loaded in the current view
 * @param error field indicating if an error has occurred, null otherwise
 * @see [BaseActions]
 * @see [UiBaseStateRenderer]
 * @see [Ui]
 * @see [Ui.State]
 */
abstract class BaseState<C : Ui.State.Change>(
        val isLoading: Boolean,
        val error: String?
) : Ui.State<C>