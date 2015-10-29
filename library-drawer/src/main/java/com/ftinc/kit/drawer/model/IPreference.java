package com.ftinc.kit.drawer.model;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.drawer.model
 * Created by drew.heavner on 10/29/15.
 */
public interface IPreference<T> {

    void set(T value);
    T get();

}
