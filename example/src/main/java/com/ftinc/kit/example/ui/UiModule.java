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

package com.ftinc.kit.example.ui;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.example.ui
 * Created by drew.heavner on 5/12/15.
 */
@Module
public class UiModule {

    @Provides @Singleton
    InputMethodManager provideIMM(Context appContext){
        return (InputMethodManager) appContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Provides @Singleton
    NotificationManager provideNotificationManager(Context appContext){
        return (NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides @Singleton
    NotificationManagerCompat provideCompatNotificationManager(Context appContext){
        return NotificationManagerCompat.from(appContext);
    }

}
