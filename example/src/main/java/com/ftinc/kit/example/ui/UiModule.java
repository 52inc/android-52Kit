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
