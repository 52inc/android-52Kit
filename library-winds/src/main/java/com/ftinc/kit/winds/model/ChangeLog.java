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
