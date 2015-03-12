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

import android.os.Bundle;

/**
 * Project: 52Kit
 * Package: com.ftinc.kit.mvp
 * Created by drew.heavner on 3/9/15.
 */
public interface IBaseActivityPresenter {

    /**
     * Parse the extras from {@link android.app.Activity#getIntent()} or the
     * savedInstanceState(icicle) bundles for this view
     *
     * @param icicle        the saved instance state bundle
     */
    void parseExtras(Bundle icicle);

    /**
     * Save the instance state for a given activity
     *
     * @param outState      the out state bundle to store in
     */
    void saveInstanceState(Bundle outState);

}
