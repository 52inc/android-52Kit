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

package com.ftinc.kit.arch.presentation.delegates


import android.os.Bundle
import com.ftinc.kit.arch.presentation.presenter.Presenter


class PresenterActivityDelegate(val presenter: Presenter) : ActivityDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
    }

    override fun onResume() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onPause() {
    }

    override fun onDestroy() {
        presenter.stop()
    }

}