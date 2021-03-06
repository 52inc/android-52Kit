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

package com.ftinc.kit.arch.presentation

/**
 * An interface to define components that can easily plug into the base Activity/Fragment
 * delegate system using [com.ftinc.kit.arch.presentation.delegates.StatefulActivityDelegate] or
 * [com.ftinc.kit.arch.presentation.delegates.StatefulFragmentDelegate]
 */
interface Stateful {

    fun start()
    fun stop()
}