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
import android.support.v4.app.Fragment;


import dagger.ObjectGraph;

/**
 * Created by r0adkll on 12/1/14.
 */
public abstract class BaseFragment extends Fragment {

    private ObjectGraph fragmentGraph;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        fragmentGraph = HaystackApp.get(getActivity()).createScopedGraph(getModules());
        fragmentGraph.inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentGraph = null;
    }

    protected abstract Object[] getModules();

}
