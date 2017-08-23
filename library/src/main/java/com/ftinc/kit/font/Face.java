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

package com.ftinc.kit.font;

/**
 * These are all the available typeface types that the {@link com.ftinc.kit.font.FontLoader}
 * will take to load the Roboto typefaces.
 *
 * Created by drew.heavner on 6/26/14.
 */
public enum Face {
    ROBOTO_BOLD("Roboto-Bold.ttf"),
    ROBOTO_LIGHT("Roboto-Light.ttf"),
    ROBOTO_MEDIUM("Roboto-Medium.ttf"),
    ROBOTO_REGULAR("Roboto-Regular.ttf"),
    ROBOTO_THIN("Roboto-Thin.ttf");

    private final String mFileName;

    /**
     * Constructor
     * @param fileName
     */
    Face(String fileName){
        mFileName = fileName;
    }

    public String getFontFileName(){
        return mFileName;
    }

}
