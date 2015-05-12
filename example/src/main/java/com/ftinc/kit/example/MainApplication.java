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

package com.ftinc.kit.example;

import com.ftinc.kit.mvp.BaseApplication;

import timber.log.Timber;

/**
 * Created by r0adkll on 3/12/15.
 */
public class MainApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // Setup PostOffice....

        // Setup ORM....

        // etc....

    }

    @Override
    public Timber.Tree[] getDebugTrees() {
        return new Timber.Tree[]{
                new Timber.DebugTree()
        };
    }

    @Override
    public Timber.Tree[] getReleaseTrees() {
        return new Timber.Tree[0];
    }

    @Override
    public Boolean isDebug() {
        return BuildConfig.DEBUG;
    }

}
