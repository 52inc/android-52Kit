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

package com.ftinc.kit.winds.internal;

import android.content.Context;
import android.support.annotation.XmlRes;

import com.ftinc.kit.winds.model.Change;
import com.ftinc.kit.winds.model.ChangeLog;
import com.ftinc.kit.winds.model.Version;
import com.ftinc.kit.util.Utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Winds XML changelog parser
 *
 * Created by r0adkll on 3/10/15.
 */
public final class Parser {

    private final static String DOCUMENT_TAG = "Changelog";
    private final static String TAG_VERSION = "Version";
    private final static String TAG_CHANGE = "Change";
    private final static String ATTR_CODE = "code";
    private final static String ATTR_NAME = "name";
    private final static String ATTR_DATE = "date";
    private final static String ATTR_TYPE = "type";


    /**
     * Parse a given XML resource file
     *
     * @param license       the license res id
     * @return              the list of parsed libraries
     */
    public static ChangeLog parse(Context ctx, @XmlRes int license){
        ChangeLog clog = new ChangeLog();

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
                if(name.equals(TAG_VERSION)){
                    clog.addVersion(readVersion(parser));
                }else{
                    skip(parser);
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clog;
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
    private static Version readVersion(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, TAG_VERSION);
        Version version = new Version();

        // Pull attribute
        version.code = Utils.parseInt(parser.getAttributeValue(null, ATTR_CODE), -1);
        version.name = parser.getAttributeValue(null, ATTR_NAME);
        version.date = parser.getAttributeValue(null, ATTR_DATE);

        // Pull children
        while(parser.next() != XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name){
                case TAG_CHANGE:
                    version.addChange(readChange(parser));
                    break;
                default:
                    skip(parser);
            }

        }

        return version;
    }

    private static Change readChange(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, TAG_CHANGE);
        Change change = new Change();

        change.type = Change.Type.from(parser.getAttributeValue(null, ATTR_TYPE));
        change.setText(readText(parser));

        parser.require(XmlPullParser.END_TAG, null, TAG_CHANGE);
        return change;
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
