package com.ftinc.kit.drawer.model;


import com.f2prateek.rx.preferences.Preference;


public class BooleanPreferenceImpl implements IPreference<Boolean> {

    private Preference<Boolean> preference;

    public BooleanPreferenceImpl(Preference<Boolean> preference){
        this.preference = preference;
    }

    @Override
    public void set(Boolean value) {
        preference.set(value);
    }

    @Override
    public Boolean get() {
        return preference.get();
    }
}
