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
public class Version implements Parcelable{

    /***********************************************************************************************
     *
     * Variables
     *
     */

    public int code;
    public String name;
    public String date;
    public List<Change> changes = new ArrayList<>();

    /**
     * Default Constructor
     */
    public Version(){}

    /**
     * Parcelable Constructor
     */
    private Version(Parcel in){
        code = in.readInt();
        name = in.readString();
        date = in.readString();
        in.readTypedList(changes, Change.CREATOR);
    }

    /**
     * Add a change line to this version
     *
     * @param change        the change to add
     */
    public void addChange(Change change){
        changes.add(change);
    }

    public String getDisplayString(){
        return String.format("Version %s %s", name, date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeTypedList(changes);
    }

    public static final Creator<Version> CREATOR = new Creator<Version>() {
        @Override
        public Version createFromParcel(Parcel source) {
            return new Version(source);
        }

        @Override
        public Version[] newArray(int size) {
            return new Version[size];
        }
    };

}
