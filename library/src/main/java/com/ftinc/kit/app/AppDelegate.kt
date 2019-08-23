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

package com.ftinc.kit.app


import android.app.Application


/**
 * A delegate to provide configuration application setups based on flavor or build type that
 * can be injected via Dagger
 */
interface AppDelegate {

    fun onCreate(app: Application)
}

/**
 * No-Op delegate to provide when needed
 */
class NoOpAppDelegate : AppDelegate {
    override fun onCreate(app: Application) {}
}
