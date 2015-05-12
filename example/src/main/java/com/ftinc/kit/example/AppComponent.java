package com.ftinc.kit.example;

import com.ftinc.kit.example.ui.UiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.example
 * Created by drew.heavner on 5/12/15.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        UiModule.class
})
public interface AppComponent {
    void inject(App app);

    // Subcomponent scope graphs


}
