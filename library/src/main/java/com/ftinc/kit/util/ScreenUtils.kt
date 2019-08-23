/*
 * Copyright (c) 2018 52inc.
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

package com.ftinc.kit.util


import android.content.Context
import android.content.res.Resources
import android.view.View


object ScreenUtils {

    fun smallestWidth(resources: Resources, widthInDp: Int): Boolean {
        return resources.configuration.smallestScreenWidthDp >= widthInDp
    }


    fun smallestWidth(resources: Resources, config: Config): Boolean = smallestWidth(resources, config.widthInDp)


    fun View.smallestWidth(config: Config): Boolean = smallestWidth(this.resources, config)


    enum class Config(val widthInDp: Int) {
        PHONE(320),
        PHABLET(480),
        TABLET_7IN(600),
        TABLET_10(720)
    }
}
