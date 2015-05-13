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

package com.ftinc.kit.mvp;

import android.app.Application;

import timber.log.Timber;

/**
 * Base application that takes care of planting timber trees and
 * soon will incorporate other shortcuts for features added down the road
 *
 * Created by r0adkll on 3/9/15.
 */
public abstract class BaseApplication extends Application {

    /***********************************************************************************************
     *
     * Variables
     *
     */

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Logging Trees
        if(isDebug()){
            for(Timber.Tree tree: getDebugTrees()){
                Timber.plant(tree);
            }
        }else{
            for(Timber.Tree tree: getReleaseTrees()){
                Timber.plant(tree);
            }
        }

    }

    /***********************************************************************************************
     *
     * Abstract Methods
     *
     */

    /**
     * Get the list of {@link timber.log.Timber} {@link timber.log.Timber.Tree}s to use
     * for Debug builds
     *
     * @return      the array of debug build logging trees
     */
    public abstract Timber.Tree[] getDebugTrees();

    /**
     * Get the list of {@link timber.log.Timber} {@link timber.log.Timber.Tree}s to use
     * for Release builds
     *
     * @return      the array of release build logging trees
     */
    public abstract Timber.Tree[] getReleaseTrees();

    /**
     * Return whether or not the application build is a debug. Since I cannot read the project
     * implementing this' BuildConfig.DEBUG flag I have to implement this method to correctly
     * plant the Timber Trees
     *
     * @return      true if subclassing app is in debug, false otherwise
     */
    public abstract Boolean isDebug();

}
