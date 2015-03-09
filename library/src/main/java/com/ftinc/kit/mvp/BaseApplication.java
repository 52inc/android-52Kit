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
import android.app.Fragment;
import android.app.Service;
import android.content.Context;

import com.ftinc.kit.BuildConfig;
import com.ftinc.kit.mvp.modules.Mods;

import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * Created by r0adkll on 3/9/15.
 */
public abstract class BaseApplication extends Application {

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private ObjectGraph mObjectGraph;

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Logging Trees
        if(BuildConfig.DEBUG){
            for(Timber.Tree tree: getDebugTrees()){
                Timber.plant(tree);
            }
        }else{
            for(Timber.Tree tree: getReleaseTrees()){
                Timber.plant(tree);
            }
        }

        // Build and inject the object graph
        buildObjectGraphAndInject();

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
     * Get the module manager, {@link com.ftinc.kit.mvp.modules.Mods}, to get the list of modules
     * for dependency Injection
     *
     * @return      the module manager
     */
    public abstract Mods getMods();

    /***********************************************************************************************
     *
     * Dagger Injection Methods
     *
     */

    /**
     * Build and inject the object graph
     */
    private void buildObjectGraphAndInject(){
        mObjectGraph = ObjectGraph.create(getMods().getModules());
        mObjectGraph.inject(this);
    }

    /**
     * Create a scoped object graph
     *
     * @param modules       the list of modules to add to the scope
     * @return              the scoped graph
     */
    public ObjectGraph createScopedGraph(Object... modules){
        return mObjectGraph.plus(modules);
    }

    /**
     * Inject an object with the object graph
     */
    public void inject(Object o){
        mObjectGraph.inject(o);
    }

    /***********************************************************************************************
     *
     * Static Methods
     *
     */

    /**
     * Get a reference to the Application
     *
     * @param ctx       the context
     * @return          the ChipperApp reference
     */
    public static BaseApplication get(Context ctx){
        return (BaseApplication) ctx.getApplicationContext();
    }

    /**
     * Get a reference to this application with a service
     * object
     *
     * @param ctx       the service object
     * @return          the Application reference for injection
     */
    public static BaseApplication get(Service ctx){
        return (BaseApplication) ctx.getApplication();
    }

    /**
     * Get the reference to this application with a Fragment object
     *
     * @param fragment      the fragment object
     * @return              the Application reference for injection
     */
    public static BaseApplication get(Fragment fragment){
        return (BaseApplication) fragment.getActivity().getApplication();
    }

    /**
     * Get the reference to this application with a Fragment object
     *
     * @param fragment      the fragment object
     * @return              the Application reference for injection
     */
    public static BaseApplication get(android.support.v4.app.Fragment fragment){
        return (BaseApplication) fragment.getActivity().getApplication();
    }


}
