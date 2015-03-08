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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a helper class for loading and applying Roboto fonts to {@link android.widget.TextView}
 * or for just loading and returning the {@link android.graphics.Typeface} themselves.
 *
 * This uses a special String input called 'type' to determine which roboto font to load.
 * Examples:
 * <pre>
 *     'roboto-[black|italic|bold|light|medium|thin|regular]'
 *     'condensed-[bold|light]'
 *
 *     So typical usage is like:
 *      'roboto-regular',
 *      'roboto-bold',
 *      'roboto-light',
 *      'roboto-bold-italic',
 *      'roboto-medium-italic'
 *
 *      or
 *
 *      'condensed-bold',
 *      'condensed-light',
 *      'condensed-italic',
 *      'condensed-regular',
 *      'condensed-bold-italic', etc...
 *
 * </pre>
 *
 * For a list of all 'types' that can be used see {@link com.ftinc.kit.font.Types}
 *
 * Created by drew@52inc.co on 6/26/14.
 */
public class FontLoader {

    /**
     * Typeface Prefix Constants
     */
    private static final String ROBOTO = "Roboto";
    private static final String CONDENSED = "Condensed";
    private static final String ROBOTO_CONDENSED = "RobotoCondensed";

    /**
     * Typeface Suffix Components
     */
    private static final String BLACK = "Black";
    private static final String ITALIC = "Italic";
    private static final String BOLD = "Bold";
    private static final String LIGHT = "Light";
    private static final String MEDIUM = "Medium";
    private static final String THIN = "Thin";
    private static final String REGULAR = "Regular";

    /**
     * Array of all the first level suffix components for Roboto
     */
    private static final String[] TYPE_ROBOTO_CHOICES = new String[]{BLACK.toLowerCase(), BOLD.toLowerCase(), ITALIC.toLowerCase(), LIGHT.toLowerCase(), MEDIUM.toLowerCase(), REGULAR.toLowerCase(), THIN.toLowerCase()};

    /**
     * Array of all the first level suffix components that can be followed by 'Italic' for Roboto
     */
    private static final String[] TYPE_ROBOTO_FIRST_CHOICES = new String[]{BLACK.toLowerCase(), BOLD.toLowerCase(), LIGHT.toLowerCase(), MEDIUM.toLowerCase(), THIN.toLowerCase()};

    /**
     * Array of all the first level suffix components for RobotoCondensed
     */
    private static final String[] TYPE_CONDENSED_CHOICES = new String[]{BOLD.toLowerCase(), LIGHT.toLowerCase(), ITALIC.toLowerCase(), REGULAR.toLowerCase()};

    /**
     * Array of all the first level suffix components that can be followed by 'Italic' for RobotoCondensed
     */
    private static final String[] TYPE_CONDENSED_FIRST_CHOICES = new String[]{BOLD.toLowerCase(), LIGHT.toLowerCase()};

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
     * @see com.ftinc.kit.font.Types
     *
     * @param textView      the text view you wish to apply to
     * @param type          the typeface to apply
     */
    public static void applyTypeface(TextView textView, String type){
        // First check for existing typefaces
        Typeface typeface = getTypeface(textView.getContext(), type);
        if (typeface != null)
            textView.setTypeface(typeface);
    }

    /**
     * Apply a typeface to a given view by id
     *
     * @param viewId    the TextView to apply to id
     * @param parent    the parent of the text view
     * @param type      the typeface type argument
     */
    public static void applyTypeface(View parent, int viewId, String type){
        TextView text = (TextView) parent.findViewById(viewId);
        if(text != null)
            applyTypeface(text, type);
    }

    /**
     * Apply a typeface to a given view by id
     *
     * @param activity      the activity in which the textview resides
     * @param viewId        the id of the textview you want to style
     * @param type          the typeface code to apply
     */
    public static void applyTypeface(Activity activity, int viewId, String type){
        TextView text = (TextView) activity.findViewById(viewId);
        if(text != null)
            applyTypeface(text, type);
    }

    /**
     * Get a Roboto typeface for a given string type
     *
     * @param ctx       the application context
     * @param type      the typeface type argument
     * @return          the loaded typeface, or null
     */
    public static Typeface getTypeface(Context ctx, String type){
        // Check for existing typefaces
        Typeface existing = mLoadedTypefaces.get(type);
        if(existing == null) {
            String typeface = parseTypefaceType(type);
            if (typeface != null && !typeface.isEmpty()) {
                existing = Typeface.createFromAsset(ctx.getAssets(), "fonts/" + typeface);
                mLoadedTypefaces.put(type, existing);
            }
        }

        return existing;
    }


