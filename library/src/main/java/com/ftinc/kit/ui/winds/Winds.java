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
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.XmlRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ftinc.kit.BuildConfig;
import com.ftinc.kit.R;
import com.ftinc.kit.ui.winds.internal.Parser;
import com.ftinc.kit.ui.winds.model.ChangeLog;
import com.ftinc.kit.ui.winds.model.Version;
import com.ftinc.kit.ui.winds.ui.ChangeLogActivity;
import com.ftinc.kit.ui.winds.ui.ChangeLogAdapter;
import com.ftinc.kit.widget.StickyRecyclerHeadersElevationDecoration;

import timber.log.Timber;

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

    private static final String PREF_CHANGELOG_LAST_SEEN = "pref_winds_last_version_code_seen";

    /**
     * Draft a changelog recycler view ready to be displayed
     *
     * @param ctx           the context to construct the view with
     * @param configId      the xml configuration resource id
     * @return              the built RecyclerView ready for insertion
     */
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

    /**
     * Open the changelog activity
     *
     * @param ctx           the context to launch the activity with
     * @param configId      the changelog xml configuration
     */
    public static void gust(Context ctx, @XmlRes int configId){
        Intent intent = new Intent(ctx, ChangeLogActivity.class);
        intent.putExtra(ChangeLogActivity.EXTRA_CONFIG, configId);
        ctx.startActivity(intent);
    }

    /**
     * Open the changelog activity with the default configuration file {@link R.xml#changelog}
     * @param ctx       the context to open with
     */
    public static void gust(Context ctx){
        gust(ctx, R.xml.changelog);
    }

    /**
     * Check to see if we should show the changelog activity if there are any new changes
     * to the configuration file.
     *
     * @param ctx           the context to launch the activity with
     * @param configId      the changelog configuration xml resource id
     */
    public static void checkChangelogActivity(Context ctx, @XmlRes int configId){

        // Parse configuration
        ChangeLog changeLog = Parser.parse(ctx, configId);
        if(changeLog != null){

            // Validate that there is a new version code
            if(validateVersion(ctx, changeLog)) {
                gust(ctx, configId);
            }else{
                Timber.i("No new changelog available.");
            }

        }else{
            throw new NullPointerException("Unable to find a 'Winds' configuration @ " + configId);
        }


    }

    /**
     * Check to open the changelog activity with the default configuration file
     * {@link R.xml#changelog}
     *
     * @param ctx       the context to launch with
     */
    public static void checkChangelogActivity(Context ctx){
        checkChangelogActivity(ctx, R.xml.changelog);
    }

    /**
     * Validate the last seen stored verion code against the current changelog configuration
     * to see if there is any updates and whether or not we should show the changelog dialog when
     * called.
     *
     * @param ctx
     * @param clog
     * @return
     */
    private static boolean validateVersion(Context ctx, ChangeLog clog){

        // Get Preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        int lastSeen = prefs.getInt(PREF_CHANGELOG_LAST_SEEN, -1);

        // Second sort versions by it's code
        int latest = Integer.MIN_VALUE;
        for(Version version: clog.versions){
            if(version.code > latest){
                latest = version.code;
            }
        }

        // Get applications current version
        if(latest > lastSeen){
            prefs.edit().putInt(PREF_CHANGELOG_LAST_SEEN, latest).apply();
            return true;
        }

        return false;
    }

}
