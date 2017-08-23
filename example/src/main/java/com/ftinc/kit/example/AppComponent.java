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