    /*********************************************************************************
     *
     * Private Helper Methods
     *
     */


    /**
     * Parse the typeface type request parameter
     * to find the appropriate typeface file
     *
     * @param type      the typeface type argument
     * @return          the full typeface filename
     */
    private static String parseTypefaceType(String type){
        String[] parts = type.split("-");

        // Check the first part
        String builder = "";
        if(parts[0].equalsIgnoreCase(ROBOTO)){
            builder = ROBOTO.concat("-");
            builder = builder.concat(parseArguments(ROBOTO, Arrays.copyOfRange(parts, 1, parts.length)));
        }else if(parts[0].equalsIgnoreCase(CONDENSED)){
            builder = ROBOTO_CONDENSED.concat("-");
            builder = builder.concat(parseArguments(CONDENSED, Arrays.copyOfRange(parts, 1, parts.length)));
        }

        // Concat the suffix of the font file
        builder = builder.concat(".ttf");

        return builder;
    }

    /**
     * Parse the type arguments for requesting a typeface
     *
     * @param type      the type, Roboto, or Condensed
     * @param args      the array of argument strings
     * @return          the parsed argument suffix string
     */
    private static String parseArguments(String type, String[] args){

        List<String> builder = new ArrayList<>();
        List<String> robotoChoices = Arrays.asList(TYPE_ROBOTO_CHOICES);
        List<String> robotoFirstChoices = Arrays.asList(TYPE_ROBOTO_FIRST_CHOICES);
        List<String> condensedChoices = Arrays.asList(TYPE_CONDENSED_CHOICES);
        List<String> condensedFirstChoices = Arrays.asList(TYPE_CONDENSED_FIRST_CHOICES);

        // Now analyze the rest of the parts
        int N = (args.length > 2) ? 2 : args.length;
        for(int i=0; i<N; i++){
            String part = args[i];

            if(type.equalsIgnoreCase(ROBOTO)){
                if(builder.size() == 0) {
                    if (robotoChoices.contains(part.toLowerCase())) {
                        // So the first
                        String id = (Character.toUpperCase(part.charAt(0))) + part.toLowerCase().substring(1);
                        switch (id){
                            case BLACK:
                                builder.add(BLACK);
                                break;
                            case BOLD:
                                builder.add(BOLD);
                                break;
                            case ITALIC:
                                builder.add(ITALIC);
                                break;
                            case LIGHT:
                                builder.add(LIGHT);
                                break;
                            case MEDIUM:
                                builder.add(MEDIUM);
                                break;
                            case REGULAR:
                                builder.add(REGULAR);
                                break;
                            case THIN:
                                builder.add(THIN);
                                break;
                        }
                    }
                }else{
                    if(robotoFirstChoices.contains(builder.get(0))){
                        String id = (Character.toUpperCase(part.charAt(0))) + part.toLowerCase().substring(1);
                        switch (id){
                            case ITALIC:
                                builder.add(ITALIC);
                                break;
                        }
                    }
                }


            }else if(type.equalsIgnoreCase(CONDENSED)){

                if(builder.size() == 0){
                    if(condensedChoices.contains(part.toLowerCase())){
                        String id = (Character.toUpperCase(part.charAt(0))) + part.toLowerCase().substring(1);
                        switch (id){
                            case BOLD:
                                builder.add(BOLD);
                                break;
                            case ITALIC:
                                builder.add(ITALIC);
                                break;
                            case LIGHT:
                                builder.add(LIGHT);
                                break;
                            case REGULAR:
                                builder.add(REGULAR);
                                break;
                        }
                    }
                }else{
                    if(condensedFirstChoices.contains(builder.get(0))){
                        String id = (Character.toUpperCase(part.charAt(0))) + part.toLowerCase().substring(1);
                        switch (id){
                            case ITALIC:
                                builder.add(ITALIC);
                                break;
                        }
                    }
                }

            }


        }

        // Construct Post String
        String result = "";
        for(String part: builder){
            result = result.concat(part);
        }

        return result;
    }


}
