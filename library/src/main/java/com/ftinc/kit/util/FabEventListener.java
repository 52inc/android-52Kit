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

package com.ftinc.kit.util;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.listeners.EventListenerAdapter;

public class FabEventListener extends EventListenerAdapter {

        private View mFab;

        public FabEventListener(View fab){
            mFab = fab;
        }

        @Override
        public void onShow(Snackbar sb) {
            int height = sb.getHeight();
            mFab.animate()
                    .translationY(-height)
                    .setDuration(300)
                    .setInterpolator(new DecelerateInterpolator(1.5f))
                    .start();
        }

        @Override
        public void onDismiss(Snackbar sb) {
            mFab.animate()
                    .translationY(0)
                    .setDuration(300)
                    .setInterpolator(new AccelerateInterpolator(1.5f))
                    .start();
        }
    }