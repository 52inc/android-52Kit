package com.ftinc.kit.ui.winds.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;
import android.text.TextUtils;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.ui.winds.model
 * Created by drew.heavner on 3/13/15.
 */
public class Change implements Parcelable{

    /***********************************************************************************************
     *
     * Enums
     *
     */

    public enum Type{
        NEW,
        UPDATE,
        FIX,
        MYTHICAL,
        NONE;

        public static Type from(int ordinal){
            return Type.values()[ordinal];
        }

        public static Type from(String code){
            if(TextUtils.isEmpty(code)) return NONE;
            switch (code.toLowerCase()){
                case "new":
                    return NEW;
                case "update":
                    return UPDATE;
                case "fix":
                    return FIX;
                case "myth":
                    return MYTHICAL;
                case "mythical":
                    return MYTHICAL;
                default:
                    return NONE;
            }
        }

    }

    /***********************************************************************************************
     *
     * Variables
     *
     */

    public Type type;
    public CharSequence text;

    /**
     * Default Constructor
     */
    public Change(){}

    /**
     * Parcelable Constructor
     *
     * @param in        the parcel to parse
     */
    private Change(Parcel in){
        type = Type.from(in.readInt());
        text = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
    }

    /**
     * Parse the string into html code by replacing all the '[' with '<' and ']' with '>'
     * and converting it into a Span
     *
     * @param rawText   the raw text from xml
     */
    public void setText(String rawText){
        // Format the text into html
        String htmlText = rawText.replaceAll("\\[", "<").replaceAll("\\]", ">");
        text = Html.fromHtml(htmlText);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type.ordinal());
        TextUtils.writeToParcel(text, dest, 0);
    }

    public static final Creator<Change> CREATOR = new Creator<Change>() {
        @Override
        public Change createFromParcel(Parcel source) {
            return new Change(source);
        }

        @Override
        public Change[] newArray(int size) {
            return new Change[size];
        }
    };

}
