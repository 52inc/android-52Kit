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

package com.ftinc.kit.widget;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * This is an implementation of the seekbar that smooths out progress sliding
 * on seekbar tracking changes
 * 
 * @author drew.heavner
 *
 */
public class SmoothSeekBarChangeListener implements OnSeekBarChangeListener {
	private int smoothnessFactor = 10;

    /**
     * Default empty constructor
     */
    public SmoothSeekBarChangeListener(){}

    /**
     * Smooth Factor Constructor
     * @param smoothFactor      the smoothing factor
     */
    public SmoothSeekBarChangeListener(int smoothFactor){
        smoothnessFactor = smoothFactor;
    }

	/**
	 * Unused
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
	
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		seekBar.setProgress(Math.round((seekBar.getProgress() + (smoothnessFactor / 2)) / smoothnessFactor) * smoothnessFactor);
	}
}
