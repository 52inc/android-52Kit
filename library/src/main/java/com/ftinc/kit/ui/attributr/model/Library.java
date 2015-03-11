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

/**
 * Project: 52Kit
 * Package: com.ftinc.kit.ui.attributr.model
 * Created by drew.heavner on 3/9/15.
 */
public class Library {

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

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    public String getLicenseText(){
        if(license != null){
            return license.getLicense(description, year, name, email);
        }
        return "N/A";
    }

}