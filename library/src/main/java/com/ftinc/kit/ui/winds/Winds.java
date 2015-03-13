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

package com.ftinc.kit.ui.winds;

import android.content.Context;
import android.support.annotation.XmlRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ftinc.kit.ui.winds.internal.Parser;
import com.ftinc.kit.ui.winds.model.ChangeLog;
import com.ftinc.kit.ui.winds.ui.ChangeLogAdapter;
import com.ftinc.kit.widget.StickyRecyclerHeadersElevationDecoration;

/**
 * Static tool class for inflating and displaying a changelog in a Material Design manner
 * </br>
 * <p>
 * Winds - 'Winds of Change'
 * </p>
 * </br>
 * Created by r0adkll on 3/11/15.
 */
public class Winds {

    // TODO: Actually implement this

    public static RecyclerView draft(Context ctx, @XmlRes int configId){

        // Parse config
        ChangeLog changeLog = Parser.parse(ctx, configId);

        // Setup the adapter
        ChangeLogAdapter adapter = new ChangeLogAdapter();
        adapter.setChangeLog(changeLog);

        // Parse the configuration from the resource ID and generate a RecyclerView that is ready to go
        RecyclerView recycler = new RecyclerView(ctx);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(ctx));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.addItemDecoration(new StickyRecyclerHeadersElevationDecoration(adapter));

        return recycler;
    }

}
