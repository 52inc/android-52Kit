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

package com.ftinc.kit.arch.presentation.delegates


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import com.ftinc.kit.arch.presentation.Stateful
import com.ftinc.kit.arch.presentation.renderers.DisposableStateRenderer


class StatefulActivityDelegate(
        private val state: Stateful,
        private val lifecycle: Lifecycle.Event = Lifecycle.Event.ON_CREATE
) : ActivityDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        tryStart(Lifecycle.Event.ON_CREATE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
    }

    override fun onResume() {
        tryStart(Lifecycle.Event.ON_RESUME)
    }

    override fun onStart() {
        tryStart(Lifecycle.Event.ON_START)
    }

    override fun onStop() {
        tryStop(Lifecycle.Event.ON_STOP)
    }

    override fun onPause() {
        tryStop(Lifecycle.Event.ON_PAUSE)
    }

    override fun onDestroy() {
        tryStop(Lifecycle.Event.ON_DESTROY)
    }


    private fun tryStart(event: Lifecycle.Event) {
        if (event == lifecycle) {
            state.start()
        }
    }

    private fun tryStop(event: Lifecycle.Event) {
        val opposite = when(lifecycle) {
            Lifecycle.Event.ON_CREATE -> Lifecycle.Event.ON_DESTROY
            Lifecycle.Event.ON_START -> Lifecycle.Event.ON_STOP
            else -> Lifecycle.Event.ON_PAUSE
        }

        if (opposite == event) {
            state.stop()
        }
    }
}