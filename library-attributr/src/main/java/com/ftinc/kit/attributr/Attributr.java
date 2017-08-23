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

package com.ftinc.kit.attributr;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.XmlRes;
import com.ftinc.kit.attributr.ui.LicenseActivity;


/**
 * Static tool class for generating and displaying third party License attribution in a
 * Material Design manner.
 *
 * Project: 52Kit
 * Package: com.ftinc.kit.ui.attributr
 * Created by drew.heavner on 3/9/15.
 */
public class Attributr {

    /**
     * Open the third party licenses activity with the supplied configuration file
     *
     * @param ctx               the context reference to launch the activity with
     * @param configResId       the XML configuration resource id
     * @param title             the Activity/Screen title you want to use
     */
    public static void openLicenses(Context ctx, @XmlRes int configResId, String title){
        Intent intent = new Intent(ctx, LicenseActivity.class);
        intent.putExtra(com.ftinc.kit.attributr.ui.LicenseActivity.EXTRA_CONFIG, configResId);
        intent.putExtra(com.ftinc.kit.attributr.ui.LicenseActivity.EXTRA_TITLE, title);
        ctx.startActivity(intent);
    }

    /**
     * Open the third party licenses activity with the supplied configuration file
     *
     * @param ctx               the context reference to launch the activity with
     * @param configResId       the XML configuration resource id
     */
    public static void openLicenses(Context ctx, @XmlRes int configResId){
        openLicenses(ctx, configResId, ctx.getString(R.string.activity_license_label));
    }

}
