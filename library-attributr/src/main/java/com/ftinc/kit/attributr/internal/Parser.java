/*
 * Copyright (c) 2019 52inc.
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

package com.ftinc.kit.attributr.internal;

import android.content.Context;
import androidx.annotation.XmlRes;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by r0adkll on 3/10/15.
 */
public final class Parser {

    /***********************************************************************************************
     *
     * Constants
     *
     */

    private final static String DOCUMENT_TAG = "Licenses";
    private final static String TAG_LIBRARY = "Library";
    private final static String TAG_NAME = "Name";
    private final static String TAG_AUTHOR = "Author";
    private final static String TAG_YEAR = "Year";
    private final static String TAG_EMAIL = "Email";
    private final static String TAG_DESCRIPTION = "Description";
    private final static String TAG_URL = "Url";
    private final static String ATTR_LICENSE = "license";


    /**
     * Parse a given XML resource file
     *
     * @param license       the license res id
     * @return              the list of parsed libraries
     */
    public static List<com.ftinc.kit.attributr.model.Library> parse(Context ctx, @XmlRes int license){
        List<com.ftinc.kit.attributr.model.Library> libraries = new ArrayList<>();

        // Get the XMLParser
        XmlPullParser parser = ctx.getResources().getXml(license);

        // Begin parsing
        try {
            parser.next();
            parser.require(XmlPullParser.START_DOCUMENT, null, null);
            parser.nextTag();
            parser.require(XmlPullParser.START_TAG, null, DOCUMENT_TAG);
            while(parser.next() != XmlPullParser.END_TAG){
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }

                String name = parser.getName();
                if(name.equals(TAG_LIBRARY)){
                    libraries.add(readLibrary(parser));
                }else{
                    skip(parser);
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return libraries;
    }

    /**
     * Read an XML defined Library object
     *
     * @param parser        the pull parser
     * @return              the parsed Library object
     *
     * @throws IOException              if there was a problem reading the file
     * @throws XmlPullParserException   if there was a problem parsing the library
     */
    private static com.ftinc.kit.attributr.model.Library readLibrary(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, TAG_LIBRARY);
        com.ftinc.kit.attributr.model.Library lib = new com.ftinc.kit.attributr.model.Library();

        // Pull attribute
        String license = parser.getAttributeValue(null, ATTR_LICENSE);
        lib.license = com.ftinc.kit.attributr.model.License.fromKey(license);

        // Pull children
        while(parser.next() != XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name){
                case TAG_NAME:
                    lib.name = readTextTag(parser, TAG_NAME);
                    break;
                case TAG_AUTHOR:
                    lib.author = readTextTag(parser, TAG_AUTHOR);
                    break;
                case TAG_YEAR:
                    lib.year = readTextTag(parser, TAG_YEAR);
                    break;
                case TAG_EMAIL:
                    lib.email = readTextTag(parser, TAG_EMAIL);
                    break;
                case TAG_DESCRIPTION:
                    lib.description = readTextTag(parser, TAG_DESCRIPTION);
                    break;
                case TAG_URL:
                    lib.url = readTextTag(parser, TAG_URL);
                    break;
                default:
                    skip(parser);
            }

        }

        return lib;
    }

    private static String readTextTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, tag);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, tag);
        return title;
    }

    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
