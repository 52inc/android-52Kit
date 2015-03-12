/*
 * Copyright (c) 2015 52inc
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
 */

package com.ftinc.kit.ui.attributr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ftinc.kit.util.Utils;

import java.util.Comparator;

/**
 * Project: 52Kit
 * Package: com.ftinc.kit.ui.attributr.model
 * Created by drew.heavner on 3/9/15.
 */
public class Library implements Parcelable{

    /***********************************************************************************************
     *
     * Variables
     *
     */

    public String name;
    public String author;
    public String description;
    public String url;
    public String year;
    public String email;
    public License license;

    /**
     * Default constructor
     */
    public Library(){}

    /**
     * Parcelable constructor
     * @param in        the parcel to reconstruct from
     */
    private Library(Parcel in){
        name = in.readString();
        author = in.readString();
        description = in.readString();
        url = in.readString();
        year = in.readString();
        email = in.readString();
        license = License.values()[in.readInt()];
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    /**
     * Get the formatted license text for display
     *
     * @return      the formatted user facing license text
     */
    public String getLicenseText(){
        if(license != null){
            return license.getLicense(description, year, author, email);
        }
        return "N/A";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(author);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(year);
        dest.writeString(email);
        dest.writeInt(license.ordinal());
    }

    public static final Creator<Library> CREATOR = new Creator<Library>() {
        @Override
        public Library createFromParcel(Parcel source) {
            return new Library(source);
        }

        @Override
        public Library[] newArray(int size) {
            return new Library[size];
        }
    };

    /**
     * The Library comparator for sorting it by it's license
     */
    public static class LibraryComparator implements Comparator<Library> {
        @Override
        public int compare(Library lhs, Library rhs) {
            return Utils.compare(lhs.license.ordinal(), rhs.license.ordinal());
        }
    }

}
