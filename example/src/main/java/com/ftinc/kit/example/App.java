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

package com.ftinc.kit.example;

import android.app.Application;
import android.app.Service;
import android.content.Context;

import hugo.weaving.DebugLog;

/**
 * Created by r0adkll on 3/12/15.
 */
public class App extends Application {

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private AppComponent mComponent;

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onCreate() {
        super.onCreate();

        // Setup PostOffice....

        // Setup ORM....

        // etc....

        buildAndInject();
    }

    /***********************************************************************************************
     *
     * 52Kit base methods
     *
     */

//    @Override
//    public Timber.Tree[] getDebugTrees() {
//        return new Timber.Tree[]{
//            new Timber.DebugTree()
//        };
//    }
//
//    @Override
//    public Timber.Tree[] getReleaseTrees() {
//        return new Timber.Tree[0];
//    }
//
//    @Override
//    public Boolean isDebug() {
//        return BuildConfig.DEBUG;
//    }

    /***********************************************************************************************
     *
     * Dagger 2 Methods
     *
     */

    @DebugLog
    private void buildAndInject(){
        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        mComponent.inject(this);
    }

    public AppComponent component(){
        return mComponent;
    }

    public static App get(Context ctx){
        return (App) ctx.getApplicationContext();
    }

    public static App get(Service ctx){
        return (App) ctx.getApplication();
    }

}
