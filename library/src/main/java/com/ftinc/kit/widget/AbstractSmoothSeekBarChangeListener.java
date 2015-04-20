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
public abstract class AbstractSmoothSeekBarChangeListener implements OnSeekBarChangeListener {
	private int smoothnessFactor = 10;

    /**
     * Default empty constructor
     */
    public AbstractSmoothSeekBarChangeListener(){}

    /**
     * Smooth Factor Constructor
     * @param smoothFactor      the smoothing factor
     */
    public AbstractSmoothSeekBarChangeListener(int smoothFactor){
        smoothnessFactor = smoothFactor;
    }

	/**
	 * Unused
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        onSmoothProgressChanged(seekBar, progress, progress / smoothnessFactor, fromUser);
    }
	
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
        onSmoothStartTrackingTouch(seekBar);
    }

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		seekBar.setProgress(Math.round((seekBar.getProgress() + (smoothnessFactor / 2)) / smoothnessFactor) * smoothnessFactor);
        onSmoothStopTrackingTouch(seekBar, seekBar.getProgress() / smoothnessFactor);
	}


    public abstract void onSmoothProgressChanged(SeekBar seekBar, int progress, int smoothProgress, boolean fromUser);
    public abstract void onSmoothStartTrackingTouch(SeekBar seekBar);
    public abstract void onSmoothStopTrackingTouch(SeekBar seekBar, int smoothProgress);

}
