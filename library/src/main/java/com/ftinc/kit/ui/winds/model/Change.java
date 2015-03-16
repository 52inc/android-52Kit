package com.ftinc.kit.ui.winds.model;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.ftinc.kit.R;
import com.ftinc.kit.font.Face;
import com.ftinc.kit.font.TypefaceSpan;

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
    public String text;

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
        text = in.readString();
    }

    /**
     * Parse the string into html code by replacing all the '[' with '<' and ']' with '>'
     * and converting it into a Span
     *
     * @param rawText   the raw text from xml
     */
    public void setText(String rawText){
        text = rawText.trim();
    }

    public CharSequence getDisplayText(Context ctx){
        // Format the text into html
        String htmlText = text.replaceAll("\\[", "<").replaceAll("\\]", ">");

        SpannableString text = new SpannableString(Html.fromHtml(htmlText));
        SpannableString tag;
        switch (type){
            case NEW:
                tag = new SpannableString("NEW: ");
                tag.setSpan(new ForegroundColorSpan(ctx.getResources().getColor(R.color.green_500)), 0, tag.length(), 0);
                tag.setSpan(new TypefaceSpan(ctx, Face.ROBOTO_BOLD), 0, tag.length(), 0);
                break;
            case FIX:
                tag = new SpannableString("FIX: ");
                tag.setSpan(new ForegroundColorSpan(ctx.getResources().getColor(R.color.red_500)), 0, tag.length(), 0);
                tag.setSpan(new TypefaceSpan(ctx, Face.ROBOTO_BOLD), 0, tag.length(), 0);
                break;
            case UPDATE:
                tag = new SpannableString("UPDATE: ");
                tag.setSpan(new ForegroundColorSpan(ctx.getResources().getColor(R.color.blue_500)), 0, tag.length(), 0);
                tag.setSpan(new TypefaceSpan(ctx, Face.ROBOTO_BOLD), 0, tag.length(), 0);
                break;
            case MYTHICAL:
                tag = new SpannableString("MYTH: ");
                tag.setSpan(new ForegroundColorSpan(ctx.getResources().getColor(R.color.purple_500)), 0, tag.length(), 0);
                tag.setSpan(new TypefaceSpan(ctx, Face.ROBOTO_BOLD), 0, tag.length(), 0);
                text.setSpan(new StyleSpan(Typeface.ITALIC), 0, text.length(), 0);
                break;
            default:
                tag = new SpannableString("");
        }

        return TextUtils.concat(tag, text);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type.ordinal());
        dest.writeString(text);
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
