/*
 * Copyright (c) 2017 52inc.
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

package com.ftinc.kit.widget

import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import kotlin.math.roundToInt

/**
 * This is an implementation of the seekbar that smooths out progress sliding
 * on seekbar tracking changes
 *
 * @author drew.heavner
 */
abstract class SmoothSeekBarChangeListener(
        private var smoothnessFactor: Int = 10
) : OnSeekBarChangeListener {

    abstract fun onSmoothProgressChanged(seekBar: SeekBar, progress: Int, smoothProgress: Int, fromUser: Boolean)
    abstract fun onSmoothStartTrackingTouch(seekBar: SeekBar)
    abstract fun onSmoothStopTrackingTouch(seekBar: SeekBar, smoothProgress: Int)

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        onSmoothProgressChanged(seekBar, progress, progress / smoothnessFactor, fromUser)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        onSmoothStartTrackingTouch(seekBar)
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        seekBar.progress = ((seekBar.progress + smoothnessFactor / 2) / smoothnessFactor)
                .toFloat().roundToInt() * smoothnessFactor
        onSmoothStopTrackingTouch(seekBar, seekBar.progress / smoothnessFactor)
    }
}
