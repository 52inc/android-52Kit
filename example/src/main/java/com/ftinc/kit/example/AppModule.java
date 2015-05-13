package com.ftinc.kit.example;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.example
 * Created by drew.heavner on 5/12/15.
 */
@Module
public class AppModule {

    private App mApp;

    public AppModule(App app){
        mApp = app;
    }

    @Provides @Singleton
    Context provideApplicationContext(){
        return mApp;
    }

}
