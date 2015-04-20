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
