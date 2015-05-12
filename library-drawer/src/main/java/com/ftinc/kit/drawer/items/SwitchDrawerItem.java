/*
 * Copyright © 52inc 2015.
 * All rights reserved.
 */

package com.ftinc.kit.drawer.items;

import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ftinc.kit.drawer.R;
import com.ftinc.kit.font.Face;
import com.ftinc.kit.font.FontLoader;
import com.ftinc.kit.preferences.BooleanPreference;

import butterknife.ButterKnife;

/**
 * Created by r0adkll on 11/13/14.
 */
public class SwitchDrawerItem extends DrawerItem implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private int mText;
    private SwitchCompat mSwitch;
    private BooleanPreference mPreference;

    /**
     * Constructor
     * @param id
     * @param text
     */
    public SwitchDrawerItem(int id, int text, BooleanPreference preference) {
        super(id);
        mText = text;
        mPreference = preference;
    }

    public SwitchCompat getSwitch(){
        return mSwitch;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.navdrawer_item_switch, container, false);

        TextView titleView = ButterKnife.findById(view, R.id.title);
        mSwitch = ButterKnife.findById(view, R.id.item_switch);

        // Apply the preference
        mSwitch.setChecked(mPreference.get());

        // Set the switch checked change listener that updates the set boolean preference
        mSwitch.setOnCheckedChangeListener(this);

        view.setOnClickListener(this);

        // Set the title of the drawer item
        FontLoader.apply(titleView, Face.ROBOTO_MEDIUM);
        titleView.setText(mText);
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mPreference.set(isChecked);
    }

    @Override
    public void onClick(View v) {
        mSwitch.setChecked(!mSwitch.isChecked());
    }
}
