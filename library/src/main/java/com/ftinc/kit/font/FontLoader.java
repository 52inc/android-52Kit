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

package com.ftinc.kit.font;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.LruCache;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * This is a helper class for loading and applying Roboto fonts to {@link android.widget.TextView}
 * or for just loading and returning the {@link android.graphics.Typeface} themselves.
 *
 * For a list of all 'types' that can be used see {@link Face}
 *
 * Created by drew@52inc.co on 6/26/14.
 */
public class FontLoader {

    /**
     * The Lru typeface memory cache so as not to keep loading typefaces from the disk
     */
    private static LruCache<String, Typeface> mLoadedTypefaces = new LruCache<>(3 * 1024 * 1024); // 3 MiB cache

    /*********************************************************************************
     *
     * Public Accessible Methods
     *
     */


    /**
     * Apply a typeface to a textview
     *
     * @see Face
     *
     * @param textView      the text view you wish to apply to
     * @param type          the typeface to apply
     */
    public static void apply(TextView textView, Face type){
        // First check for existing typefaces
        Typeface typeface = getTypeface(textView.getContext(), type);
        if (typeface != null)
            textView.setTypeface(typeface);
    }

    /**
     * Apply a typeface to one or many textviews
     *
     * @param type          the typeface to apply
     * @param textViews     the one or many textviews to apply to
     */
    public static void apply(Face type, TextView... textViews){
        if(textViews.length == 0) return;
        for (int i = 0; i < textViews.length; i++) {
            apply(textViews[i], type);
        }
    }

    /**
     * Apply a typeface to a given view by id
     *
     * @param viewId    the TextView to apply to id
     * @param parent    the parent of the text view
     * @param type      the typeface type argument
     */
    public static void apply(View parent, int viewId, Face type){
        TextView text = ButterKnife.findById(parent, viewId);
        if(text != null)
            apply(text, type);
    }

    /**
     * Apply a typeface to a given view by id
     *
     * @param activity      the activity in which the textview resides
     * @param viewId        the id of the textview you want to style
     * @param type          the typeface code to apply
     */
    public static void apply(Activity activity, int viewId, Face type){
        TextView text = ButterKnife.findById(activity, viewId);
        if(text != null)
            apply(text, type);
    }

    /**
     * Get a Roboto typeface for a given string type
     *
     * @param ctx       the application context
     * @param type      the typeface type argument
     * @return          the loaded typeface, or null
     */
    public static Typeface getTypeface(Context ctx, Face type){
        return getTypeface(ctx, "fonts/" + type.getFontFileName());
    }

    /**
     * Get a typeface for a given the font name/path
     *
     * @param ctx           the context to load the typeface from assets
     * @param fontName      the name/path of of the typeface to load
     * @return              the loaded typeface, or null
     */
    public static Typeface getTypeface(Context ctx, String fontName){
        Typeface existing = mLoadedTypefaces.get(fontName);
        if(existing == null){
            existing = Typeface.createFromAsset(ctx.getAssets(), fontName);
            mLoadedTypefaces.put(fontName, existing);
        }
        return existing;
    }


    /*********************************************************************************
     *
     * Private Helper Methods
     *
     */


}
