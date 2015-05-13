package com.ftinc.kit.winds.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.winds.model
 * Created by drew.heavner on 3/13/15.
 */
public class ChangeLog implements Parcelable{

    /***********************************************************************************************
     *
     * Variables
     *
     */

    public List<Version> versions = new ArrayList<>();

    /**
     * Default Constructor
     */
    public ChangeLog(){}

    /**
     * Parcelable Constructor
     */
    private ChangeLog(Parcel in){
        in.readTypedList(versions, Version.CREATOR);
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    /**
     * Add a version to this change log
     * @param version
     */
    public void addVersion(Version version){
        versions.add(version);
    }


    /***********************************************************************************************
     *
     * Parcelable Methods
     *
     */


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(versions);
    }

    public static final Creator<ChangeLog> CREATOR = new Creator<ChangeLog>() {
        @Override
        public ChangeLog createFromParcel(Parcel source) {
            return new ChangeLog(source);
        }

        @Override
        public ChangeLog[] newArray(int size) {
            return new ChangeLog[size];
        }
    };

}
